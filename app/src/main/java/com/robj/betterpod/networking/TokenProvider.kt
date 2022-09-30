package com.robj.betterpod.networking

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.*

interface TokenProvider {
    var accountToken: String
    var profileToken: String?
}

class TokenProviderImpl : TokenProvider {
    override var accountToken = ""
    override var profileToken: String? = null
}

object FixedTokenProvider : TokenProvider {
    override var accountToken: String =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW5kZXJzb24uY29zdGFAbXdhbmdvcHMuY28udWsiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjhjNjk0NmY0LTIyNTktNDMxNS1iZDFiLTNmMzg5MWFlNjJmNSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IjUwMjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9jb3VudHJ5IjoiMjM1IiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwOS8wOS9pZGVudGl0eS9jbGFpbXMvYWN0b3IiOiJGYWxzZSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvdXNlcmRhdGEiOiI3QTQ3MzgwOS0wQkE2LTRFNjUtQTNFQS02NTc0NzIyNEYxMTkiLCJleHAiOjE2NDgzMDU3MzIsImlzcyI6IlNIVFYiLCJhdWQiOiJBcGlVc2VyIn0.FvYQk4jKFqASP37w7I_Ac-7yX6UiuAw5IZKVVHaBYzY"
    override var profileToken: String? =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiYW5kZXJzb24uY29zdGFAbXdhbmdvcHMuY28udWsiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1laWRlbnRpZmllciI6IjhjNjk0NmY0LTIyNTktNDMxNS1iZDFiLTNmMzg5MWFlNjJmNSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvcm9sZSI6IjUwMjEiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9jb3VudHJ5IjoiMjM1IiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwOS8wOS9pZGVudGl0eS9jbGFpbXMvYWN0b3IiOiJGYWxzZSIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvdXNlcmRhdGEiOiI3QTQ3MzgwOS0wQkE2LTRFNjUtQTNFQS02NTc0NzIyNEYxMTkiLCJleHAiOjE2NDgzMDU3MzIsImlzcyI6IlNIVFYiLCJhdWQiOiJBcGlVc2VyIn0.FvYQk4jKFqASP37w7I_Ac-7yX6UiuAw5IZKVVHaBYzY"
}

class TokenInterceptor(
//    private val tokenProvider: TokenProvider
) : Interceptor {
    //    private val accountTokenEndpoints = setOf("/profile/setprofile", "/profile/profiles")
    override fun intercept(chain: Interceptor.Chain): Response {
//        val authToken = if (chain.request().url.encodedPath in accountTokenEndpoints)
//            tokenProvider.accountToken
//        else tokenProvider.profileToken
        val authKey = "FF42BTJUQHCKTUBDKJJ3"
        val secret = "U9qgBZJxcvUAeL4CEcVJEp99m\$YL\$GrPc7qH49YG"
        val unixTime = System.currentTimeMillis().toString().substring(0, 10)
        return chain.request().newBuilder()
            .header("User-Agent", "BetterPod/Dev")
            .header("X-Auth-Key", authKey)
            .header("X-Auth-Date", unixTime)
            .header("Authorization", SHA1(authKey + secret + unixTime) ?: "")
            .build().let { chain.proceed(it) }
    }
}

fun byteArrayToString(bytes: ByteArray): String? {
    val buffer = StringBuilder()
    for (b in bytes) {
        buffer.append(java.lang.String.format(Locale.getDefault(), "%02x", b))
    }
    return buffer.toString()
}

fun SHA1(clearString: String): String? {
    return try {
        val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-1")
        messageDigest.update(clearString.toByteArray(charset("UTF-8")))
        byteArrayToString(messageDigest.digest())
    } catch (ignored: Exception) {
        ignored.printStackTrace()
        null
    }
}
