package com.example.videoGameDataBase.common

data class RAWGResponseGameListResult(
    val results:List<RAWGResponseGame>?,
    val next:String?
)

data class RAWGResponseGame(
    val id:Int,
    val name: String,
    val background_image:String
)

data class RAWGResponseGameDetails(
    val id:Int,
    val name:String,
    val description:String,
    val metacritic:Int?,
    val released:String?,
    val website:String,
    val reddit_url:String,
    val genres:List<RAWGResponseGameDetailsGenre>,
    val developers: List<RAWGResponseGameDetailsDevelopers>,
    val background_image: String
)

data class RAWGResponseGameDetailsGenre(
    val name:String?
)
data class RAWGResponseGameDetailsDevelopers(
    val name:String
)

data class RAWGResponsePlatformsResult(
    val results: List<RAWGResponsePlatforms>
)

data class RAWGResponsePlatforms(
    val name: String,
    val id:Int
)