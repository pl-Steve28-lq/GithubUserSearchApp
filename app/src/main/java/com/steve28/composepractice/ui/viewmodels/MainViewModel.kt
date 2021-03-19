package com.steve28.composepractice.ui.viewmodels

import androidx.compose.runtime.Composable
import com.steve28.composepractice.client.GithubClient
import com.steve28.composepractice.ui.components.MainContent

class MainViewModel {
    private val client by lazy { GithubClient.getApi() }

    @Composable
    fun MainComponent() {
        MainContent(vm=this)
    }

    suspend fun getUserList(username: String)
        = client.getUsers(username).items

    suspend fun getUserInfo(username: String)
        = client.getUserInfo(username)
}