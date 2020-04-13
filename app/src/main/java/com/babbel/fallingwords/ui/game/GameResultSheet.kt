package com.babbel.fallingwords.ui.game

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.FrameLayout
import com.babbel.fallingwords.R
import com.babbel.fallingwords.databinding.LayoutResultSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class GameResultSheet (context: Context, score: Int): BottomSheetDialog(context) {

    init {
        val binding = LayoutResultSheetBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        setCancelable(false)
        setupWindow()

        binding.tvResult.text = score.toString()
        binding.btnPlay.setOnClickListener {
            dismiss()
            val intent = Intent(context,GameActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    /**
     * setup action sheet window properties
     */
    private fun setupWindow(){
        if (window != null) {
            val attributes = window?.attributes
            attributes?.gravity = Gravity.CENTER
            attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes?.height = WindowManager.LayoutParams.MATCH_PARENT
            window?.attributes = attributes
        }

        val bottomSheet = findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
        }

    }

}