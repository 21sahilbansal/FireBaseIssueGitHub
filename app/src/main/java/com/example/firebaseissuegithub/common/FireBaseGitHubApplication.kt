package com.example.firebaseissuegithub.common

import android.app.Application
import com.example.firebaseissuegithub.dependncyInjection.components.AppComponents
import com.example.firebaseissuegithub.dependncyInjection.components.DaggerAppComponents
import com.example.firebaseissuegithub.dependncyInjection.modules.ApiModule

class FireBaseGitHubApplication : Application() {

    lateinit var  appComponent : AppComponents


    override fun onCreate() {
        super.onCreate()
        instancej = this
        appComponent = DaggerAppComponents.builder().apiModule(ApiModule(this)).build()
        appComponent.inject(this)
    }

    companion object{
        lateinit var instancej : FireBaseGitHubApplication
        fun getInstance() :FireBaseGitHubApplication{
            return instancej
        }
    }


}