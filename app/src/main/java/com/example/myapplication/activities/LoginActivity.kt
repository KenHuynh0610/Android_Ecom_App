package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.models.UserLogin
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edit_text_email
import kotlinx.android.synthetic.main.activity_login.edit_text_password
import kotlinx.android.synthetic.main.activity_login.status
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {

//        Log.d("abc", Endpoints.getRegister())
        button_login.setOnClickListener {
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()

            var sessionManager = SessionManager(this)
            var login = UserLogin(email, password)
            var gson = Gson().toJson(login, UserLogin::class.java)

            var params = JSONObject(gson)
//
//            params.put("email", email)
//            params.put("password", password)
//            params.put("mobile", phone)

            var queue = Volley.newRequestQueue(this)

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getLogin(),
                params,
                Response.Listener {
                    Toast.makeText(applicationContext, "User Login Successfully", Toast.LENGTH_SHORT).show()
//                    status.text = "User Authenticated"
                    var str = it.toString()
                    var loginResponse = Gson().fromJson(str, LoginResponse::class.java)
                    sessionManager.loginSuccess(loginResponse.user)
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                },

                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
//                    var str = String(it.networkResponse.data)
//                    status.text = str


                }
            )
            queue.add(request)
        }
        not_register_text.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}