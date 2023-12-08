package com.example.videoGameDataBase.domain.usecase.get_games_by_nexturl

import com.example.videoGameDataBase.common.RAWGResponseGameListResult
import com.example.videoGameDataBase.domain.repository.IApiRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetGamesByNextURLUseCase @Inject constructor(
    private val repository: IApiRepository
) {
    operator fun invoke(nextURL:String):Single<RAWGResponseGameListResult>{
        return repository.getGamesByNextURL(nextURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}