package com.example.videoGameDataBase.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.videoGameDataBase.R
import com.example.videoGameDataBase.common.UIPlatforms

class PlatformListingAdapter(var platformList:ArrayList<UIPlatforms>,val itemClick:(position:Int)->Unit):RecyclerView.Adapter<PlatformViewHolder>() {
    var selectedItemPos=-1
    var previousSelectedItemPos=-1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlatformViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.platform_filter_card,parent,false)
        return PlatformViewHolder(view)
    }
    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        holder.bindData(platformList[position])
        if (selectedItemPos==position){
            holder.filterName.background=AppCompatResources.getDrawable(holder.itemView.context,R.drawable.selected_platform_filter_card_rounded)
            holder.filterName.setTextColor(holder.itemView.context.resources.getColor(R.color.filter_card_bg_color))
        }else{
            holder.filterName.background=AppCompatResources.getDrawable(holder.itemView.context,R.drawable.platform_filter_card_rounded)
            holder.filterName.setTextColor(holder.itemView.context.resources.getColor(R.color.white))
        }
        holder.itemView.setOnClickListener {
            itemClick(position)
        }
    }
    override fun getItemCount(): Int {
        return platformList.size
    }
    fun updatePlatformList(newList:ArrayList<UIPlatforms>){
        platformList=newList
        notifyDataSetChanged()
    }
    fun highlightItem(position: Int){
        previousSelectedItemPos=selectedItemPos
        selectedItemPos=position
        notifyItemChanged(selectedItemPos)
        notifyItemChanged(previousSelectedItemPos)
    }
    fun clearHighlights(){
        notifyItemChanged(selectedItemPos)
        selectedItemPos=-1
        previousSelectedItemPos=-1
    }
}

class PlatformViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var filterName:TextView
    init {
        filterName=itemView.findViewById(R.id.TextView_platform_filter_card_name)
    }
    fun bindData(platform:UIPlatforms){
        filterName.text=platform.name
    }
}
