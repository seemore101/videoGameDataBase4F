package com.example.videoGameDataBase.data.remote.service

import com.example.videoGameDataBase.BuildConfig
import com.example.videoGameDataBase.common.Constants
import com.example.videoGameDataBase.common.RAWGResponseGameDetails
import com.example.videoGameDataBase.common.RAWGResponseGameListResult
import com.example.videoGameDataBase.common.RAWGResponsePlatformsResult
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RawgAPIProvider {

    private val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val headerInterceptor=object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original=chain.request()
            val originalHttpUrl=original.url
            val url=originalHttpUrl.newBuilder()
                .addQueryParameter("key",BuildConfig.RAWG_KEY)
                .build()
            val requestBuilder=original.newBuilder()
                .url(url)
            val request=requestBuilder.build()
            return chain.proceed(request)
        }
    }
    private val okHttpClient= OkHttpClient.Builder()
        .addInterceptor(logger)


    private val api= Retrofit.Builder()
        .baseUrl(Constants.RAWG_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient.build())
        .build()
        .create(IRawgAPI::class.java)


    fun getGameDetails(id:Int):Single<RAWGResponseGameDetails>{
        return api.getGameDetails(id)
    }
    fun getPlatforms(page:Int=1,pageSize:Int=16):Single<RAWGResponsePlatformsResult>{
        return api.getPlatforms(page = page, pageSize = pageSize)
    }
    fun getGameListBySearch(page: Int,pageSize: Int,searchString: String):Single<RAWGResponseGameListResult>{
        return api.getGameListBySearch(page = page, pageSize = pageSize, search = searchString)
    }
    fun getGameListBySearchPlatform(page: Int,pageSize: Int,searchString: String,platformID:Int):Single<RAWGResponseGameListResult>{
        return api.getGameListBySearchPlatform(page=page, pageSize = pageSize, search = searchString, platformID = platformID)
    }
    fun getGamesByNextURL(urlString:String):Single<RAWGResponseGameListResult>{
        return api.getGamesByNextURL(urlString)
    }
}