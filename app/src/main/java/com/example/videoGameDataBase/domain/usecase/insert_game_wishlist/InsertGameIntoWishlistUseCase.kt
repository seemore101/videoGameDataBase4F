package com.example.videoGameDataBase.domain.usecase.insert_game_wishlist

import android.content.Context
import com.example.videoGameDataBase.common.DBModelDetails
import com.example.videoGameDataBase.data.local.service.ModelDatabase
import com.example.videoGameDataBase.dependencyinjection.DefaultDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject

class InsertGameIntoWishlistUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(context: Context, dbModelDetails: DBModelDetails)= withContext(defaultDispatcher){
            val dao= ModelDatabase(context).modelDao()
            dao.insertIntoWishlist(dbModelDetails)
    }
}