package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemMusicBinding

class MusicRVAdapter(private val musiclist: ArrayList<Album>): RecyclerView.Adapter<MusicRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    fun removeItem(position: Int) {
        musiclist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MusicRVAdapter.ViewHolder {
        val binding: ItemMusicBinding = ItemMusicBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicRVAdapter.ViewHolder, position: Int) {
        holder.bind(musiclist[position])
        holder.binding.playerMoreIv.setOnClickListener { myItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = musiclist.size

    inner class ViewHolder(val binding: ItemMusicBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.titleTv.text = album.title
            binding.singerTv.text = album.singer
            binding.itemMusicCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}