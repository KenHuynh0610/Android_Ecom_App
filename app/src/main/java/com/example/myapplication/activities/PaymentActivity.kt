package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.app.Endpoints
import com.example.myapplication.helpers.DBHelpers
import com.example.myapplication.helpers.SessionManager
import com.example.myapplication.models.*
import com.example.myapplication.models.AddressData.Companion.KEY_ADDRESS

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_toolbar.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {

    var db = DBHelpers(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        init()

    }

    private fun init() {
        var sessionManager = SessionManager(this)
        tool_bar.title = "Payment"
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var productSummary: ArrayList<ProductSummary> = ArrayList()
        var address = intent.getSerializableExtra(KEY_ADDRESS) as AddressData
        lateinit var shippingAddress: ShippingAddress

        change_button.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }
        if (address._id != null) {
            shippingAddress = ShippingAddress(
                address._id!!,
                address.city,
                address.houseNo,
                address.pincode,
                address.streetName,
                address.type
            )
        }
        var product = db.getAllProduct()

        text_view_city.text = address.city + ", "
        text_view_houseNo.text = address.houseNo + ", "
        text_view_street.text = address.streetName + ", "
        text_view_zip.text = address.pincode.toString()
        text_view_type.text = address.type
        //TODO: make swipe, change color button, delete button, add and minus buttons, show cart in every UI

        var totalPrice = sessionManager.getPriceSummary()
        var totalMrp = sessionManager.getMRPSummary()
        var totalSave = sessionManager.getSaveSummary()

        var orderSummary = OrderSummary(null, 0, totalSave, totalPrice, totalPrice, totalMrp)

        text_view_number_items.text = "${ db.getNumberOfProducts().toString() }"
        text_view_subtotal.text = "$${totalPrice.toString()}"

        for (i in product) {
            productSummary.add(
                ProductSummary(
                    i.productId,
                    i.image,
                    i.mrp,
                    i.price,
                    i.name,
                    i.quantity
                )
            )
        }

        button_cash.setOnClickListener{
            button_card.visibility = View.GONE
            button_confirm.visibility = View.VISIBLE
        }

        button_card.setOnClickListener{
            button_cash.visibility = View.GONE
            button_confirm.visibility = View.VISIBLE
        }

        var userId = sessionManager.getUserId()
        var userEmail = sessionManager.getUser()
        var userName = sessionManager.getUserName()
        Log.d("abc", "$totalPrice,  $totalMrp, $totalSave}")
        var userInfo = UserInfo(userId, userEmail, userName)
        var summary = OrderPost(
            null,
            null,
            null,
            orderSummary,
            productSummary,
            shippingAddress,
            userInfo,
            userId
        )
        var gson = Gson().toJson(summary, OrderPost::class.java)
        var params = JSONObject(gson)

        button_confirm.setOnClickListener {
            var queue = Volley.newRequestQueue(this)
            var response = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.postOrder(),
                params,
                Response.Listener {
                    Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show()
                    db.deleteAll()
                    startActivity(Intent(this, ThankyouActivity::class.java))
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show()
                    var error = String(it.networkResponse.data)

                }
            )
            queue.add(response)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

}