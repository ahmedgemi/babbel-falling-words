package com.babbel.fallingwords.ui.game

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.babbel.fallingwords.R
import com.babbel.fallingwords.base.BaseActivity
import com.babbel.fallingwords.data.model.QuestionModel
import com.babbel.fallingwords.databinding.ActivityGameBinding


class GameActivity : BaseActivity<ActivityGameBinding>() {

    lateinit var viewModel: GameViewModel

    override fun getViewBinding(): ActivityGameBinding {
        return ActivityGameBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding.btnCorrect.setOnClickListener {
            cancelAnimation()
            viewModel.onCorrectTransClicked()
        }

        binding.btnWrong.setOnClickListener {
            cancelAnimation()
            viewModel.onWrongTransClicked()
        }

        binding.animationView.visibility =
            if(isRunningTest) View.GONE else View.VISIBLE
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this,viewModelFactory).get(GameViewModel::class.java)

        viewModel.questionLiveData.observe(this, Observer {
            startAnimation(it)
        })

        viewModel.scoreLiveData.observe(this, Observer {score->
            binding.tvScore.text = score.toString()
        })

        viewModel.livesLiveData.observe(this, Observer { lives->
            binding.tvLives.text = lives.toString()
        })

        viewModel.gameOverLiveData.observe(this, Observer {score->
            GameResultSheet(this,score).show()
        })

        viewModel.errorLiveData.observe(this, Observer {
            showErrorMessage(it)
        })
    }

    private fun startAnimation(question: QuestionModel){

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.fall_down)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                cancelAnimation()
                viewModel.onNoAnswer()
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        binding.tvWord.text = question.question
        binding.layoutMeteor.tvTranslation.text = question.translation
        binding.layoutMeteor.root.visibility = View.VISIBLE

        if(!isRunningTest)
            binding.layoutMeteor.root.startAnimation(animation)
    }

    private fun cancelAnimation(){
        binding.layoutMeteor.root.visibility = View.GONE
        binding.layoutMeteor.tvTranslation.text = ""
        binding.layoutMeteor.root.animation?.setAnimationListener(null)
        binding.layoutMeteor.root.clearAnimation()
    }

    val isRunningTest : Boolean by lazy {
        try {
            Class.forName("androidx.test.espresso.Espresso")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}
