package com.robj.betterpod

import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class BaseFragment<T : BaseView> :
    Fragment(),
    BaseView,
    CoroutineScope by MainScope() {

    abstract val presenter: BasePresenter<T>

    override fun onStart() {
        super.onStart()

        @Suppress("UNCHECKED_CAST")
        presenter.bind(view = this as T)

        launch { presenter.start() }
    }

    override fun onStop() {
        super.onStop()
        presenter.unBind()
    }

    override fun handleError(string: String) {
        Toast.makeText(
            activity,
            string,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun handleError(resource: Int) {
        Toast.makeText(
            activity,
            getString(resource),
            Toast.LENGTH_LONG
        ).show()
    }
}
