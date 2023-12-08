package com.example.videoGameDataBase.data.remote.service

import com.example.videoGameDataBase.BuildConfig
import com.example.videoGameDataBase.common.RAWGResponseGameDetails
import com.example.videoGameDataBase.common.RAWGResponseGameListResult
import com.example.videoGameDataBase.common.RAWGResponsePlatformsResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface IRawgAPI {

    @GET("games/{id}")
    fun getGameDetails(@Path("id")id:Int,@Query("key")key: String=BuildConfig.RAWG_KEY):Single<RAWGResponseGameDetails>

    @GET("platforms")
    fun getPlatforms(@Query("key")key:String=BuildConfig.RAWG_KEY,@Query("page")page: Int=1, @Query("page_size")pageSize: Int=16):Single<RAWGResponsePlatformsResult>

    @GET("games")
    fun getGameListBySearch(@Query("key")key:String=BuildConfig.RAWG_KEY,@Query("page")page:Int=1,@Query("page_size")pageSize:Int=6,@Query("search")search:String):Single<RAWGResponseGameListResult>

    @GET("games")
    fun getGameListBySearchPlatform(@Query("key")key:String=BuildConfig.RAWG_KEY,@Query("page")page:Int=1,@Query("page_size")pageSize:Int=6,@Query("search")search:String,@Query("platforms")platformID:Int):Single<RAWGResponseGameListResult>

    @GET
    fun getGamesByNextURL(@Url urlString: String):Single<RAWGResponseGameListResult>

}