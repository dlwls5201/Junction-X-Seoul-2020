package com.junction.mymusicmap.presentation.userpage

data class ZepetoResponse(
    val type: String,
    val target: Target,
    val url: String?
)

data class Target(
    val hashCodes: List<String>,
)