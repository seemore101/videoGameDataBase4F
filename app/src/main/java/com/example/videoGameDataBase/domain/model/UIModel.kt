package com.example.videoGameDataBase.common

import java.io.Serializable

data class UIModelDetails(
    var id:Int,
    var name:String,
    var description:String,
    var metacritic:Int?,
    var releaseDate:String?,
    var websiteURL:String,
    var redditURL:String,
    var genres:ArrayList<String>,
    var developers:ArrayList<String>,
    var backgroundImage:String
)
data class UIModelListing(
    var id: Int,
    var name: String,
    var backgroundImage: String?
):Serializable

data class UIPlatforms(
    var id:Int,
    var name: String
)