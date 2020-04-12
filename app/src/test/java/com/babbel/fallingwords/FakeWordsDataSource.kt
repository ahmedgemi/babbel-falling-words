package com.babbel.fallingwords

import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.data.source.WordsDataSource

class FakeWordsDataSource: WordsDataSource {
    override suspend fun fetchWords(): List<WordModel> {
        return listOf(
            WordModel("holidays","vacaciones"),
            WordModel("group","grupo"),
            WordModel("ball","balón"),
            WordModel("jungle","jungla")
        )
    }
}