package com.capstone.invoicemanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.capstone.invoicemanager.connection.CrudApp
import com.capstone.invoicemanager.connection.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class LoginActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var crudApp: CrudApp

    fun injectCrudApp(crudApp: CrudApp) {
        this.crudApp = crudApp
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        retrofit = RetrofitClient.create()
        val crud = retrofit.create(CrudApp::class.java)
        crudApp = retrofit.create(CrudApp::class.java)


        findViewById<Button>(R.id.loginButton).setOnClickListener {
            mainScope.launch {

                val userName = findViewById<EditText>(R.id.etUsername).text.toString()
                val userPassword = findViewById<EditText>(R.id.etPassword).text.toString()

                Log.i("@login", "u Id : $userName , psd : $userPassword")
                try {

                    val response = crudApp.getUser(userName, userPassword)

                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        Log.i("@login", "u Id : $userResponse ")
                        if (userResponse != null) {

                            val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            val userId = response.body() ?: -1 // Handle potential null response
                            editor.putInt("user_id", userId)
                            editor.apply()
                            Toast.makeText(
                                this@LoginActivity,
                                "Login successful! User ID: $userResponse",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, InvoiceListActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed (empty response)",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Handle login failure
                        val error = response.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(
                            this@LoginActivity,
                            "Please check username and password!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    // Handle network connection issues
                    Toast.makeText(
                        this@LoginActivity,
                        "Please check your connection and try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("LoginActivity", "Network error during login:", e)
                }

            }

        }
    }
}
