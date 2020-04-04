package com.example.firebaseissuegithub.dependncyInjection.components

import android.app.Application
import android.content.Context
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.dependncyInjection.modules.ApiModule
import com.example.firebaseissuegithub.repository.FireBaseIssueApiService
import com.example.firebaseissuegithub.userInterface.activity.MainActivity
import com.example.firebaseissuegithub.userInterface.fragment.CommentFragment
import com.example.firebaseissuegithub.userInterface.fragment.IssueFragment
import com.example.firebaseissuegithub.userInterface.viewModel.MainViewModel
import dagger.Component
import java.security.AccessController.getContext
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule :: class])
interface AppComponents {

    fun inject (mainViewModel: MainViewModel)
    fun inject (fireBaseIssueApiService: FireBaseIssueApiService)
    fun inject (issueFragment: IssueFragment)
    fun inject (commentFragment: CommentFragment)
    fun inject (fireBaseGitHubApplication: FireBaseGitHubApplication)
    fun inject (mainActivity: MainActivity)
}