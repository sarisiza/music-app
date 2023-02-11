package com.example.musicapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.musicapp.database.MusicDbRepository
import com.example.musicapp.model.domain.Song
import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.IncorrectQuery
import com.example.musicapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MusicViewModel"

/**
 * Application View Model
 */
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val musicDbRepository: MusicDbRepository
): ViewModel() {

    //fragment state
    private var _fragmentState: MutableLiveData<Boolean> = MutableLiveData(false)
    val fragmentState: LiveData<Boolean> get() = _fragmentState

    //Rock States
    private val _rockMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val rockMusic: LiveData<UIState> get() = _rockMusic

    //Classic States
    private val _classicMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val classicMusic: LiveData<UIState> get() = _classicMusic

    //Pop States
    private val _popMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val popMusic: LiveData<UIState> get() = _popMusic

    //Database livedata
    val rockMusicList: LiveData<List<Song>> = musicDbRepository.allRockSongs.asLiveData()
    val classicMusicList: LiveData<List<Song>> = musicDbRepository.allClassicSongs.asLiveData()
    val popMusicList: LiveData<List<Song>> = musicDbRepository.allPopSongs.asLiveData()

    //size of the database
    val rockListSize = musicDbRepository.rockListSize.asLiveData()
    val popListSize = musicDbRepository.popListSize.asLiveData()
    val classicListSize = musicDbRepository.classicListSize.asLiveData()

    //item selected
    private var _itemSelected: MutableLiveData<Song> = MutableLiveData()
    val itemSelected: LiveData<Song> get() = _itemSelected

    init {
        //update the fragment state
        updateFragmentState(false)
    }

    /**
     * Method to get songs
     */
    fun getSongs(genre: Genres){
        if(songListIsEmpty(genre)) { //check if database is empty
            //make network calls
            viewModelScope.launch(ioDispatcher) {
                when (genre) {
                    Genres.ROCK -> { //rock network call
                        musicRepository.getAllSongs(genre).collect { state ->
                            _rockMusic.postValue(state)
                        }
                    }
                    Genres.POP -> { //pop network call
                        musicRepository.getAllSongs(genre).collect { state ->
                            _popMusic.postValue(state)
                        }
                    }
                    Genres.CLASSIC -> { //classic network call
                        musicRepository.getAllSongs(genre).collect { state ->
                            _classicMusic.postValue(state)
                        }
                    }
                }
            }
        } else{
            // get information from the database
            Log.d(TAG, "getSongs: got to db")
            when(genre){
                Genres.ROCK -> { //rock
                    if(rockMusicList.value != null){
                        //post database into state
                        _rockMusic.postValue(UIState.SUCCESS(rockMusicList.value as List<Song>))
                    } else{
                        //handle error
                        _rockMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
                Genres.POP -> { //pop
                    if(popMusicList.value != null){
                        //post database into state
                        _popMusic.postValue(UIState.SUCCESS(popMusicList.value as List<Song>))
                    } else{
                        //handle error
                        _popMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
                Genres.CLASSIC -> { //classic
                    if(classicMusicList.value != null){
                        //post database into state
                        _classicMusic.postValue(UIState.SUCCESS(classicMusicList.value as List<Song>))
                    } else{
                        //handle error
                        _classicMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
            }
        }
    }

    /**
     * Method to insert songs into the database
     */
    fun updateSongsDatabaseByGenre(genre: Genres){
        if(songListIsEmpty(genre)){ //check if database is empty
            viewModelScope.launch(ioDispatcher) {
                when(genre){
                    Genres.ROCK -> { //update rock
                        when(rockMusic.value){
                            is UIState.ERROR -> { //handle error
                                throw IncorrectQuery()
                            }
                            UIState.LOADING -> {
                                updateSongsDatabaseByGenre(genre) //wait until data is ready
                            }
                            is UIState.SUCCESS -> {
                                (rockMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it) //insert song
                                }

                            }
                            null -> { //handle null
                                throw IncorrectQuery()
                            }
                        }
                    }
                    Genres.POP -> { //update pop
                        when(popMusic.value){
                            is UIState.ERROR -> { //handle error
                                throw IncorrectQuery()
                            }
                            UIState.LOADING -> {
                                updateSongsDatabaseByGenre(genre) //wait until data is ready
                            }
                            is UIState.SUCCESS -> {
                                (popMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it) //insert song
                                }
                            }
                            null -> { //handle null
                                throw IncorrectQuery()
                            }
                        }
                    }
                    Genres.CLASSIC -> { //update classic
                        when(classicMusic.value){
                            is UIState.ERROR -> { //handle error
                                throw IncorrectQuery()
                            }
                            UIState.LOADING -> {
                                updateSongsDatabaseByGenre(genre) //wait until data is ready
                            }
                            is UIState.SUCCESS -> {
                                (classicMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it) //insert song
                                }
                            }
                            null -> { //handle null
                                throw IncorrectQuery()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to check if the database is empty
     */
    fun songListIsEmpty(genre: Genres): Boolean{
        when(genre){
            Genres.ROCK -> { //check rock
                if((rockListSize.value?:0) > 0)
                    return false
            }
            Genres.POP -> { //check pop
                if((popListSize.value?:0) > 0)
                    return false
            }
            Genres.CLASSIC -> { //check classic
                if((classicListSize.value?:0) > 0)
                    return false
            }
        }
        return true
    }

    /**
     * Method to update the selected item
     */
    fun selectItem(song: Song){
        _itemSelected.postValue(song)
    }

    /**
     * Method to update the fragment state
     */
    fun updateFragmentState(stateChange: Boolean){
        _fragmentState.postValue(stateChange)
    }

}