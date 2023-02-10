package com.example.musicapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.databinding.ArtistNameViewBinding
import com.example.musicapp.databinding.SongsListHolderBinding
import com.example.musicapp.model.SongItem
import com.example.musicapp.model.domain.Song
import com.example.musicapp.utils.ViewType

class ArtistsSongsAdapter(
    private val artistsSongsList: MutableList<ViewType> = mutableListOf(),
    private val onClickedSong: (Song) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateArtistsSongsList(newArtistsSongsList: List<Song>){
        var tempArtist = "+"
        val songsListTemp: MutableList<Song> = mutableListOf()
        val songsAdapterList: MutableList<SongsListAdapter> = mutableListOf()
        var i = 0
        newArtistsSongsList.sortedBy { it.artistName }.forEach {song ->
            val currentArtist = song.artistName ?: "+"
            if(currentArtist != tempArtist){
                if(songsListTemp.size>0){
                    songsAdapterList.add(SongsListAdapter(mutableListOf(),onClickedSong))
                    songsAdapterList[i].updateSongs(songsListTemp)
                    artistsSongsList.add(ViewType.SONGS_DATA(songsAdapterList[i]))
                    i++
                    songsListTemp.clear()
                }
                artistsSongsList.add(ViewType.ARTIST(currentArtist))
                tempArtist = currentArtist
            }
            songsListTemp.add(song)
        }
        songsListTemp.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 0){
            ArtistsViewHolder(
                ArtistNameViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            SongsDataViewHolder(
                SongsListHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount() = artistsSongsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = artistsSongsList[position]){
            is ViewType.ARTIST ->
                (holder as ArtistsViewHolder).artistNameBinding(item.artist)
            is ViewType.SONGS_DATA ->
                (holder as SongsDataViewHolder).songsDataBinding(item.songsAdapter)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(artistsSongsList[position]){
            is ViewType.ARTIST -> 0
            is ViewType.SONGS_DATA -> 1
        }
    }
}

class SongsDataViewHolder(private val binding: SongsListHolderBinding): RecyclerView.ViewHolder(binding.root){

    fun songsDataBinding(songsListAdapter: SongsListAdapter){
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

    fun artistNameBinding(artistName: String){
        binding.tvArtistName.text = artistName
    }

}