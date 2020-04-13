package com.babbel.fallingwords.ui.game

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.babbel.fallingwords.AnimationsRule
import com.babbel.fallingwords.Constants
import com.babbel.fallingwords.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GameActivityTest {

    //disable animation with Espresso testing
    @get:Rule
    val animationsRule = AnimationsRule()

    @get:Rule
    var activityRule: ActivityTestRule<GameActivity>
            = ActivityTestRule(GameActivity::class.java)

    lateinit var gameActivity: GameActivity
    lateinit var viewModeTest: GameViewModel

    @Before
    fun setUp() {
        gameActivity = activityRule.activity
        viewModeTest = activityRule.activity.viewModel
    }

    @Test
    fun scoreAndLives_initialCorrectValuesDisplay() {
        onView(withId(R.id.tv_score))
            .check(matches(withText("0")))

        onView(withId(R.id.tv_lives))
            .check(matches(withText(Constants.LIVES_COUNT.toString())))
    }

    @Test
    fun wordAndTranslation_DisplayCorrectData() {
        val question = viewModeTest.questionLiveData.value

        onView(withId(R.id.tv_word))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_word))
            .check(matches(withText(question?.question)))

        onView(withId(R.id.tv_translation))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_translation))
            .check(matches(withText(question?.translation)))
    }

    @Test
    fun clickCorrectAnswer_IncreaseScoreValue() {
        val currentWord = viewModeTest.wordsList[viewModeTest.currentWordIndex]
        val question = viewModeTest.questionLiveData.value

        if(question?.translation == currentWord.textSpa){
            onView(withId(R.id.btn_correct))
                .perform(click())
        }
        else {
            onView(withId(R.id.btn_wrong))
                .perform(click())
        }

        val score = viewModeTest.scoreLiveData.value
        onView(withId(R.id.tv_score))
            .check(matches(withText(score.toString())))
    }

    @Test
    fun clickWrongAnswer_DecreaseLivesValue() {
        val currentWord = viewModeTest.wordsList[viewModeTest.currentWordIndex]
        val question = viewModeTest.questionLiveData.value

        if(question?.translation == currentWord.textSpa){
            onView(withId(R.id.btn_wrong))
                .perform(click())
        }
        else {
            onView(withId(R.id.btn_correct))
                .perform(click())
        }

        onView(withId(R.id.tv_lives))
            .check(matches(withText((Constants.LIVES_COUNT-1).toString())))
    }

    @Test
    fun loseAllLives_DisplayResultSheet() {
        for (i in 0..Constants.LIVES_COUNT-1){
            val currentWord = viewModeTest.wordsList[viewModeTest.currentWordIndex]
            val question = viewModeTest.questionLiveData.value

            if(question?.translation == currentWord.textSpa){
                onView(withId(R.id.btn_wrong))
                    .perform(click())
            }
            else {
                onView(withId(R.id.btn_correct))
                    .perform(click())
            }
        }

        onView(withId(R.id.tv_result))
            .inRoot(isDialog())
            .check(matches(isDisplayed()));
    }
}