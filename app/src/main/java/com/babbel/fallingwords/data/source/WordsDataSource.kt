package com.babbel.fallingwords.data.source

import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordModel

interface WordsDataSource {
    suspend fun fetchWords(): List<WordModel>
}