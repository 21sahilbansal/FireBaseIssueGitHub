package com.example.firebaseissuegithub.userInterface.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseissuegithub.R
import com.example.firebaseissuegithub.base.BaseFragment
import com.example.firebaseissuegithub.callBack.AdpterCallBack
import com.example.firebaseissuegithub.callBack.FragCallBack
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.model.Issues
import com.example.firebaseissuegithub.userInterface.adapter.IssueAdapter
import com.example.firebaseissuegithub.userInterface.viewModel.MainViewModel
import kotlinx.android.synthetic.main.issue_layout.*

class IssueFragment : BaseFragment(),AdpterCallBack {
    private lateinit var fragmentChangeListener: FragCallBack
    private lateinit var viewModel: MainViewModel
    private val issueData by lazy { ArrayList<Issues>() }
    private val issueAdapter by lazy {
        IssueAdapter(issueData, this)
    }

    init {
        FireBaseGitHubApplication.getInstance().appComponent.inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(FireBaseGitHubApplication.getInstance()).create(MainViewModel::class.java)
        fragmentChangeListener = context as FragCallBack

    }

    override fun viewInitialization(view: View) {
        showLoadingState(true)
        viewModel.getIssuesList()?.observe(this, Observer {
            it.data?.let {
                setIssueData(it)
            }?: kotlin.run {
                handleError(it.throwable!!)
                showLoadingState(false)
            }
        })
    }

    private fun setIssueData(list: List<Issues>) {
        initAdapter()
        showLoadingState(false)
        issueData.addAll(list)
        with(issueAdapter) {
            notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        with(parent_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = issueAdapter
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.issue_layout
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading)
            shimmer_view_container.startShimmerAnimation()
        else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }


    override fun onIssueClick(number: Int) {
        fragmentChangeListener.onFragmentChange(
            CommentFragment.getInstance(number),
            CommentFragment.TAG
        )    }


    companion object {
        val TAG = IssueFragment::class.java.name
        fun newInstance(): IssueFragment {
            return IssueFragment()
        }
    }
}