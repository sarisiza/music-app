package com.example.musicapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.musicapp.database.MusicDbRepository
import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class MusicViewModelTest {

    private lateinit var testObject: MusicViewModel

    private val mockRepository = mockk<MusicRepository>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()
    private val mockDbRepository = mockk<MusicDbRepository>(relaxed = true)


    @get:Rule val instantTask = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = MusicViewModel(mockRepository,mockDispatcher, mockDbRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get songs when the repository retrieves a list of rock songs returns success state`(){
        //ASSIGN
        every { mockRepository.getAllSongs(Genres.ROCK) } returns flowOf(
            UIState.SUCCESS(listOf(mockk(),mockk(),mockk()))
        )

        //ACTION
        testObject.getSongs(Genres.ROCK)

        //ASSERT
        testObject.rockMusic.observeForever {
            when(it){
                is UIState.ERROR -> {}
                UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    assertEquals(3,it.response.size)
                }
            }
        }
    }

}