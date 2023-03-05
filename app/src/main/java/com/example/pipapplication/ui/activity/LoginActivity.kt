package com.example.pipapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pipapplication.MoviesApp
import com.example.pipapplication.data.database.MoviesDatabase
import com.example.pipapplication.data.model.UserModel
import com.example.pipapplication.data.viewmodels.MoviesMainViewModel
import com.example.pipapplication.data.viewmodels.MoviesViewModelFactory
import com.example.pipapplication.databinding.ActivityLoginBinding
import com.example.pipapplication.utils.PreferenceManager
import com.example.pipapplication.utils.Validator.Companion.isValidPassword
import com.example.pipapplication.utils.Validator.Companion.isvValidEmail
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var moviesMainViewModel: MoviesMainViewModel

    @Inject
    lateinit var moviesViewModelFactory: MoviesViewModelFactory

/*    @Inject
    lateinit var moviesDatabase: MoviesDatabase*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        (application as MoviesApp).applicationComponent.inject(this)

        moviesMainViewModel =
            ViewModelProvider(this, moviesViewModelFactory).get(MoviesMainViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            val emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            if (binding.edtUsername.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Email cannot be empty", Toast.LENGTH_SHORT)
                    .show()
//            } else if (!binding.edtUsername.text.toString().trim { it <= ' ' }.matches(emailpattern)) {
            } else if (!isvValidEmail(binding.edtUsername.text.toString().trim())) {
                Toast.makeText(this@LoginActivity, "Enter a valid Email", Toast.LENGTH_SHORT).show()
            } else if (binding.edtPass.text.toString().trim { it <= ' ' }.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Password cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.edtPass.text.toString().trim { it <= ' ' }.length < 6) {
                Toast.makeText(
                    this@LoginActivity,
                    "Password cannot be less than 6 digits",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isValidPassword(binding.edtPass.text.toString().trim())) {
                Toast.makeText(
                    this@LoginActivity,
                    "Password should contain char, number and at least one special char",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                moviesMainViewModel.getUser(binding.edtUsername.text.toString(),binding.edtPass.text.toString())
                    .observe(this) { user ->
                        if ((user != null) and (user?.email == binding.edtUsername.text.toString())
                            and (user?.password == binding.edtPass.text.toString())) {
                            doLogin(user!!)
                        } else {
                            Toast.makeText(
                                this@LoginActivity, "Email or password incorrect",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

/*                val isUserExists =
                    moviesMainViewModel.userExists(binding.edtUsername.text.toString())
                if (isUserExists) {
                    doLogin()
                } else {
                Toast.makeText(
                    this@LoginActivity, "This user does not exist in our database",
                    Toast.LENGTH_LONG
                ).show()
//                }
*/
            }
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun doLogin(userModel: UserModel) {
        PreferenceManager.setIsLogin(true)
        PreferenceManager.setEmail(userModel.email)
        PreferenceManager.setUsername(userModel.username)
        PreferenceManager.setName(userModel.fullname)
        Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("email", binding.edtUsername.text.toString().trim { it <= ' ' })
        intent.putExtra("pass", binding.edtPass.text.toString().trim { it <= ' ' })
        startActivity(intent)
        finish()
    }
}