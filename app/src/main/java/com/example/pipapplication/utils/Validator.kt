package com.example.pipapplication.utils

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validator {

    companion object{
        fun isvValidEmail(email: String?): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && (email?.substringAfter(".")?.length!! >= 2)
        }

        fun isValidPassword(password: String?): Boolean {
            val pattern: Pattern
            val PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
//            val PASSWORD_PATTERN =
//                "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            pattern = Pattern.compile(PASSWORD_PATTERN)
            val matcher: Matcher = pattern.matcher(password)
            return matcher.matches()
        }

    }
}