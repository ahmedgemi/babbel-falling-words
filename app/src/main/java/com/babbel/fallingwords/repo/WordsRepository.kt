package com.babbel.fallingwords.repo

import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.data.source.WordsDataSource
import com.babbel.fallingwords.data.source.WordsLocalDataSource
import javax.inject.Inject

class WordsRepository @Inject constructor(
    private val dataSource: WordsDataSource
){

    suspend fun fetchWords(): ResultState<List<WordModel>>{
        return dataSource.fetchWords()
    }
}