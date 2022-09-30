package com.robj.betterpod

interface BaseView {
    fun handleError(string: String)
    fun handleError(resource: Int)
}

interface BasePresenter<T : BaseView> {
    var view: T?

    suspend fun start()

    fun bind(view: T) {
        this.view = view
    }

    fun unBind() {
        this.view = null
    }
}
