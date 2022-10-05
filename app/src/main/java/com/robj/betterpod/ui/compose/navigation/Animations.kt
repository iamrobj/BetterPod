package com.robj.betterpod.ui.compose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween

@OptIn(ExperimentalAnimationApi::class)
object Animations {

    fun AnimatedContentScope<*>.slideUpEnterAnim(): EnterTransition =
        slideIntoContainer(
            AnimatedContentScope.SlideDirection.Up,
            animationSpec = tween(300)
        )

    fun AnimatedContentScope<*>.slideDownExitAnim(): ExitTransition =
        slideOutOfContainer(
            AnimatedContentScope.SlideDirection.Down,
            animationSpec = tween(300)
        )

}