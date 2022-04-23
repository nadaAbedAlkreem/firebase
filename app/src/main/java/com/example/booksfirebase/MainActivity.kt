package com.example.booksfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() , communicator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer,   listAllBook()).commit()

    }



    override fun postData(postionName: String) {
         val bundle = Bundle()
        bundle.putString("name" , postionName)
        val transaction  =  supportFragmentManager.beginTransaction()
        val fragamatupdate = updateBook()
        fragamatupdate.arguments = bundle
        transaction.replace(R.id.mainContainer , fragamatupdate).commit()

    }
}
