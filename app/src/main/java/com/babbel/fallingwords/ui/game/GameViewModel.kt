package com.babbel.fallingwords.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babbel.fallingwords.data.model.QuestionModel
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.repo.WordsRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class GameViewModel @Inject constructor(
    val wordsRepository: WordsRepository
): ViewModel(){

    //private lateinit var wordsRepository: WordsRepository
    private val wordsList = ArrayList<WordModel>()
    private var score = 0
    private var lifes = 3
    private lateinit var currentWord: WordModel
    private lateinit var currentQuestion: QuestionModel

    val questionLiveData = MutableLiveData<QuestionModel>()
    val scoreLiveData = MutableLiveData<Int>(score)
    val lifesLiveData = MutableLiveData<Int>(lifes)
    val gameOverLiveData = MutableLiveData<Int>()

    init {
        loadWords()
    }

    fun loadWords(){
        viewModelScope.launch{
            val list = wordsRepository.fetchWords()
            wordsList.addAll(list)
            Collections.shuffle(wordsList)
            setNewWord()
        }
    }

    fun onNoAnswer(){
        lifesLiveData.value = --lifes
        setNewWord()
    }

    fun onCorrectTransClicked(){
        if(currentQuestion.translation == currentWord.textSpa){
            score += 10
            scoreLiveData.value = score
        }
        else {
            lifesLiveData.value = --lifes
        }

        setNewWord()
    }

    fun onWrongTransClicked(){
        if(currentQuestion.translation != currentWord.textSpa){
            score += 10
            scoreLiveData.value = score
        }
        else {
            lifesLiveData.value = --lifes
        }

        setNewWord()
    }

    fun setNewWord(){
        if(wordsList.isEmpty() || lifes<= 0){
            gameOverLiveData.value = score
            return
        }

        currentWord = wordsList[0]
        wordsList.removeAt(0)

        val rnd = (0..wordsList.size).random()
        currentQuestion = if(rnd%2 == 0){
            //use the correct translation
            QuestionModel(currentWord.textEng,currentWord.textSpa)
        }
        else {
            //use wrong translation
            QuestionModel(currentWord.textEng,wordsList[rnd].textSpa)
        }

        questionLiveData.value = currentQuestion
    }
}