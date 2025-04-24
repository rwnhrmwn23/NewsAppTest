package com.onedev.newsapptest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val publishedAt: String,
    val summary: String
) : Parcelable
