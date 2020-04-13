package com.babbel.fallingwords.repo

import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.data.source.WordsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class WordsRepositoryTest {

    @Mock
    lateinit var dataSource: WordsDataSource
    private lateinit var wordsRepository: WordsRepository

    private val mockedList = listOf(
        WordModel("holidays","vacaciones"),
        WordModel("group","grupo")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        wordsRepository = WordsRepository(dataSource)
    }

    @Test
    fun `fetch word list from data-source successfully`() = runBlockingTest{
        `when`(dataSource.fetchWords()).thenReturn(ResultState.Success(mockedList))

        val result = wordsRepository.fetchWords() as ResultState.Success
        assertEquals(result.data.isNotEmpty(),true)
        assertArrayEquals(result.data.toTypedArray(),mockedList.toTypedArray())
    }
}