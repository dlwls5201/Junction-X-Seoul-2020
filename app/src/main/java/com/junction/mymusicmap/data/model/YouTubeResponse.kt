package com.junction.mymusicmap.data.model

data class YouTubeResponse(
    val query: String?,
    val items: List<Item>?,
    val nextpageRef: Any?,
    val results: Int?,
    val filters: List<Filter?>?,
    val currentRef: Any?,
    val status: Boolean?
) {
    data class Item(
        val id: String?,
        val type: String?,
        val title: String?,
        val link: String?,
        val thumbnail: String?,
        val author: Author?,
        val description: String?,
        val views: Int?,
        val duration: String?,
        val uploaded_at: String?,

        //client
        var isPlay: Boolean = false
    ) {
        data class Author(
            val name: String?,
            val ref: String?,
            val verified: Boolean?
        )
    }

    data class Filter(
        val ref: Any?,
        val name: String?,
        val active: Boolean?
    )
}