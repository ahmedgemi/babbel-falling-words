package com.babbel.fallingwords.ui.tutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babbel.fallingwords.R
import com.babbel.fallingwords.base.BaseActivity
import com.babbel.fallingwords.databinding.ActivityTutorialBinding
import com.babbel.fallingwords.ui.game.GameActivity

class TutorialActivity : BaseActivity<ActivityTutorialBinding>() {

    override fun getViewBinding(): ActivityTutorialBinding {
        return ActivityTutorialBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this,GameActivity::class.java))
        }
    }
}
