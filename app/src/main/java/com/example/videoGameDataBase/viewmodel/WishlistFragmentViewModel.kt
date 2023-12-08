package com.example.videoGameDataBase.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoGameDataBase.common.UIModelListing
import com.example.videoGameDataBase.domain.usecase.get_wishlist_games.GetWishlistGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistFragmentViewModel @Inject constructor(
    private val getWishlistGamesUseCase: GetWishlistGamesUseCase
):ViewModel() {

    val listing=MutableLiveData<ArrayList<UIModelListing>>()

    init {
        listing.value= arrayListOf()
    }

    fun getGames(context: Context){
        getGamesFromDB(context)
    }

    private fun getGamesFromDB(context: Context){
        viewModelScope.launch{
            listing.value=getWishlistGamesUseCase(context) ?: arrayListOf()
        }
    }

}