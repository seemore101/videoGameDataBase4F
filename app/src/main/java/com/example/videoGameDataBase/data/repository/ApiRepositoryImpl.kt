package com.example.videoGameDataBase.data.repository

import com.example.videoGameDataBase.common.*
import com.example.videoGameDataBase.data.remote.service.IRawgAPI
import com.example.videoGameDataBase.domain.repository.IApiRepository
import io.reactivex.Single
import javax.inject.Inject


//Bu sınıf fonksiyonları IAPIRepository'den inherit eder. Fonksiyonlar zaten yazılmıştır.
// Yeni request olduğu zaman fonksiyon burada implement edilir.
// API değişimi olacağı zaman constructor içerisindeki api değiştirilir.


class ApiRepositoryImpl @Inject constructor(
    private val api: IRawgAPI
): IApiRepository {
    override fun getGameDetails(id: Int): Single<RAWGResponseGameDetails> {
        return api.getGameDetails(id)
    }

    override fun getPlatforms(): Single<RAWGResponsePlatformsResult> {
        return api.getPlatforms()
    }

    override fun getGamesBySearch(searchString: String): Single<RAWGResponseGameListResult> {
        return api.getGameListBySearch(search = searchString)
    }

    override fun getGamesBySearchPlatform(
        searchString: String,
        platformId: Int
    ): Single<RAWGResponseGameListResult> {
        return api.getGameListBySearchPlatform(search = searchString, platformID = platformId)
    }

    override fun getGamesByNextURL(urlString: String): Single<RAWGResponseGameListResult> {
        return api.getGamesByNextURL(urlString)
    }

}