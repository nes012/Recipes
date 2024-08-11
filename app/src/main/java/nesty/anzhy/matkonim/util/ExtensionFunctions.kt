package nesty.anzhy.matkonim.util
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

//extension function for a live data. This function will observe data object only once and not every time
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(value: T) {
                removeObserver(this)
                observer.onChanged(value)
            }
        }
    )
}

fun bindMenu(
    menuHost: MenuHost,
    @MenuRes menuRes: Int,
    lifecycleOwner: LifecycleOwner,
    onMenuItemSelected: (MenuItem) -> Unit
): MenuProvider {
    val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuRes, menu)
        }
        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onMenuItemSelected(menuItem)
            return true
        }
    }
    menuHost.addMenuProvider(menuProvider,lifecycleOwner, Lifecycle.State.RESUMED)
    return menuProvider
}