package com.example.firebaseissuegithub.userInterface.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.helper.WrapperDataClass
import com.example.firebaseissuegithub.model.Comments
import com.example.firebaseissuegithub.model.Issues
import com.example.firebaseissuegithub.repository.FireBaseIssueApiService
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var apiService: FireBaseIssueApiService

    init {
        FireBaseGitHubApplication.getInstance().appComponent.inject(this)
    }


    fun getIssuesList(): MutableLiveData<WrapperDataClass<List<Issues>>>?{
      return  apiService?.let { it.getFireBaseIosIssues() }

    }

    fun getCommentList(number : Int): MutableLiveData<WrapperDataClass<List<Comments>>>?{
        return apiService?.let { it.getFireBaseIosUserComments(number) }
    }

}