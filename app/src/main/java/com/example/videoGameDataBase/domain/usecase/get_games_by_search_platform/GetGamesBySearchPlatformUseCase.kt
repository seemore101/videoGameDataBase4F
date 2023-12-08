package com.example.videoGameDataBase.domain.usecase.get_games_by_search_platform

import com.example.videoGameDataBase.common.RAWGResponseGameListResult
import com.example.videoGameDataBase.domain.repository.IApiRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetGamesBySearchPlatformUseCase @Inject constructor(
    private val repository: IApiRepository
) {
    operator fun invoke(searchString: String,platformID:Int):Single<RAWGResponseGameListResult>{
        return repository.getGamesBySearchPlatform(searchString,platformID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}