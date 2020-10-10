package com.junction.mymusicmap.data.api

import com.junction.mymusicmap.data.model.Mp3Response
import com.junction.mymusicmap.data.model.YouTubeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeMp32Api {

    @GET("yt_to_mp3/")
    fun getMp3(@Query("video_id") video_id: String): Single<Mp3Response>
}