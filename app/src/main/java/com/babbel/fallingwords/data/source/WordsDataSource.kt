package com.babbel.fallingwords.data.source

import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordModel

/**
 * This represent word list data source interface.
 * All types (local, remote, test-double) words list data-source should implement this interface
 */
interface WordsDataSource {
    suspend fun fetchWords(): ResultState<List<WordModel>>
}