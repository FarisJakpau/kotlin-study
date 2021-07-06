package com.example.nullcmd.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.nullcmd.R
import com.example.nullcmd.core.state.State
import kotlinx.android.synthetic.main.layout_placeholder_view.view.*

class DataLoadingPlaceholderView: ConstraintLayout {

    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attr: AttributeSet): super(context, attr) {
        init()
    }

    var retryClick: (() -> Unit)? = null

    private fun init() {
        inflate(context, R.layout.layout_placeholder_view, this)
        progressBar.playAnimation()
        btn_retry.setOnClickListener {
            retryClick?.invoke()
        }
    }

    fun bindState(state: State<*>) {
        when(state) {
            is State.Loading -> {
                isVisible = true
                progressBar.isVisible = true
                ll_error.isVisible = false
            }

            is State.Success -> {
                isVisible = false
                progressBar.isVisible = false
                ll_error.isVisible = false
            }

            is State.Failure -> {
                isVisible = true
                progressBar.isVisible = false
                ll_error.isVisible = true
            }
        }
    }


}