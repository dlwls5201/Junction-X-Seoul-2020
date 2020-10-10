package com.junction.mymusicmap.data.api

import com.junction.mymusicmap.data.model.YouTubeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeSearchApi {

    @GET("youtube-search/")
    fun searchYoutube(@Query("q") query: String): Single<YouTubeResponse>
}