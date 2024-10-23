package com.nassican.apiapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nassican.apiapp.data.models.Result
import com.nassican.apiapp.databinding.RecyclerLayoutBinding

class GOTRecyclerView(private val onItemClick: (Result) -> Unit) : RecyclerView.Adapter<GOTRecyclerView.ViewHolder>() {

    private var characters = emptyList<Result>()

    fun setData(newCharacters: List<Result>) {
        characters = newCharacters
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RecyclerLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Result) {
            binding.showName.text = character.fullName
            Glide.with(binding.root.context)
                .load(character.imageUrl)
                .into(binding.characterImage)

            itemView.setOnClickListener {
                onItemClick(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size
}