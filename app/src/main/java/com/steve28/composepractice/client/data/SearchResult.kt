package com.steve28.composepractice.client.data

import com.google.gson.annotations.SerializedName
import com.steve28.composepractice.client.data.ProfileData

data class SearchResult(
    @SerializedName(value="items") val items: List<ProfileData>
)