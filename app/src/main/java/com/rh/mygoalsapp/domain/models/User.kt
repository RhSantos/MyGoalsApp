package com.rh.mygoalsapp.domain.models

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.rh.mygoalsapp.R

data class User(
    var nome: String,
    var email: String,
    var senha: String,
    var data_nascimento: String,
    var foto: Bitmap,
    var endereco: Address
) {
    companion object {
        fun emptyUser(context: Context) : User {
            val photo = ContextCompat.getDrawable(context, R.drawable.user)
            return User("","","","",photo!!.toBitmap(), Address.emptyAddress)
        }
    }
}
