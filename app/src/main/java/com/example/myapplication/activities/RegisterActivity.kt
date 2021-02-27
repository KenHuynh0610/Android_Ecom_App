package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.UserRegister
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var sessionManager = SessionManager(this)
        if(sessionManager.isLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        init()
    }

    private fun init() {
        button_register.setOnClickListener {
            var queue = Volley.newRequestQueue(this)

            var firstName = edit_text_name.text.toString()
            var email = edit_text_email.text.toString()
            var password = edit_text_password.text.toString()
            var phoneNumber= edit_text_phone.text.toString()


            var register = UserRegister(firstName, email, phoneNumber, password)
            var gson = Gson().toJson(register, UserRegister::class.java)
            var params = JSONObject(gson)

//            Log.d("abc",Endpoints.getRegister())

            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegister(),
                params,
                Response.Listener {
                    Toast.makeText(applicationContext, "User Registered Successfully", Toast.LENGTH_SHORT).show()
//                    status.text = "User Registered"
                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
//                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                    var str = String(it.networkResponse.data)
                    status.text = str
                }
            )
            queue.add(request)
        }
        already_register_text.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
