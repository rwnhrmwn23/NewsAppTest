package com.onedev.newsapptest.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsSiteResponse(
    val version: String,
    @SerializedName("news_sites")
    val newsSites: List<String>
)