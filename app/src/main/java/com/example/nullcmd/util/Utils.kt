package com.example.nullcmd.util

import android.content.Context
import android.widget.Toast

/**
 * Created by FarisJakpau on 6/07/2021
 *
 **/


fun showToast(context: Context, text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, length).show()
}