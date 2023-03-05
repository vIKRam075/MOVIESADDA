package com.example.pipapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class PreferenceManager {
    companion object {
        private val sharedPrefKey = "movies_app"

        private lateinit var sharedPreference: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        lateinit var mContext: Context

        @SuppressLint("CommitPrefEdits")
        public fun init(mContext: Context) {
            this.mContext = mContext
            sharedPreference =
                this.mContext.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
            editor = sharedPreference.edit()
        }

        fun getUserId(): Int {
            return sharedPreference.getInt(Constants.USER_ID, 0)
        }

        fun setUserId(id: Int?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            id?.let { editor.putInt(Constants.USER_ID, it) }
            editor.apply()
        }

        fun getIsLogin(): Boolean {
            return sharedPreference.getBoolean(Constants.IS_LOGIN, false)
        }

        fun setIsLogin(isLogin: Boolean?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            isLogin?.let { editor.putBoolean(Constants.IS_LOGIN, it) }
            editor.apply()
        }

        fun getUsername(): String? {
            return sharedPreference.getString(Constants.USERNAME, "")
        }

        fun setUsername(username: String?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            username?.let { editor.putString(Constants.USERNAME, username) }
            editor.apply()
        }

        fun getName(): String? {
            return sharedPreference.getString(Constants.FULL_NAME, "")
        }

        fun setName(name: String?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            name?.let { editor.putString(Constants.FULL_NAME, name) }
            editor.apply()
        }

        fun getEmail(): String? {
            return sharedPreference.getString(Constants.EMAIL, "")
        }

        fun setEmail(email: String?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            email?.let { editor.putString(Constants.EMAIL, email) }
            editor.apply()
        }

        fun getProfileImg(): String? {
            return sharedPreference.getString(Constants.PROFILE_IMG, "")
        }

        fun setProfileImg(image: String?) {
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            image?.let { editor.putString(Constants.PROFILE_IMG, image) }
            editor.apply()
        }

        fun clearAll() {
//            val editor: SharedPreferences.Editor = sharedPreference.edit()
//            editor.clear().apply()
            sharedPreference.edit().clear().apply()
        }
    }
}