package com.steve28.composepractice.client.data

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName(value="name") val name: String?,
    @SerializedName(value="login") val username: String,
    @SerializedName(value="bio") val bio: String?
)