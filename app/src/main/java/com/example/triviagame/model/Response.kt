package com.example.triviagame.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("response_code") val responseCode: Int,
    val results: List<Question>
)