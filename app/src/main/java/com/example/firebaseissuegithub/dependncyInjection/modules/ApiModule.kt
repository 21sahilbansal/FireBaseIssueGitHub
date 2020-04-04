package com.example.firebaseissuegithub.dependncyInjection.modules

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.firebaseissuegithub.apiService.ApiConstants
import com.example.firebaseissuegithub.apiService.ApiConstants.Companion.HEADER_CACHE_CONTROL
import com.example.firebaseissuegithub.apiService.ApiConstants.Companion.HEADER_PRAGMA
import com.example.firebaseissuegithub.apiService.GitHubAPIservice
import com.example.firebaseissuegithub.common.FireBaseGitHubApplication
import com.example.firebaseissuegithub.helper.ApplicationUtil
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(val fireBaseGitHubApplication: FireBaseGitHubApplication) {


    @Singleton
    @Provides
    fun provideApplication(): FireBaseGitHubApplication{
        return fireBaseGitHubApplication
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideApiInterface(retrofit: Retrofit): GitHubAPIservice {
        return retrofit.create(GitHubAPIservice::class.java)
    }
    @Provides
    fun provideOkHttpClient(
        context: FireBaseGitHubApplication,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { provideOfflineCacheInterceptor(context, it) }
            .addNetworkInterceptor { provideCacheInterceptor(context, it) }
            .cache(provideCache(context))
            .build()
    }


    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    private fun provideCache(context: FireBaseGitHubApplication): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
        } catch (e: Exception) {
            Log.e("Cache", "Could not create Cache!")
        }
        return cache
    }

    private fun provideOfflineCacheInterceptor(
        context: FireBaseGitHubApplication,
        chain: Interceptor.Chain
    ): Response {
        var request = chain.request()

        if (!ApplicationUtil.hasNetwork(context)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun provideCacheInterceptor(
        context: FireBaseGitHubApplication,
        chain: Interceptor.Chain
    ): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = if (ApplicationUtil.hasNetwork(context)) {
            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
        } else {
            CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()
        }

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}