package com.babbel.fallingwords.data.source

import android.content.Context
import com.babbel.fallingwords.R
import com.babbel.fallingwords.data.ResultState
import com.babbel.fallingwords.data.model.WordListModel
import com.babbel.fallingwords.data.model.WordModel
import com.babbel.fallingwords.di.application.ApplicationContext
import com.google.gson.Gson
import javax.inject.Inject

class WordsLocalDataSource @Inject constructor(
  private val appContext: Context
) : WordsDataSource{

    /**
     * Load words dataset from resources
     */
    override suspend fun fetchWords(): ResultState<List<WordModel>> {
        try {
            val inputStream = appContext.resources.openRawResource(R.raw.words_dataset)
            val buffer = ByteArray(inputStream.available())
            while (inputStream.read(buffer) !== -1);
            val json = String(buffer)

            val list = Gson().fromJson(json, WordListModel::class.java)
            return ResultState.Success(list)
        }
        catch (e: Exception){
            return ResultState.Error(e)
        }
    }

}