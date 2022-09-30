package com.robj.betterpod.di

import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.robj.betterpod.database.AppDatabase
import com.robj.betterpod.networking.ApiRepo
import com.robj.betterpod.networking.ApiService
import com.robj.betterpod.networking.DbRepo
import com.robj.betterpod.networking.TokenInterceptor
import kotlinx.serialization.json.Json
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

val repositoryModule = module {

    single { ApiRepo(get(), get()) }
    single { DbRepo(get()) }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor())
//            .addInterceptor(MockInterceptor(get()))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
//                        if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
//                        } else {
//                            HttpLoggingInterceptor.Level.NONE
//                        }
                }
            )
            .build()
    }

    single<Retrofit> {
        val contentType = "application/json".toMediaType()
        @Suppress("OPT_IN_USAGE")
        Retrofit.Builder()
            .baseUrl("https://api.podcastindex.org/api/1.0/")
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        val sharedPrefsFile = "shared_prefs"
        EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            get(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    factory<SupportSQLiteOpenHelper.Factory> {
        fun generateKey(): String {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            return MasterKeys.getOrCreate(keyGenParameterSpec).subSequence(0, 10).toString()
        }

        val password = get<SharedPreferences>().let { sharedPreferences ->
            sharedPreferences.getString(
                DB_PASS,
                sharedPreferences.apply {
                    edit().putString(
                        DB_PASS,
                        generateKey()
                    ).apply()
                }.getString(
                    DB_PASS, null
                )
            )
        } ?: throw RuntimeException("Password was null!!")
        SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
    }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, "database-name.db"
        ).openHelperFactory(get())
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }

}

private const val DB_PASS = "db_pass"
