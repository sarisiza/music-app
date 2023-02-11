package com.example.musicapp.rest

import com.example.musicapp.model.MusicResponse
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


internal class MusicRepositoryImplTest {

    private lateinit var testObject: MusicRepository
    private val mockServiceApi = mockk<MusicApi>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
        testObject = MusicRepositoryImpl(mockServiceApi)

    }

    @After
    fun tearDown() {

        Dispatchers.resetMain()
        clearAllMocks()

    }

    @Test
    fun `get all rock songs when server is a success response returns a list of all rock songs`() {
        //ASSIGN
        coEvery { mockServiceApi.getSongsList(Genres.ROCK.genre) } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns MusicResponse(songItems = listOf(
                mockk{
                    every { trackId } returns 1
                    every { trackName } returns "a"
                    every { artistName } returns "a"
                    every { artworkUrl60 } returns "a"
                    every { collectionName } returns "a"
                    every { previewUrl } returns "a"
                    every { releaseDate } returns "a"
                    every { trackPrice } returns 1.1
                },
                mockk{
                    every { trackId } returns 2
                    every { trackName } returns "b"
                    every { artistName } returns "b"
                    every { artworkUrl60 } returns "b"
                    every { collectionName } returns "b"
                    every { previewUrl } returns "b"
                    every { releaseDate } returns "b"
                    every { trackPrice } returns 1.1
                },
                mockk{
                    every { trackId } returns 3
                    every { trackName } returns "c"
                    every { artistName } returns "c"
                    every { artworkUrl60 } returns "c"
                    every { collectionName } returns "c"
                    every { previewUrl } returns "c"
                    every { releaseDate } returns "c"
                    every { trackPrice } returns 1.1
                }))
        }

        //ACTION
        var state: UIState = UIState.LOADING
        val job = testScope.launch {
            testObject.getAllSongs(Genres.ROCK).collect {
                state = it
            }
        }

        //ASSESS
        assert(state is UIState.SUCCESS)
        //assertEquals(3, (state as UIState.SUCCESS).response.size)

    }
}
