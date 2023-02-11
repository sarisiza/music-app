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
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val musicDbRepository: MusicDbRepository
): ViewModel() {

    private var _fragmentState: MutableLiveData<Boolean> = MutableLiveData(false)
    val fragmentState: LiveData<Boolean> get() = _fragmentState

    private val _rockMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val rockMusic: LiveData<UIState> get() = _rockMusic

    private val _classicMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val classicMusic: LiveData<UIState> get() = _classicMusic

    private val _popMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val popMusic: LiveData<UIState> get() = _popMusic

    val rockMusicList: LiveData<List<Song>> = musicDbRepository.allRockSongs.asLiveData()

    val classicMusicList: LiveData<List<Song>> = musicDbRepository.allRockSongs.asLiveData()

    val popMusicList: LiveData<List<Song>> = musicDbRepository.allRockSongs.asLiveData()

    val rockListSize = musicDbRepository.rockListSize.asLiveData()

    val popListSize = musicDbRepository.popListSize.asLiveData()

    val classicListSize = musicDbRepository.classicListSize.asLiveData()

    private var _itemSelected: MutableLiveData<Song> = MutableLiveData()
    val itemSelected: LiveData<Song> get() = _itemSelected

    init {
        updateFragmentState(false)
    }

    fun getSongs(genre: Genres){
        if(songListIsEmpty(genre)) {
            viewModelScope.launch(ioDispatcher) {
                when (genre) {
                    Genres.ROCK -> {
                        musicRepository.getAllSongs(genre).collect { state ->
                            _rockMusic.postValue(state)
                        }
                    }
                    Genres.POP -> {
                        musicRepository.getAllSongs(genre).collect { state ->
                            _popMusic.postValue(state)
                        }
                    }
                    Genres.CLASSIC -> {
                        musicRepository.getAllSongs(genre).collect { state ->
                            _classicMusic.postValue(state)
                        }
                    }
                }
            }
        } else{
            Log.d(TAG, "getSongs: got to db")
            when(genre){
                Genres.ROCK -> {
                    if(rockMusicList.value != null){
                        _rockMusic.postValue(UIState.SUCCESS(rockMusicList.value as List<Song>))
                    } else{
                        _rockMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
                Genres.POP -> {
                    if(popMusicList.value != null){
                        _popMusic.postValue(UIState.SUCCESS(popMusicList.value as List<Song>))
                    } else{
                        _popMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
                Genres.CLASSIC -> {
                    if(classicMusicList.value != null){
                        _classicMusic.postValue(UIState.SUCCESS(classicMusicList.value as List<Song>))
                    } else{
                        _classicMusic.postValue(UIState.ERROR(IncorrectQuery()))
                    }
                }
            }
        }
    }

    fun updateSongsDatabaseById(genre: Genres){
        if(songListIsEmpty(genre)){
            viewModelScope.launch(ioDispatcher) {
                when(genre){
                    Genres.ROCK -> {
                        when(rockMusic.value){
                            is UIState.ERROR -> {}
                            UIState.LOADING -> {
                                updateSongsDatabaseById(genre)
                            }
                            is UIState.SUCCESS -> {
                                Log.d(TAG, "getSongs: got here - rock")
                                (rockMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it)
                                }

                            }
                            null -> {}
                        }
                    }
                    Genres.POP -> {
                        when(popMusic.value){
                            is UIState.ERROR -> {}
                            UIState.LOADING -> {
                                updateSongsDatabaseById(genre)
                            }
                            is UIState.SUCCESS -> {
                                Log.d(TAG, "getSongs: got here - pop")
                                (popMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it)
                                }
                            }
                            null -> {}
                        }
                    }
                    Genres.CLASSIC -> {
                        when(classicMusic.value){
                            is UIState.ERROR -> {}
                            UIState.LOADING -> {
                                updateSongsDatabaseById(genre)
                            }
                            is UIState.SUCCESS -> {
                                Log.d(TAG, "getSongs: got here - classic")
                                (classicMusic.value as UIState.SUCCESS).response.forEach{
                                    musicDbRepository.insertSong(it)
                                }
                            }
                            null -> {}
                        }
                    }
                }
            }
        }
    }

    fun songListIsEmpty(genre: Genres): Boolean{
        when(genre){
            Genres.ROCK -> {
                if((rockListSize.value?:0) > 0)
                    return false
            }
            Genres.POP -> {
                if((popListSize.value?:0) > 0)
                    return false
            }
            Genres.CLASSIC -> {
                if((classicListSize.value?:0) > 0)
                    return false
            }
        }
        return true
    }

    fun selectItem(song: Song){
        _itemSelected.postValue(song)
    }

    fun updateFragmentState(stateChange: Boolean){
        _fragmentState.postValue(stateChange)
    }

}