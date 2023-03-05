package com.example.pipapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    @PrimaryKey
    val email: String,
    val fullname: String,
    val username: String,
    val password: String
)
