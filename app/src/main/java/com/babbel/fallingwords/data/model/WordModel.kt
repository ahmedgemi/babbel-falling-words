package com.babbel.fallingwords.data.model


import com.google.gson.annotations.SerializedName

data class WordModel(
    @SerializedName("text_eng")
    val textEng: String,
    @SerializedName("text_spa")
    val textSpa: String
)