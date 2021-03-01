package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.adapters.AddressAdapter
import com.example.myapplication.fragments.AddAddressFragment
import com.example.myapplication.fragments.EditAddressFragment
import com.example.myapplication.fragments.SelectAddressFragment
import com.example.myapplication.models.AddressData
import com.example.myapplication.models.AddressData.Companion.KEY_ADDRESS
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.view.*
import kotlinx.android.synthetic.main.app_toolbar.*
import java.io.Serializable

class AddressActivity : AppCompatActivity(), AddressAdapter.OnClickListener,
    AddressAdapter.OnFragmentClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        init()
    }

    private fun init() {
        setupToolbar()

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout, SelectAddressFragment())
            .commit()
    }

    private fun setupToolbar() {
        tool_bar.title = "Shipping"
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
    


    override fun onClick(address: Serializable) {
        var intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(KEY_ADDRESS, address)
        startActivity(intent)

    }

    override fun onFragmentClick(address: Serializable) {
        Log.d("abc","button clicked")
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, EditAddressFragment.newInstance(address)).commit()
    }
}