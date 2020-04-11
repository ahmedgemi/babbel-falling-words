package com.babbel.fallingwords

import android.app.Application
import com.babbel.fallingwords.di.application.ApplicationComponent
import com.babbel.fallingwords.di.application.ApplicationModule
import com.babbel.fallingwords.di.application.DaggerApplicationComponent

class GameApp : Application() {

    companion object{
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}