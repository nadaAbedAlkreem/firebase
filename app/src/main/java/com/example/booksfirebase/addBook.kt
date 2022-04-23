package com.example.booksfirebase

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.booksfirebase.databinding.FragmentAddBookBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class addBook : Fragment() {
    lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentAddBookBinding
    var TAG = "hzm"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Firebase.firestore
        binding = FragmentAddBookBinding.inflate(inflater, container, false)

        binding.buttonadd.setOnClickListener {
            var name = binding.namebook.text.toString()
            var email = binding.description.text.toString()
            var auther = binding.auther.text.toString()
            var price = binding.price.text.toString().toLong()
            addcstomer(name, email, auther, price)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, listAllBook()).addToBackStack(null)
                .commit()

         }

        return binding.root
    }


      fun addcstomer(name: String, description: String, auther: String, price: Long) {
        val data = hashMapOf(
            "name" to name,
            "description" to description,
            "auther" to auther,
            "price" to price
        )
        db.collection("library").add(data).addOnSuccessListener {
            Log.e(TAG,"added successful with id: ${it.id}")
        }.addOnFailureListener {
            Log.e(TAG, it.message!!)
        }


    }


}