package com.example.videoGameDataBase.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dbmodel")
data class DBModelDetails(
    @PrimaryKey(autoGenerate = false)
    var id:Int,

    @ColumnInfo(name ="name")
    var name:String,
    @ColumnInfo(name ="description")
    var description:String,

    @ColumnInfo(name ="metacritic")
    var metacritic:Int?,

    @ColumnInfo(name ="releasedate")
    var releaseDate:String?,

    @ColumnInfo(name ="websiteurl")
    var websiteURL:String,

    @ColumnInfo(name ="redditurl")
    var redditURL:String,

    @ColumnInfo(name ="genres")
    var genres:List<String>,//Arraylist<String>

    @ColumnInfo(name ="developers")
    var developers:List<String>,//Arraylist<String>

    @ColumnInfo(name ="backgroundimage")
    var backgroundImage:String
)


data class DBModelListing(
    var id: Int,
    var name: String,
    var backgroundImage: String?
)