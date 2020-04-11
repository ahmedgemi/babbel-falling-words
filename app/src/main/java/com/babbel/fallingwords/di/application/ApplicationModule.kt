package com.babbel.fallingwords.di.application

import android.app.Application
import android.content.Context
import com.babbel.fallingwords.data.source.WordsDataSource
import com.babbel.fallingwords.data.source.WordsLocalDataSource
import com.babbel.fallingwords.di.activity.ActivityScope
import com.babbel.fallingwords.repo.WordsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val mApplication: Application) {

    @Singleton
    @Provides
    //@ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @ActivityScope
    @Provides
    fun providesWordsRepository(dataSource: WordsLocalDataSource): WordsRepository {
        return WordsRepository(dataSource)
    }

    @Provides
    fun providesWordsLocal(): WordsDataSource {
        return WordsLocalDataSource(mApplication)
    }
}
