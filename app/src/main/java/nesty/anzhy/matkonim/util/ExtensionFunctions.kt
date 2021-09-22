package nesty.anzhy.matkonim.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


//extension function for a live data. This function will observe data object only once and not every time
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(t: T) {
                removeObserver(this)
                observer.onChanged(t)
            }
        }
    )
}