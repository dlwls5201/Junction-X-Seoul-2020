package com.junction.mymusicmap.presentation.userpage

import io.reactivex.Single

class ZepetoModelImpl(private val service: ZepetoService) : ZepetoModel {
    override fun getUrl(body: ZepetoResponse): Single<ZepetoResponse> =
        service.getUrl(
            body = ZepetoResponse(
                type = "booth",
                target = Target(listOf("07FS6S")),
                url = null
            )
        )
}