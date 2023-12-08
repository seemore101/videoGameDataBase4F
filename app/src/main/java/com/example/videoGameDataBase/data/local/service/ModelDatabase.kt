package com.example.videoGameDataBase.data.local.service

import android.content.Context
import androidx.room.*
import com.example.videoGameDataBase.common.DBModelDetails
import com.example.videoGameDataBase.data.local.util.Converters

@Database(entities = arrayOf(DBModelDetails::class), version = 1)
@TypeConverters(Converters::class)
abstract class ModelDatabase:RoomDatabase() {

    abstract fun modelDao(): ModelDAO


    companion object{
        @Volatile private var instance: ModelDatabase?=null

        private val lock= Any()

        operator fun invoke(context: Context)= instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance=it
            }
        }

        private fun makeDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,ModelDatabase::class.java,"modeldatabase"
        ).build()
    }
}