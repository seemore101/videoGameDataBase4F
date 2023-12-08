package com.example.videoGameDataBase.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoGameDataBase.common.UIModelListing
import com.example.videoGameDataBase.common.UIPlatforms
import com.example.videoGameDataBase.databinding.FragmentListingBinding
import com.example.videoGameDataBase.view.GameListingAdapter
import com.example.videoGameDataBase.view.adapter.PlatformListingAdapter
import com.example.videoGameDataBase.viewmodel.ListingFragmentViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class ListingFragment  : Fragment() {
    lateinit var binding: FragmentListingBinding
    private lateinit var gameListingAdapter:GameListingAdapter
    private lateinit var platformAdapter:PlatformListingAdapter
    private var isFirstTime=true
    private val viewModel:ListingFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentListingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        platformAdapter=PlatformListingAdapter(arrayListOf(),this::platformAdapterOnClicK)
        setupComponents()
        observeLiveData()
        if (isFirstTime){
            isFirstTime=false
            getFirstListings()
        }
    }

    private fun getFirstListings(){
        val listType=object : TypeToken<ArrayList<UIModelListing>>() {}.type
        val platformsType= object : TypeToken<ArrayList<UIPlatforms>>() {}.type

        //TODO: AntiPattern X
        viewModel.nextPageURL.value=activity!!.intent.getStringExtra("nextPageUrl")
        viewModel.listing.value=Gson().fromJson(activity!!.intent.getStringExtra("firstList"),listType)
        viewModel.platforms.value=Gson().fromJson(activity!!.intent.getStringExtra("platforms"),platformsType)
    }

    private fun platformAdapterOnClicK(position: Int){
        if (platformAdapter.selectedItemPos!=position && viewModel.selectedPlatformPosition.value != position){
            viewModel.setPlatformValues(platformAdapter.platformList[position].id,position)
        }
        else if (platformAdapter.selectedItemPos==position && viewModel.selectedPlatformPosition.value== position){
            viewModel.clearPlatformFilters()
        }
        viewModel.getNewListing()
    }

    private fun gameListingAdapterOnClick(position:Int){
        val toDetailsFragment=ListingFragmentDirections.actionListingFragmentToDetailsFragment(viewModel.listing.value!!.get(position).id)
        findNavController().navigate(toDetailsFragment)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupComponents(){

        binding.ButtonFragmentListingClearsearch.visibility=View.GONE

        binding.ButtonFragmentListingClearFilters.visibility=View.GONE

        binding.ProgressBarFragmentListingProgressbar.visibility=View.GONE

        gameListingAdapter=GameListingAdapter(this::gameListingAdapterOnClick)
        binding.RecyclerViewFragmentListingGameCards.layoutManager=GridLayoutManager(context,2)
        binding.RecyclerViewFragmentListingGameCards.adapter=gameListingAdapter

        binding.RecyclerViewFragmentListingPlatformFilters.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.RecyclerViewFragmentListingPlatformFilters.adapter=platformAdapter

        binding.RecyclerViewFragmentListingGameCards.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (!binding.RecyclerViewFragmentListingGameCards.canScrollVertically(1)) {
                    viewModel.getNextData(context!!)
                }
            }
            false
        })

        binding.ButtonFragmentListingClearsearch.setOnClickListener {
            viewModel.clearSearch()
            binding.SearchViewListingFragment.setQuery("",false)
            viewModel.getNewListing()
        }

        binding.ButtonFragmentListingClearFilters.setOnClickListener {
            viewModel.clearFilters()
            binding.SearchViewListingFragment.setQuery("",false)
            platformAdapter.clearHighlights()
            viewModel.getNewListing()
        }

        binding.SearchViewListingFragment.setOnClickListener {

        }

        binding.SearchViewListingFragment.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.setSearchValues(p0)
                viewModel.getNewListing()
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    fun observeLiveData(){
        viewModel.platforms.observe(viewLifecycleOwner, Observer {
            it?.let {
                platformAdapter.updatePlatformList(it)
            }
        })
        viewModel.listing.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.size==0){
                    binding.TextViewFragmentListingNoGames.visibility=View.VISIBLE
                    gameListingAdapter.submitList(it)
                }else{
                    gameListingAdapter.submitList(it)
                    binding.TextViewFragmentListingNoGames.visibility=View.GONE
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.RecyclerViewFragmentListingGameCards.visibility=View.GONE
                }
                if(!it){
                    binding.RecyclerViewFragmentListingGameCards.visibility=View.VISIBLE
                }
            }
        })
        viewModel.selectedPlatformPosition.observe(viewLifecycleOwner, Observer {
            it?.let {
                platformAdapter.highlightItem(it)
            }
        })
        viewModel.showPBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.ProgressBarFragmentListingProgressbar.visibility=View.VISIBLE
                }
                if (!it){
                    binding.ProgressBarFragmentListingProgressbar.visibility=View.GONE
                }
            }
        })
        viewModel.searchString.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it!=""){
                    binding.ButtonFragmentListingClearsearch.visibility=View.VISIBLE
                }else{
                    binding.ButtonFragmentListingClearsearch.visibility=View.GONE
                }
            }
        })
    }
}