package com.steve28.composepractice.client

import com.steve28.composepractice.client.data.SearchResult
import com.steve28.composepractice.client.data.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {
    @GET("search/users")
    suspend fun getUsers(@Query("q") username: String): SearchResult

    @GET("users/{username}")
    suspend fun getUserInfo(@Path("username") username: String): UserInfo
}