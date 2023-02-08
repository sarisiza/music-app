package com.example.musicapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.rest.MusicRepository
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

    init {
        getSongs("rock")
        getSongs("pop")
        getSongs("classick")
    }

    fun getSongs(genre: String){
        viewModelScope.launch(ioDispatcher){
            when(genre){
                "rock" -> {
                    musicRepository.getAllSongs(genre).collect{
                        _rockMusic.postValue(it)
                        Log.d(TAG, "Rock: $it")
                    }
                }
                "pop" -> {
                    musicRepository.getAllSongs(genre).collect{
                        _popMusic.postValue(it)
                        Log.d(TAG, "pop: $it")
                    }
                }
                "classick" -> {
                    musicRepository.getAllSongs(genre).collect{
                        _classicMusic.postValue(it)
                        Log.d(TAG, "Classic: $it")
                    }
                }
                else -> throw IncorrectQuery()
            }
        }
    }

}