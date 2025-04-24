package com.example.clase_nativas.utils

import android.content.Context
import android.widget.Toast

class ToastAlert {

    companion object {
        fun show(context: Context , message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}