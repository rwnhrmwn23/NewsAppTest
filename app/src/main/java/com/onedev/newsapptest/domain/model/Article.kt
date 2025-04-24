package com.onedev.newsapptest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val publishedAt: String,
    val summary: String,
    val launches: List<Launch>,
    val events: List<Event>
) : Parcelable {
    @Parcelize
    data class Launch(val launchId: String, val provider: String) : Parcelable

    @Parcelize
    data class Event(val eventId: Int, val provider: String) : Parcelable
}
