package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumlist: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
        fun onItemClick(album: Album)
        fun onRemoveAlbum(position: Int)
        fun onPlayAlbum(album: Album)
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }

    fun addItem(album: Album) {
        albumlist.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        albumlist.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) {
        holder.bind(albumlist[position])
        holder.itemView.setOnClickListener { myItemClickListener.onItemClick(albumlist[position]) }

//        holder.binding.itemAlbumTitleTv.setOnClickListener { myItemClickListener.onRemoveAlbum(position) }
    }

    override fun getItemCount(): Int = albumlist.size

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
            binding.itemAlbumPlayImgIv.setOnClickListener {
                myItemClickListener.onPlayAlbum(album)
            }
        }
    }

}