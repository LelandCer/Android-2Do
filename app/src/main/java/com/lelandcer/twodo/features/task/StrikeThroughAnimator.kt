package com.lelandcer.twodo.features.task

import android.view.View

/** An animation for the strikethrough line for when a task is marked complete */
class StrikeThroughAnimator(val view: View) {

    /** Animate the view */
    fun animate() {
        with(view) {
            translationX = -width.toFloat()
            animate().translationX(0F).duration = 300
        }
    }

}