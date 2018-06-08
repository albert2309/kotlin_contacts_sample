package com.contacts.app.ui.detailsScreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.contacts.app.R
import com.contacts.app.util.loadUrl
import kotlinx.android.synthetic.main.activity_contact_details.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class ContactDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tv_name.text = intent.extras.getString("name")
        tv_phone.text = intent.extras.getString("phone")

        //another usage of loadUrl extension method
        iv_profile.loadUrl(intent.extras.getString("imageUrl"))

        tv_name.onClick {
            toast(tv_name.text)
        }

        tv_phone.onClick {
            toast(tv_phone.text)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            finish()
        return true
    }
}
