package com.example.nullcmd.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nullcmd.R
import com.example.nullcmd.ui.home.HomeFragment
import com.example.nullcmd.ui.recipe.RecipeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(HomeFragment(), BottomNavFragmentTag.HOME)

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    setCurrentFragment(HomeFragment(), BottomNavFragmentTag.HOME)
                }
                R.id.recipeFragment -> {
                    setCurrentFragment(RecipeFragment(), BottomNavFragmentTag.RECIPE)
                }
                else -> false
            }
        }
    }

    private enum class BottomNavFragmentTag {
        HOME,
        RECIPE
    }

    private fun setCurrentFragment(fragment: Fragment, tag: BottomNavFragmentTag): Boolean {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_layout, fragment, tag.name)
            replace(R.id.frame_layout, fragment)
            commit()
        }
        return true
    }
}

