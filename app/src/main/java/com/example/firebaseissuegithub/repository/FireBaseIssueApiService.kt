package com.example.firebaseissuegithub.repository

import androidx.lifecycle.MutableLiveData
import com.example.firebaseissuegithub.apiService.GitHubAPIservice
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.helper.WrapperDataClass
import com.example.firebaseissuegithub.model.Comments
import com.example.firebaseissuegithub.model.Issues
import com.example.firebaseissuegithub.network.RetrofitCallBack
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class FireBaseIssueApiService @Inject constructor(var gitHubAPIservice: GitHubAPIservice) {

    init {
        FireBaseGitHubApplication.getInstance().appComponent.inject(this)
    }
    var mutableLiveData : MutableLiveData<WrapperDataClass<List<Issues>>> = MutableLiveData()

    var mutableLiveDataCommentsList : MutableLiveData<WrapperDataClass<List<Comments>>> = MutableLiveData()

    var  issueDataWrapper = WrapperDataClass<List<Issues>>()
    var commentDataWrapper = WrapperDataClass<List<Comments>>()

    fun getFireBaseIosIssues() : MutableLiveData <WrapperDataClass<List<Issues>>>?{
        gitHubAPIservice.getFireBaseIosIssues().enqueue(object : RetrofitCallBack<List<Issues>>(){
            override fun handleSuccess(
                call: Call<List<Issues>>?,
                response: Response<List<Issues>>?
            ) {
                var issuesDataResponse = response?.body()
                issueDataWrapper.data = issuesDataResponse
                mutableLiveData.postValue(issueDataWrapper)
            }

            override fun handleFailure(call: Call<List<Issues>>?, t: Throwable?) {
                issueDataWrapper.throwable = t
                mutableLiveData.postValue(issueDataWrapper)
            }
        })
        return mutableLiveData
    }

    fun getFireBaseIosUserComments(number : Int) : MutableLiveData <WrapperDataClass<List<Comments>>>?{

       gitHubAPIservice.getFireBaseIosUserComments(number).enqueue(object : RetrofitCallBack<List<Comments>>(){
           override fun handleSuccess(
               call: Call<List<Comments>>?,
               response: Response<List<Comments>>?
           ) {
               commentDataWrapper.data = response?.body()
               mutableLiveDataCommentsList.postValue(commentDataWrapper)
           }
           override fun handleFailure(call: Call<List<Comments>>?, t: Throwable?) {
               commentDataWrapper.throwable = t
               mutableLiveDataCommentsList.postValue(commentDataWrapper)
           }
       })
        return mutableLiveDataCommentsList
    }
}

