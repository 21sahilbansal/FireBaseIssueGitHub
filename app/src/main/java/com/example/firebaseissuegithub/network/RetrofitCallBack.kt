package com.example.firebaseissuegithub.network

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

abstract class RetrofitCallBack<T> : Callback<T> {

    private val TAG: String = RetrofitCallBack::class.java.toString()
    private val DEFAULT_ERROR_MESSAGE = "Something Went Wrong!!"
    private val NO_INTERNET = "No Internet Connection"

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        if (t is IOException) { // case of internet error
            handleFailure(call, Throwable(NO_INTERNET, Throwable(0.toString())))
        } else { // case of response parsing
            handleFailure(
                call,
                Throwable(DEFAULT_ERROR_MESSAGE, Throwable("-1"))
            )
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>) {
        Log.i(
            "http",
            "endService " + response.raw().request.url + " : " + Date().time
        )
        if (response.isSuccessful) {
            handleSuccess(call, response)
        } else {
            var error: String
            var errorBody: JSONObject? = null
            try {
                errorBody = JSONObject(response.errorBody()!!.string())
                error = errorBody.getString("message")
            } catch (e: JSONException) {
                error = DEFAULT_ERROR_MESSAGE
                e.printStackTrace()
            } catch (e: IOException) {
                error = DEFAULT_ERROR_MESSAGE
                e.printStackTrace()
            } catch (e: NullPointerException) {
                error = DEFAULT_ERROR_MESSAGE
                e.printStackTrace()
            }
            try {
                //logexception
            } catch (e: Exception) {
                e.printStackTrace()
            }
            handleFailure(
                call,
                Throwable(error, Throwable(response.code().toString()))
            )
        }
    }

    abstract fun handleSuccess(call: Call<T>?, response: Response<T>?
    )

    abstract fun handleFailure(call: Call<T>?, t: Throwable?)

}