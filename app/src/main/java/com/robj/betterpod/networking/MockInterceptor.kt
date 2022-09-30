package com.robj.betterpod.networking

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toUrl().toString()
        val responseString = getResponseString(url)
        return Response.Builder()
            .request(chain.request())
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toResponseBody(
                    "application/json".toMediaType()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun getResponseString(url: String): String {
        val apiCall = url.split("azurewebsites.net/api")[1]
        return when {
            apiCall.startsWith("/user/checkpin") -> loadJsonFromFile("checkPinResponse.json")
            apiCall.startsWith("/channel/channels") -> loadJsonFromFile("channelsResponse.json")
            apiCall.contains("medias") -> loadJsonFromFile("channelMedias.json")
            apiCall.startsWith("/profile/update") ||
                    apiCall.startsWith("/profile/create") ||
                    apiCall.startsWith("/tvfriends/share") -> loadJsonFromFile("messageResponse.json")
            apiCall.startsWith("/profile/setprofile") -> loadJsonFromFile("setProfileResponse.json")
            apiCall.startsWith("/profile/profiles") -> loadJsonFromFile("profilesResponse.json")
            apiCall.startsWith("/misc/background") -> loadJsonFromFile("background.json")
            apiCall.startsWith("/user/authenticate") -> loadJsonFromFile("authentication.json")
            apiCall.startsWith("/user/forgotpassword") -> loadJsonFromFile("messageResponse.json")
            apiCall.startsWith("/media/section/") -> {
                val id = apiCall.split("/")[3]
                loadJsonFromFile("section-$id.json")
            }
            apiCall.startsWith("/media/sections") -> loadJsonFromFile("sections.json")
            apiCall.startsWith("/media/banners") -> loadJsonFromFile("banners.json")
            apiCall.startsWith("/media/mylist") -> loadJsonFromFile("mylist.json")
            apiCall.startsWith("/media/") -> loadJsonFromFile("media.json")
            apiCall.startsWith("/misc/menuitems") -> loadJsonFromFile("menuitems.json")
            apiCall.startsWith("/search") -> loadJsonFromFile("search.json")
            apiCall.startsWith("/tvfriends/friendsfeed") -> loadJsonFromFile("friendList.json")
            apiCall.startsWith("/tvfriends/mediaFeed") -> loadJsonFromFile("channelMedias.json")
            apiCall.startsWith("/tvfriends/likesfeed") -> loadJsonFromFile("tvLikesResponse.json")
            apiCall.startsWith("/genre/genres") -> loadJsonFromFile("genres.json")
            apiCall.startsWith("/genre/") -> loadJsonFromFile("specific_genre.json")
            apiCall.startsWith("/linear/channels") -> loadJsonFromFile("channels.json")
            else -> ""
        }
    }

    private fun loadJsonFromFile(fileName: String): String = context.assets.open(fileName).use {
        val buffer = ByteArray(it.available())
        it.read(buffer)
        String(buffer, Charset.forName("UTF-8"))
    }
}
