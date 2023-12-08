package com.example.videoGameDataBase.domain.usecase.alert_dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import javax.inject.Inject


class AlertDialogUseCase @Inject constructor()  {
    private lateinit var dialog:AlertDialog
    operator fun invoke(
        context: Context,
        titleString: String,
        messageString: String,
        positiveString:String,
        negativeString: String,
        positiveOnClick:()->Unit,
        negativeOnClick:()->Unit=this::negativeOnClick
    ):AlertDialog{
        val alertDialogBuilder=AlertDialog.Builder(context)
            .setTitle(titleString)
            .setMessage(messageString)
            .setPositiveButton(positiveString,object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    positiveOnClick()
                }})
            .setNegativeButton(negativeString,object :DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    negativeOnClick()
                }
            })
        dialog=alertDialogBuilder.create()

        return dialog
    }
    private fun negativeOnClick(){
        dialog.dismiss()
    }

}