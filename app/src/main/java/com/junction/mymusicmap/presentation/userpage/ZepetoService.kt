package com.junction.mymusicmap.presentation.userpage

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ZepetoService {
    @Headers(
        "Content-Type: application/json"
    )
    @POST("graphics/zepeto/booth/PHOTOBOOTH_ONE_543")
    fun getUrl(
        @Body body: ZepetoResponse,
    ): Single<ZepetoResponse>
}