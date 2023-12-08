package com.example.videoGameDataBase.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.videoGameDataBase.databinding.ActivitySplashScreenBinding
import com.example.videoGameDataBase.viewmodel.SplasherScreenViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplasherScreen : AppCompatActivity() {
    private val viewModel:SplasherScreenViewModel by viewModels()
    private lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observe()
        viewModel.getPlatforms()
        viewModel.getFirstList()

    }

    //TODO:Response döndürüp diğer tarafta işlemek daha mantıklı olabilir

    fun observe(){
        viewModel.readyForNextPagePlatforms.observe(this, Observer{
            it?.let {
                if (it && viewModel.readyForNextPageFirstList.value ==true){
                    val intent=Intent(this,MainActivity::class.java)
                    intent.putExtra("nextPageUrl",viewModel.nextPageURL.value)
                    intent.putExtra("platforms",Gson().toJson(viewModel.platforms.value))
                    intent.putExtra("firstList",Gson().toJson(viewModel.firstList.value))
                    startActivity(intent)
                    finish()
                }
            }
        })
        viewModel.readyForNextPageFirstList.observe(this, Observer {
            if (it && viewModel.readyForNextPagePlatforms.value==true){
                val intent=Intent(this,MainActivity::class.java)
                intent.putExtra("nextPageUrl",viewModel.nextPageURL.value)
                intent.putExtra("platforms",Gson().toJson(viewModel.platforms.value))
                intent.putExtra("firstList",Gson().toJson(viewModel.firstList.value))
                startActivity(intent)
                finish()
            }
        })
    }
}