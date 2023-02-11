package com.example.musicapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ArtistNameViewBinding
import com.example.musicapp.databinding.SongsListHolderBinding
import com.example.musicapp.model.domain.Song

/**
 * Defines the Adapter for the Main Fragment
 * Defines the ViewHolder for the songs adapters
 * Defines the ViewHolder for the artist names
 */

class ArtistsSongsAdapter(
    private val artistsSongsList: MutableList<ViewType> = mutableListOf(),
    private val onClickedSong: (Song) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Method that updates the dataset for the adapter
     */
    fun updateArtistsSongsList(newArtistsSongsList: List<Song>){
        var tempArtist = "+"
        val songsListTemp: MutableList<Song> = mutableListOf()
        val songsAdapterList: MutableList<SongsListAdapter> = mutableListOf()
        var i = 0
        newArtistsSongsList.sortedBy { it.artistName }.forEach {song ->
            val currentArtist = song.artistName ?: "+"
            if(currentArtist != tempArtist){ //check if artist is already added
                if(songsListTemp.size>0){
                    //add a new adapter to adapter list
                    songsAdapterList.add(SongsListAdapter(mutableListOf(),onClickedSong))
                    //update songs in the new adapter
                    songsAdapterList[i].updateSongs(songsListTemp)
                    //add new adapter to the artistSongsList
                    artistsSongsList.add(ViewType.SONGS_DATA(songsAdapterList[i]))
                    i++
                    songsListTemp.clear()
                }
                artistsSongsList.add(ViewType.ARTIST(currentArtist)) //add an artist
                tempArtist = currentArtist
            }
            songsListTemp.add(song) //add song to the list
        }
        songsListTemp.clear()
        notifyDataSetChanged()
    }

    /**
     * Creates a new ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 0){ //check if ViewHolder should be an artist
            ArtistsViewHolder(
                ArtistNameViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{ //check if ViewHolder should be an adapter
            SongsDataViewHolder(
                SongsListHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    /**
     * Get the size of the list of items
     */
    override fun getItemCount() = artistsSongsList.size

    /**
     * Bind the ViewHolders
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = artistsSongsList[position]){ //check the ViewType
            is ViewType.ARTIST ->
                (holder as ArtistsViewHolder).artistNameBinding(item.artist) //binds to artis
            is ViewType.SONGS_DATA ->
                (holder as SongsDataViewHolder).songsDataBinding(item.songsAdapter) //binds to songs
        }
    }

    /**
     * Returns weather the item is Artist or adapter
     */
    override fun getItemViewType(position: Int): Int {
        return when(artistsSongsList[position]){
            is ViewType.ARTIST -> 0
            is ViewType.SONGS_DATA -> 1
        }
    }
}

class SongsDataViewHolder(private val binding: SongsListHolderBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Bind the song adapter to the nested Recycler View
     */
    fun songsDataBinding(songsListAdapter: SongsListAdapter){
        //binds the recycler view
        //add a linear layout manager
        //add the adapter
        binding.rvSongsDataHolder.apply {
            adapter = songsListAdapter
            layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false)
        }
    }

}

class ArtistsViewHolder(private val binding: ArtistNameViewBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Bind the artist to the UI
     */
    fun artistNameBinding(artistName: String){
        binding.tvArtistName.text = artistName
    }

}