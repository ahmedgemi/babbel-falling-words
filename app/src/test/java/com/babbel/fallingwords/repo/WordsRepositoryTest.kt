package com.babbel.fallingwords.repo

import com.babbel.fallingwords.FakeWordsDataSource
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

    private val fakeWordsDataSource = FakeWordsDataSource()
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setUp() {
        wordsRepository = WordsRepository(fakeWordsDataSource)
    }

    @Test
    fun `fetch word list from data-source successfully`() = runBlockingTest{
        assertEquals(wordsRepository.fetchWords().isNotEmpty(),true)
        assertArrayEquals(wordsRepository.fetchWords().toTypedArray(),fakeWordsDataSource.fetchWords().toTypedArray())
    }
}