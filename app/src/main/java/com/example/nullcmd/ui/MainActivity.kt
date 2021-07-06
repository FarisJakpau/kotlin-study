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

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(HomeFragment())

        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    setCurrentFragment(HomeFragment())
                }
                R.id.recipeFragment -> {
                    setCurrentFragment(RecipeFragment())
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment):Boolean {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.frame_layout, fragment)
            replace(R.id.frame_layout, fragment)
            commit()
        }
        return true
    }
}