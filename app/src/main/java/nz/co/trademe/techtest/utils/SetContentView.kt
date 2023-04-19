package nz.co.trademe.techtest.utils

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KProperty


fun <T : ViewDataBinding> contentView(@LayoutRes layoutRes: Int) = SetContentView<T>(layoutRes)

class SetContentView<out T : ViewDataBinding>(@LayoutRes private val layoutRes: Int) {

    private var value: T? = null

    operator fun getValue(thisRef: Activity, property: KProperty<*>): T {
        value = value ?: DataBindingUtil.setContentView(
            thisRef,
            layoutRes
        )

        return value!!
    }
}