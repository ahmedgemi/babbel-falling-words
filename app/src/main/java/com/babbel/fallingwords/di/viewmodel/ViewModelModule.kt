package com.mobiquity.products.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babbel.fallingwords.di.viewmodel.ViewModelFactory
import com.babbel.fallingwords.ui.game.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun provideGameViewModel(viewModel: GameViewModel): ViewModel
}