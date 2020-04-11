package com.babbel.fallingwords.di.activity

import androidx.viewbinding.ViewBinding
import com.babbel.fallingwords.base.BaseActivity
import com.babbel.fallingwords.di.application.ApplicationComponent
import com.mobiquity.products.di.ViewModelModule
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ViewModelModule::class])
interface ActivityComponent {
    fun inject(activity: BaseActivity<ViewBinding>)
}