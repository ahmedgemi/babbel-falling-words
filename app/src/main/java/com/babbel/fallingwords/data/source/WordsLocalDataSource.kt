package com.babbel.fallingwords.data.source

import android.content.Context
import com.babbel.fallingwords.R
import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordListModel
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.di.application.ApplicationContext
import com.google.gson.Gson
import javax.inject.Inject

//@ApplicationContext
class WordsLocalDataSource @Inject constructor(
  val appContext: Context
) : WordsDataSource{

    override suspend fun fetchWords(): List<WordModel> {
        val inputStream = appContext.resources.openRawResource(R.raw.words_dataset)
        val buffer = ByteArray(inputStream.available())
        while (inputStream.read(buffer) !== -1);
        val json = String(buffer)

        val list = Gson().fromJson(json, WordListModel::class.java)
        return list
    }

}