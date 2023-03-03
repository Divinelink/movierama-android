package com.andreolas.movierama.base.data.remote.movies.dto.details.videos

import com.andreolas.movierama.details.domain.model.Video
import com.andreolas.movierama.details.domain.model.VideoSite
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponseApi(
    val id: Int,
    val videos: List<VideoResultsApi>,
)

@Serializable
data class VideoResultsApi(
    val id: String,
    @SerialName("iso_3166_1")
    val iso31661: String,
    @SerialName("iso_639_1")
    val iso6391: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerialName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String,
)

internal fun VideosResponseApi.toDomainVideosList(): List<Video> {
    return this.videos.map(VideoResultsApi::toVideo)
}

private fun VideoResultsApi.toVideo(): Video {
    return Video(
        id = this.id,
        name = this.name,
        site = VideoSite.values().find { it.name == this.site },
        key = this.key,
        officialTrailer = this.type == "Trailer" && this.official,
    )
}
