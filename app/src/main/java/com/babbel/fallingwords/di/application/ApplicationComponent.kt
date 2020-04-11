package com.babbel.fallingwords.di.application

import android.app.Application
import android.content.Context
import com.babbel.fallingwords.GameApp
import com.babbel.fallingwords.data.source.WordsDataSource
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun getApplication(): Application

    //@ApplicationContext
    fun getContext(): Context

    fun inject(app: GameApp)

    fun getWordsDataSource(): WordsDataSource
}
