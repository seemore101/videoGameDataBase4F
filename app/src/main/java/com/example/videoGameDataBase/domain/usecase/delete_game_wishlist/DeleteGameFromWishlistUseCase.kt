package com.example.videoGameDataBase.domain.usecase.delete_game_wishlist

import android.content.Context
import com.example.videoGameDataBase.data.local.service.ModelDatabase
import com.example.videoGameDataBase.dependencyinjection.DefaultDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject

class DeleteGameFromWishlistUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(context: Context, uuid:Int)= withContext(defaultDispatcher) {
        val dao = ModelDatabase(context).modelDao()
        dao.deleteFromWishlist(uuid)
    }
}