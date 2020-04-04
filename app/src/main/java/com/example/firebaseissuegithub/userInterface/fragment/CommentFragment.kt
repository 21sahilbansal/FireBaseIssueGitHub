package com.example.firebaseissuegithub.userInterface.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseissuegithub.R
import com.example.firebaseissuegithub.base.BaseFragment
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.model.Comments
import com.example.firebaseissuegithub.userInterface.adapter.CommentAdapter
import com.example.firebaseissuegithub.userInterface.viewModel.MainViewModel
import kotlinx.android.synthetic.main.comments_layout.*

class CommentFragment : BaseFragment() {

    private lateinit var viewModel: MainViewModel
    private val commentList by lazy { ArrayList<Comments>() }
    private val issueAdapter by lazy {
        CommentAdapter(commentList)
    }
    init {
        FireBaseGitHubApplication.getInstance().appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(FireBaseGitHubApplication.getInstance()).create(MainViewModel::class.java)
    }
    override fun viewInitialization(view: View) {
        showLoadingState(true)
       getIssueNumber()?.let {
           viewModel.getCommentList(it)?.observe(this, Observer {
               it.data?.let {
                   showLoadingState(false)
                   if (it.isNotEmpty())
                   {
                   setIssueData(it)
                   }
                   else
                       showNoCommentDialog()
               }?: kotlin.run {
                   handleError(it.throwable!!)
                   showLoadingState(false)
               }
           })
       }
    }

    private fun showNoCommentDialog() {
        showErrorDialog(getString(R.string.no_comment), getString(R.string.go_back))
    }

    private fun setIssueData(list: List<Comments>) {
        with(comments_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = issueAdapter
        }
        commentList.addAll(list)
        with(issueAdapter) {
            notifyDataSetChanged()
        }

    }

    override fun getLayoutRes(): Int {
        return R.layout.comments_layout
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }



    override fun onError(message: String) {
        showToast(message)
    }

    fun getIssueNumber() = arguments?.getInt(ISSUE_NUMBER)


    companion object {
        val TAG = CommentFragment::class.java.name
        const val ISSUE_NUMBER = "ISSUE_NUMBER"
        fun getInstance(number: Int): CommentFragment {
            val fragment = CommentFragment()
            val arg = Bundle()
            arg.putInt(ISSUE_NUMBER, number)
            fragment.arguments = arg
            return fragment
        }
    }

}