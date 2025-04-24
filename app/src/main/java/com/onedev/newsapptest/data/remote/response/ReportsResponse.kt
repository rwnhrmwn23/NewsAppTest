package com.onedev.newsapptest.data.remote.response

import com.onedev.newsapptest.data.remote.dto.ReportDto

data class ReportsResponse(
    val results: List<ReportDto>
)