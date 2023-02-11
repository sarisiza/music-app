package com.example.musicapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.databinding.SongViewBinding
import com.example.musicapp.model.SongItem
import com.example.musicapp.model.domain.Song
import com.squareup.picasso.Picasso

/**
 * Updates the Adapter and the View Holder for the songs list
 */
class SongsListAdapter(
    private val songsList: MutableList<Song> = mutableListOf(),
    private val onClickedSong: (Song) -> Unit
): RecyclerView.Adapter<SongsListViewHolder>() {

    /**
     * Updates the songs list
     */
    fun updateSongs(newSongs: List<Song>){
        if(songsList != newSongs){
            songsList.addAll(newSongs) //add songs list to adapter
        }
        notifyDataSetChanged()
    }

    /**
     * Creates the View Holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsListViewHolder {
        return SongsListViewHolder(
            SongViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Gets the size of the songs list
     */
    override fun getItemCount() = songsList.size

    /**
     * Binds the UI to the ViewHolder
     */
    override fun onBindViewHolder(holder: SongsListViewHolder, position: Int) {
        holder.songsBinding(songsList[position],onClickedSong)
    }

}

class SongsListViewHolder(private val binding: SongViewBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Definse the binding for the ViewHolder
     */
    fun songsBinding(song: Song, onClickedSong: (Song) -> Unit){
        binding.tvSongName.text = song.trackName
        binding.tvArtistName.text = song.artistName
        binding.tvSongPrice.text = song.trackPrice.toString()
        //calling to Picasso for image processing
        Picasso
            .get()
            .load(song.artworkUrl60)
            .centerCrop()
            .resize(700,0)
            .placeholder(R.drawable.ic_image_search_24)
            .error(R.drawable.ic_broken_image_24)
            .into(binding.ivSongCover)
        //set a clickListener
        itemView.setOnClickListener {
            song.let(onClickedSong)
        }
    }

}