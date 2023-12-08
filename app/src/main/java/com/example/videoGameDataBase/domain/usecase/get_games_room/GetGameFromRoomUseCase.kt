package com.example.videoGameDataBase.domain.usecase.get_games_room

import android.content.Context
import com.example.videoGameDataBase.common.UIModelDetails
import com.example.videoGameDataBase.data.local.model.toUIModelDetails
import com.example.videoGameDataBase.data.local.service.ModelDatabase
import com.example.videoGameDataBase.dependencyinjection.DefaultDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject

class GetGameFromRoomUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
){
//Dispatcher,Statehandle,Navigation-Navbar,suspend

    suspend operator fun invoke(context: Context,uuid:Int):UIModelDetails?= withContext(defaultDispatcher){
        val dao=ModelDatabase(context).modelDao()
        dao.checkIfWishlist(uuid)?.toUIModelDetails()
    }
}