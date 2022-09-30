package com.robj.betterpod

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

abstract class BasePresenterActivity<T : BaseView> :
    AppCompatActivity(),
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
            this,
            string,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun handleError(resource: Int) {
        Toast.makeText(
            this,
            getString(resource),
            Toast.LENGTH_LONG
        ).show()
    }
}
