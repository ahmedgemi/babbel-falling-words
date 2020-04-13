package com.babbel.fallingwords.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babbel.fallingwords.Constants
import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.QuestionModel
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.repo.WordsRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class GameViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
): ViewModel(){

    /**
     * store all game data in ViewModel to survive configuration changes
     * like switch to landscape
     */
    val wordsList = ArrayList<WordModel>()

    private var score = 0
    private var lives = Constants.LIVES_COUNT

    //current correct word
    var currentWordIndex = 0
        get() = field

    /**
     * QuestionModel: represent the (word & translation) that appear to user
     * and the translation of QuestionModel maybe correct or wrong
     */
    private lateinit var currentQuestion: QuestionModel

    val questionLiveData = MutableLiveData<QuestionModel>()
    val scoreLiveData = MutableLiveData<Int>(score)
    val livesLiveData = MutableLiveData<Int>(lives)
    val errorLiveData = MutableLiveData<String>()

    //notify the view when the game is over and passing the final score to display
    val gameOverLiveData = MutableLiveData<Int>()

    init {
        loadWords()
    }

    /**
     * Loading words from data-layer and store it in wordsList
     */
    fun loadWords(){
        viewModelScope.launch{
            val result = wordsRepository.fetchWords()
            when (result){
                is ResultState.Success->{
                    wordsList.addAll(result.data)
                    //shuffle words list so user can get new words each time (no in the same order)
                    Collections.shuffle(wordsList)

                    sendNewWord()
                }
                is ResultState.Error->{
                    errorLiveData.value = "Game Loading Error.."
                }
            }
        }
    }

    /**
     * If user doesn't select any answer
     */
    fun onNoAnswer(){
        livesLiveData.value = --lives
        sendNewWord()
    }

    /**
     * If user select the current question translation is correct
     */
    fun onCorrectTransClicked(){
        if(currentQuestion.translation == wordsList[currentWordIndex].textSpa){
            score += 10
            scoreLiveData.value = score
        }
        else {
            livesLiveData.value = --lives
        }

        sendNewWord()
    }

    /**
     * If user select the current question translation is wrong
     */
    fun onWrongTransClicked(){
        if(currentQuestion.translation != wordsList[currentWordIndex].textSpa){
            score += 10
            scoreLiveData.value = score
        }
        else {
            livesLiveData.value = --lives
        }

        sendNewWord()
    }

    /**
     * this function for loading new question and pass it to the view
     * and validating is the game should continue or GameOver
     */
    fun sendNewWord(){
        //if user finish all words or lose all his lives
        if(currentWordIndex >= wordsList.size || lives<= 0){
            gameOverLiveData.value = score
            return
        }

        currentWordIndex++

        if(currentWordIndex == wordsList.size-1){ //handle last question in the list scenario
            //use the correct translation
            QuestionModel(wordsList[currentWordIndex].textEng,wordsList[currentWordIndex].textSpa)
        }
        else {
            //generate random number to decide to show the correct translation or wrong one
            val rnd = if(wordsList.size > 1) (0..wordsList.size-1).random() else 0
            currentQuestion = if(rnd%2 == 0){
                //use the correct translation
                QuestionModel(wordsList[currentWordIndex].textEng,wordsList[currentWordIndex].textSpa)
            }
            else {
                //use wrong translation
                QuestionModel(wordsList[currentWordIndex].textEng,wordsList[rnd].textSpa)
            }
        }

        questionLiveData.value = currentQuestion
    }
}