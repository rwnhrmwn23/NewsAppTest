package com.onedev.newsapptest.data.remote.response

import com.onedev.newsapptest.data.remote.dto.BlogDto

data class BlogResponse(
    val results: List<BlogDto>
)