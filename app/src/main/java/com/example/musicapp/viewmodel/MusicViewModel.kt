package com.example.musicapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.model.domain.Song
import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.IncorrectQuery
import com.example.musicapp.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MusicViewModel"
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    var fragmentState = false

    private val _rockMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val rockMusic: LiveData<UIState> get() = _rockMusic

    private val _classicMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val classicMusic: LiveData<UIState> get() = _classicMusic

    private val _popMusic: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING)
    val popMusic: LiveData<UIState> get() = _popMusic

    private val _rockMusicList: MutableLiveData<List<Song>> = MutableLiveData()
    val rockMusicList: LiveData<List<Song>> get() = _rockMusicList

    private val _classicMusicList: MutableLiveData<List<Song>> = MutableLiveData()
    val classicMusicList: LiveData<List<Song>> get() = _classicMusicList

    private val _popMusicList: MutableLiveData<List<Song>> = MutableLiveData()
    val popMusicList: LiveData<List<Song>> get() = _popMusicList

    private var _itemSelected: MutableLiveData<Song> = MutableLiveData()
    val itemSelected: LiveData<Song> get() = _itemSelected

    private var _currentTab: MutableLiveData<Genres> = MutableLiveData(Genres.ROCK)
    val currentTab: LiveData<Genres> get() = _currentTab

    init {
        getSongs(Genres.ROCK)
        getSongs(Genres.POP)
        getSongs(Genres.CLASSIC)
    }

    fun getSongs(genre: Genres){
        viewModelScope.launch(ioDispatcher){
            when(genre){
                Genres.ROCK -> {
                    musicRepository.getAllSongs(genre).collect{
                        _rockMusic.postValue(it)
                        if(it is UIState.SUCCESS)
                            _rockMusicList.postValue(it.response)
                    }
                }
                Genres.POP -> {
                    musicRepository.getAllSongs(genre).collect{
                        _popMusic.postValue(it)
                        if(it is UIState.SUCCESS)
                            _popMusicList.postValue(it.response)
                    }
                }
                Genres.CLASSIC -> {
                    musicRepository.getAllSongs(genre).collect{
                        _classicMusic.postValue(it)
                        if(it is UIState.SUCCESS)
                            _classicMusicList.postValue(it.response)
                    }
                }
            }
        }
    }

    fun selectItem(song: Song){
        _itemSelected.postValue(song)
    }

    fun updateCurrentTab(genre: Genres){
        _currentTab.postValue(genre)
    }

}