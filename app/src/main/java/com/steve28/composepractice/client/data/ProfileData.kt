package com.steve28.composepractice.client.data

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName(value="login") val name: String,
    @SerializedName(value="avatar_url") val image: String
)