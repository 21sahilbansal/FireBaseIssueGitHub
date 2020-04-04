package com.example.firebaseissuegithub.apiService

import com.example.firebaseissuegithub.model.Comments
import com.example.firebaseissuegithub.model.Issues
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubAPIservice {


    @GET(ApiConstants.Companion.ApiServiceConstants.Issues)
    fun getFireBaseIosIssues(): Call<List<Issues>>

    @GET(ApiConstants.Companion.ApiServiceConstants.ISSUES_NUMBER_COMMENT)
    fun getFireBaseIosUserComments(@Path("number") number: Int): Call<List<Comments>>
}