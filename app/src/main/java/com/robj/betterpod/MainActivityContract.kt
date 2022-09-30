package com.robj.betterpod

import com.robj.betterpod.networking.models.Podcast

interface MainActivityContract {
    interface View : BaseView {
        fun showPodcasts(podcasts: List<Podcast>)
    }

    interface Presenter : BasePresenter<View> {
    }
}
