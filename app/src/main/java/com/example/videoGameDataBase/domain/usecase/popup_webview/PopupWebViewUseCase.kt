package com.example.videoGameDataBase.domain.usecase.popup_webview

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.videoGameDataBase.R
import javax.inject.Inject

class PopupWebViewUseCase @Inject constructor() {

    @SuppressLint("SetJavaScriptEnabled")
    operator fun invoke(context: Context, popupLayoutInt: Int, url:String): Dialog {
        val dialog= Dialog(context)
        dialog.setContentView(popupLayoutInt)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)

        val topTextView=dialog.window!!.findViewById<TextView>(R.id.TextView_popup_webview_toptext)
        topTextView.text=url
        val button=dialog.window!!.findViewById<Button>(R.id.Button_popup_webview_cancel)
        button.setOnClickListener {
            dialog.dismiss()
        }

        val switchToBrowser=dialog.window!!.findViewById<ImageView>(R.id.ImageView_popup_webview_switchToBrowser)
        switchToBrowser.setOnClickListener {
            val i= Intent(Intent.ACTION_VIEW)
            i.data= Uri.parse(url)
            context.startActivity(i)
            dialog.dismiss()
        }
        val webview=dialog.window!!.findViewById<WebView>(R.id.WebView_popup_webview_webview)
        webview.webViewClient=(object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                topTextView.text=request!!.url.toString()
                return false
            }
        })
        webview.settings.javaScriptEnabled=true
        webview.loadUrl(url)
        return dialog
    }
}