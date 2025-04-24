package com.onedev.newsapptest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val publishedAt: String,
    val summary: String,
    val launches: List<Launch>? = null,
    val events: List<Event>? = null
) : Parcelable {
    @Parcelize
    data class Launch(val launchId: String, val provider: String) : Parcelable

    @Parcelize
    data class Event(val eventId: Int, val provider: String) : Parcelable
}
