package com.babbel.fallingwords.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.babbel.fallingwords.GameApp
import com.babbel.fallingwords.di.activity.ActivityComponent
import com.babbel.fallingwords.di.activity.DaggerActivityComponent
import com.babbel.fallingwords.di.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * This class represent the base Activity contains common components initialization for all activities
 * <T> providing the ViewBinding generic type
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    /**
     * viewModelFactory for initializing ViewModel instance with Dagger2
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    /**
     * viewBinding instance of activity layout
     */
    protected lateinit var binding: T

    /**
     * @return ViewBinding instance of the activity
     */
    protected abstract fun getViewBinding() : T

    protected lateinit var daggerComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        initDagger()
    }

    private fun initDagger() {
        daggerComponent = DaggerActivityComponent.builder()
            .applicationComponent(GameApp.applicationComponent)
            .build()
        daggerComponent.inject(this as BaseActivity<ViewBinding>)
    }

    protected fun showErrorMessage(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    protected fun showSuccessMessage(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}