package com.example.nullcmd.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieDrawable
import com.example.nullcmd.R
import com.example.nullcmd.core.state.State
import com.example.nullcmd.models.DrinkModel
import com.example.nullcmd.models.FoodModel
import com.example.nullcmd.util.bindImage
import kotlinx.android.synthetic.main.layout_meal_placeholder_view.view.*

/**
 * Created by FarisJakpau on 7/07/2021
 *
 **/

class RandomMealPlaceholderView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    enum class MealType {
        FOOD,
        DRINK
    }

    var retryClick: (() -> Unit)? = null

    private fun init() {
        inflate(context, R.layout.layout_meal_placeholder_view, this)
        progressBar.playAnimation()
    }

    fun <T> bindState(state: State<T>, mealType: MealType) {
        when(mealType) {
            MealType.FOOD -> {
                progressBar.setAnimation(R.raw.food_loading_anim)
            }
            MealType.DRINK -> {
                progressBar.setAnimation(R.raw.drink_loading_anim)
            }
        }

        when (state) {
            State.Loading -> {
                progressBar.playAnimation()
                progressBar.repeatCount = LottieDrawable.INFINITE
                progressBar.isVisible = true
                image.isVisible = false
            }
            is State.Success -> {
                progressBar.isVisible = false
                image.isVisible = true

                val url = when(val data = state.data) {
                    is FoodModel -> {
                        (data as FoodModel).let {
                            name.text = it.strMeal
                            it.strMealThumb
                        }
                    }
                    is DrinkModel -> {
                        (data as DrinkModel).let {
                            name.text = it.strDrink
                            it.strDrinkThumb
                        }
                    }
                    else -> ""
                }
                ivImage.bindImage(url = url.toString(), progressBar)
            }
            is State.Failure -> {
                progressBar.isVisible = false
                image.isVisible = true

                ivImage.bindImage(url = "", progressBar)
            }
        }
    }
}