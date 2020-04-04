package com.example.firebaseissuegithub.userInterface.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.firebaseissuegithub.R
import com.example.firebaseissuegithub.base.BaseActivity
import com.example.firebaseissuegithub.callBack.FragCallBack
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.userInterface.fragment.IssueFragment

class MainActivity : BaseActivity() , FragCallBack {
init {
    FireBaseGitHubApplication.getInstance().appComponent.inject(this)

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openIssueFragment()
    }

    override fun getLayoutRes() = R.layout.activity_main


    private fun openIssueFragment() {
        replaceFragment(R.id.container, IssueFragment.newInstance(), IssueFragment.TAG)
    }


    override fun onFragmentChange(fragment: Fragment, tag: String) {
        replaceFragment(R.id.container, fragment, tag, true)
    }
}
