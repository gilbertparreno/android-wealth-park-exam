package com.wealthpark.exam.core.sharedPreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserSharedPreferences @Inject constructor(
    @Named(value = "userPreferences") private val sharedPreferences: SharedPreferences
) {

    var userId: Int = sharedPreferences.getInt("user.id", -1)
        set(value) {
            sharedPreferences.edit {
                putInt("user.id", value)
            }
            field = value
        }

    var email: String? = sharedPreferences.getString("user.email", null)
        set(value) {
            sharedPreferences.edit {
                putString("user.email", value)
            }
            field = value
        }

    fun setUserDetails(userId: Int, email: String) {
        this.email = email
        this.userId = userId
    }

    fun isLogged() = userId != -1
}