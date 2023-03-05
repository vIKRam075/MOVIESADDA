package com.example.pipapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pipapplication.MoviesApp
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.model.UserModel
import com.example.pipapplication.data.viewmodels.MoviesMainViewModel
import com.example.pipapplication.data.viewmodels.MoviesViewModelFactory
import com.example.pipapplication.databinding.ActivityRegisterBinding
import com.example.pipapplication.utils.PreferenceManager
import com.example.pipapplication.utils.Validator.Companion.isValidPassword
import com.example.pipapplication.utils.Validator.Companion.isvValidEmail
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var moviesMainViewModel: MoviesMainViewModel

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        (application as MoviesApp).applicationComponent.inject(this)

        moviesMainViewModel =
            ViewModelProvider(this, moviesViewModelFactory).get(MoviesMainViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            if (binding.edtFullName.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Full name cannot be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.edtUsername.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Username cannot be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.edtEmail.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Email cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (!isvValidEmail(binding.edtEmail.text.toString().trim())) {
                Toast.makeText(this@RegisterActivity, "Enter a valid Email", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.edtPass.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password cannot be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (binding.edtPass.text.toString().trim { it <= ' ' }.length < 6) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password cannot be less than 6 digits",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isValidPassword(binding.edtPass.text.toString().trim())) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password should contain char, number and at least one special char",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                moviesMainViewModel.getUserForRegister(
                    binding.edtEmail.text.toString().trim(),
                    binding.edtUsername.toString().trim()
                )
                    .observe(this) { user ->
                        if ((user != null) and (user?.email == binding.edtEmail.text.toString()
                                .trim())
                            and (user?.username == binding.edtUsername.text.toString().trim())
                        ) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "User already exist with the given email/username, Please try with different email/username",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val userModel: UserModel = UserModel(
                                binding.edtEmail.text.toString().trim(),
                                binding.edtFullName.text.toString(),
                                binding.edtUsername.text.toString().trim(),
                                binding.edtPass.text.toString().trim()
                            )
                            moviesMainViewModel.createUser(userModel = userModel)
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}