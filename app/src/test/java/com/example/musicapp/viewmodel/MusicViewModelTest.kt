package com.example.musicapp.viewmodel

import com.example.musicapp.database.MusicDbRepository
import com.example.musicapp.rest.MusicRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


internal class MusicViewModelTest {

    private lateinit var testObject: MusicViewModel

    private val mockRepository = mockk<MusicRepository>(relaxed = true)
    private val mockDispatcher = UnconfinedTestDispatcher()
    private val mockDbRepository = mockk<MusicDbRepository>(relaxed = true)

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


}