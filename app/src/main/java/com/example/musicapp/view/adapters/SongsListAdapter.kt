package com.example.musicapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.SongViewBinding
import com.example.musicapp.model.SongItem
import com.squareup.picasso.Picasso

class SongsListAdapter(
    private val songsList: MutableList<SongItem> = mutableListOf(),
    private val onClickedSong: (Int) -> Unit
): RecyclerView.Adapter<SongsListViewHolder>() {

    fun updateSongs(newSongs: List<SongItem>){
        if(songsList != newSongs){
            songsList.addAll(newSongs)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        return SongsListViewHolder(
            SongViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = songsList.size

    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.songsBinding(songsList[position],onClickedSong)
    }

}

class SongsListViewHolder(private val binding: SongViewBinding): RecyclerView.ViewHolder(binding.root){

    fun songsBinding(song: SongItem, onClickedSong: (Int) -> Unit){
        binding.tvSongName.text = song.trackName
        binding.tvArtistName.text = song.artistName
        binding.tvSongPrice.text = song.trackPrice.toString()
        Picasso
            .get()
            .load(song.artworkUrl60)
            .centerCrop()
            .resize(700,0)
            .placeholder(R.drawable.ic_image_search_24)
            .error(R.drawable.ic_broken_image_24)
            .into(binding.ivSongCover)
        itemView.setOnClickListener {
            song.trackId?.let(onClickedSong)
        }
    }

}