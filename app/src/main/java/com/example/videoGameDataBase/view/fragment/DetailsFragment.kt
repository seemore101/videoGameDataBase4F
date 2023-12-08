package com.example.videoGameDataBase.view.fragment

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.videoGameDataBase.R
import com.example.videoGameDataBase.databinding.FragmentDetailsBinding
import com.example.videoGameDataBase.viewmodel.DetailsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsFragmentViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setupComponents()
        getGameDetails()
    }

    private fun observeLiveData(){
        viewModel.gameDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.TextViewFragmentDetailsDescriptions.text= Html.fromHtml(it.description)
                binding.TextViewFragmentDetailsName.text=it.name

                if (it.developers.size==0){
                    binding.TextViewFragmentDetailsPublisher.visibility=View.GONE
                    binding.textView11.visibility=View.GONE
                }else{
                    binding.TextViewFragmentDetailsPublisher.text=it.developers.joinToString()
                }
                it.releaseDate?.let { releasedate->
                    binding.TextViewFragmentDetailsReleaseDate.text=releasedate
                }?: kotlin.run { binding.TextViewFragmentDetailsReleaseDate.visibility=View.GONE; binding.textView12.visibility=View.GONE}


                Glide.with(this).load(it.backgroundImage).into(binding.ImageViewFragmentDetailsImage)

                it.metacritic?.let {
                    if (it>75){
                        binding.TextViewFragmentDetailsScore.setBackgroundResource(R.drawable.green_score)
                        binding.TextViewFragmentDetailsScore.setTextColor(Color.parseColor("#00FF00"))
                    }
                    else if (it>50){
                        binding.TextViewFragmentDetailsScore.setBackgroundResource(R.drawable.yellow_score)
                        binding.TextViewFragmentDetailsScore.setTextColor(Color.parseColor("#FFEB3B"))
                    }
                    else{
                        binding.TextViewFragmentDetailsScore.setBackgroundResource(R.drawable.red_score)
                        binding.TextViewFragmentDetailsScore.setTextColor(Color.parseColor("#FF0000"))
                    }
                    binding.TextViewFragmentDetailsScore.text=it.toString()
                } ?: kotlin.run { binding.TextViewFragmentDetailsScore.visibility=View.GONE }


                if (it.genres.size==0){
                    binding.textView10.visibility=View.GONE
                    binding.TextViewFragmentDetailsGenres.visibility=View.GONE
                }else{
                    binding.TextViewFragmentDetailsGenres.text=it.genres.joinToString()
                }

                if (!it.redditURL.equals("")){
                    binding.TextViewFragmentDetailsReddit.setOnClickListener { v->
                        //Reddit is Blocked by CORS Policy in Webview
                        //Toast.makeText(context!!,it.redditURL,Toast.LENGTH_LONG).show()
                        //viewModel.showWebsiteWebView(context!!,it.redditURL)
                        val i=Intent(Intent.ACTION_VIEW)
                        i.data=Uri.parse(it.redditURL)
                        startActivity(i)
                    }
                }else{
                    binding.TextViewFragmentDetailsReddit.visibility=View.GONE
                }

                if (!it.websiteURL.equals("")){
                    binding.TextViewFragmentDetailsWebsite.setOnClickListener { v->
                        viewModel.showWebsiteWebView(context!!,it.websiteURL).show()
                    }
                }else{
                    binding.TextViewFragmentDetailsWebsite.visibility=View.GONE
                }
            }
        })
        viewModel.isSavedOnDB.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.ImageViewFragmentDetailsWishlist.setImageDrawable(context!!.getDrawable(R.drawable.ic_baseline_card_giftcard_24_green))
                }else{
                    binding.ImageViewFragmentDetailsWishlist.setImageDrawable(context!!.getDrawable(R.drawable.ic_baseline_card_giftcard_24))
                }
            }
        })
    }

    private fun setupComponents(){
        binding.ImageViewFragmentDetailsWishlist.setOnClickListener {
            if(viewModel.isSavedOnDB.value==true){
                viewModel.showAlertDialog(
                    context = context!!,
                    titleString = "Remove Game From Wishlist",
                    messageString = "Are you sure you wish to remove the game from your wishlist? ",
                    positiveString = "Yes",
                    negativeString = "No",
                    positiveOnclick = {Toast.makeText(context,"Deleted From Wishlist",Toast.LENGTH_SHORT).show();viewModel.deleteFromWishlist(context!!)},
                    negativeOnClick = null)
                    .show()
            }else{
                viewModel.insertIntoWishlist(context!!)
                Toast.makeText(context,"Inserted Into Wishlist",Toast.LENGTH_SHORT).show()
            }
        }
        binding.TextViewFragmentDetailsDescriptions.setOnClickListener{
            toggleReadMoreTextView(4)
        }
        binding.ButtonFragmentDetailsBackButton.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

    private fun toggleReadMoreTextView(linesWhenCollapsed: Int) {
        if (binding.TextViewFragmentDetailsDescriptions.maxLines != Integer.MAX_VALUE) {
            // expand
            binding.TextViewFragmentDetailsDescriptions.maxLines = Integer.MAX_VALUE
        } else {
            // collapse
            binding.TextViewFragmentDetailsDescriptions.maxLines = linesWhenCollapsed
        }
        // start animation
        TransitionManager.beginDelayedTransition(binding.LayoutFragmentDetailsConstraintlayout)
    }

    private fun getGameDetails(){
        arguments?.let {
            viewModel.getGameDetails(DetailsFragmentArgs.fromBundle(it).gameID,context!!)
        }
    }


}