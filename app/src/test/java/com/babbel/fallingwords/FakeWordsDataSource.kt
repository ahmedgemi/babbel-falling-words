package com.babbel.fallingwords

import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.data.source.WordsDataSource

/**
 * This class is a Test-Double data-source to provide test words list
 */
class FakeWordsDataSource: WordsDataSource {
    override suspend fun fetchWords(): ResultState<List<WordModel>> {
        val list = listOf(
            WordModel("holidays","vacaciones"),
            WordModel("group","grupo"),
            WordModel("ball","balón"),
            WordModel("jungle","jungla")
        )
        return ResultState.Success(list)
    }
}