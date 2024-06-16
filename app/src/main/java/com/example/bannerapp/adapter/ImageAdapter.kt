package com.example.bannerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bannerapp.ImageItem
import com.example.bannerapp.R
import com.example.bannerapp.databinding.ItemImageBinding

class ImageAdapter(
    private val imagesList: ArrayList<ImageItem>,
    private val action: (ImageItem) -> Unit
) :
    RecyclerView.Adapter<ImageViewHolder>() {

    private lateinit var binding: ItemImageBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ItemImageBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imagesList[position]
        holder.bind(currentItem, action)
    }

    fun updateList(list: ArrayList<ImageItem>) {
        imagesList.clear()
        imagesList.addAll(list)
        notifyDataSetChanged()
    }
}

class ImageViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(imageItem: ImageItem, action: (ImageItem) -> Unit) {
        binding.imageView.setImageResource(imageItem.image)
        binding.imageView.setOnClickListener {
            action(imageItem)
        }
    }
}