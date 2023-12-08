package com.example.videoGameDataBase.domain.usecase.get_game_details

import com.example.videoGameDataBase.common.RAWGResponseGameDetails
import com.example.videoGameDataBase.domain.repository.IApiRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val repository: IApiRepository
) {
     operator fun invoke(id:Int):Single<RAWGResponseGameDetails>{
        return repository.getGameDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}