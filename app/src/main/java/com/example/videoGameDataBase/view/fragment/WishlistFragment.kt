package com.example.videoGameDataBase.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoGameDataBase.databinding.FragmentWishlistBinding
import com.example.videoGameDataBase.view.GameListingAdapter
import com.example.videoGameDataBase.viewmodel.WishlistFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishlistFragment : Fragment() {
    private lateinit var binding : FragmentWishlistBinding
    private lateinit var gameListingAdapter: GameListingAdapter

    private val viewModel:WishlistFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentWishlistBinding.inflate(layoutInflater)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponents()
        observe()
        viewModel.getGames(context!!)

    }
    private fun gameListingAdapterOnClick(position:Int){
        val toDetailsFragment=WishlistFragmentDirections.actionWishlistFragmentToDetailsFragment(viewModel.listing.value!![position].id)
        findNavController().navigate(toDetailsFragment)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupComponents(){
        gameListingAdapter=GameListingAdapter(this::gameListingAdapterOnClick)
        binding.RecyclerViewFragmentListingRecv.layoutManager=GridLayoutManager(context,2)
        binding.RecyclerViewFragmentListingRecv.adapter=gameListingAdapter

        /*binding.RecyclerViewFragmentListingRecv.setOnTouchListener(View.OnTouchListener { view, motionEvent ->//TODO Might delete
            if (motionEvent.action == MotionEvent.ACTION_UP){
                if (!binding.RecyclerViewFragmentListingRecv.canScrollVertically(1)){
                    //viewModel.getNextData()
                }
            }
            false
        })*/
    }

    fun observe(){
        viewModel.listing.observe(viewLifecycleOwner, Observer {
            it?.let {
                gameListingAdapter.submitList(viewModel.listing.value)
            }
        })
    }
}