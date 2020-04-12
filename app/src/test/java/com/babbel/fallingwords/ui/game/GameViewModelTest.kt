package com.babbel.fallingwords.ui.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.babbel.fallingwords.Constants
import com.babbel.fallingwords.FakeWordsDataSource
import com.babbel.fallingwords.getOrAwaitValue
import com.babbel.fallingwords.repo.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GameViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val fakeWordsDataSource = FakeWordsDataSource()
    private lateinit var wordsRepository: WordsRepository
    private lateinit var viewModel: GameViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        wordsRepository = WordsRepository(fakeWordsDataSource)
        viewModel = GameViewModel(wordsRepository)
    }

    @Test
    fun `test loading word list after create viewModel instance`() = runBlockingTest {
        val fakeList = fakeWordsDataSource.fetchWords()
        assertEquals(viewModel.wordsList.isNotEmpty(),true)
        assertEquals(viewModel.wordsList.size,fakeList.size)

        //test word list is shuffled, so user can see different words every time
        if(viewModel.wordsList.size > 1)
            assertNotEquals(viewModel.wordsList[0],fakeList[0])
    }

    @Test
    fun `test viewModel liveDatas initialized with default values`() = runBlockingTest {
        val lives = viewModel.livesLiveData.getOrAwaitValue()
        val score = viewModel.scoreLiveData.getOrAwaitValue()

        assertEquals(lives,Constants.LIVES_COUNT)
        assertEquals(score,0)
    }

    @Test
    fun `test select the correct answer will increase the score`() = runBlockingTest {
        val currentWord = viewModel.wordsList[viewModel.currentWordIndex]
        val question = viewModel.questionLiveData.getOrAwaitValue()

        if(question.translation == currentWord.textSpa)
            viewModel.onCorrectTransClicked()
        else
            viewModel.onWrongTransClicked()

        val score = viewModel.scoreLiveData.getOrAwaitValue()
        assertEquals(score,10)
    }

    @Test
    fun `test select the wrong answer will decrease the lives`() = runBlockingTest {
        for (i in 0..Constants.LIVES_COUNT-1){
            val currentWord = viewModel.wordsList[viewModel.currentWordIndex]
            val question = viewModel.questionLiveData.getOrAwaitValue()

            if(question.translation == currentWord.textSpa)
                viewModel.onWrongTransClicked()
            else
                viewModel.onCorrectTransClicked()

            val lives = viewModel.livesLiveData.getOrAwaitValue()
            assertEquals(lives,Constants.LIVES_COUNT-i-1)
        }

        val score = viewModel.gameOverLiveData.getOrAwaitValue()
        assertEquals(score,0)
    }

    @Test
    fun `test select NO wrong answer will decrease the lives`() = runBlockingTest {
        for (i in 0..Constants.LIVES_COUNT-1){
            viewModel.onNoAnswer()
            val lives = viewModel.livesLiveData.getOrAwaitValue()
            assertEquals(lives,Constants.LIVES_COUNT-i-1)
        }

        val score = viewModel.gameOverLiveData.getOrAwaitValue()
        assertEquals(score,0)
    }
}