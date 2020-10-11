package com.junction.mymusicmap.presentation.userpage

import io.reactivex.Single
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ZepetoModel {
    fun getUrl(body: ZepetoResponse): Single<ZepetoResponse>
}