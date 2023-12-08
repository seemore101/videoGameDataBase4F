package com.example.videoGameDataBase.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoGameDataBase.R
import com.example.videoGameDataBase.common.UIModelListing

class GameListingAdapter(val itemClick:(position:Int)->Unit):ListAdapter<UIModelListing, GameListingAdapter.UIModelListingViewHolder>(
    UIModelListingDiffCallback()
) {
    class UIModelListingViewHolder(view: View):RecyclerView.ViewHolder(view){
        var gameImage:ImageView
        var gameName:TextView
        init {
            gameImage=itemView.findViewById(R.id.ImageView_game_card_Image)
            gameName=itemView.findViewById(R.id.TextView_game_card_Name)
        }
        fun bindData(game:UIModelListing){
            gameName.text=game.name
            Glide.with(itemView.context).load(game.backgroundImage).into(gameImage)
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UIModelListingViewHolder {
        var itemView=LayoutInflater.from(parent.context).inflate(R.layout.game_card,parent,false)
        return UIModelListingViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: UIModelListingViewHolder,
        position: Int
    ) {
        holder.bindData(getItem(position))
        holder.itemView.setOnClickListener {
            itemClick(position)
        }
    }
}
class UIModelListingDiffCallback: DiffUtil.ItemCallback<UIModelListing>() {
    override fun areItemsTheSame(oldItem: UIModelListing, newItem: UIModelListing): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: UIModelListing, newItem: UIModelListing): Boolean {
        return oldItem==newItem //TODO:
    }

}
