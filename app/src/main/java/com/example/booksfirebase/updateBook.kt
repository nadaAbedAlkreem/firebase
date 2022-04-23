package com.example.booksfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.booksfirebase.databinding.FragmentListAllBookBinding
import com.example.booksfirebase.databinding.FragmentUpdateBookBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class updateBook : Fragment() {
    private lateinit var binding: FragmentUpdateBookBinding
    private  lateinit var db : FirebaseFirestore

     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding = FragmentUpdateBookBinding.inflate(inflater, container, false)
        var namesend = arguments?.getString("name")
        db = Firebase.firestore
        db.collection("library").get().addOnSuccessListener {
                query ->
            for (docu in query) {
                if (namesend == docu.getString("name")) {
                           binding.namebook.setText(namesend)
                       binding.auther.setText( docu.getString("auther"))
                         binding.description.setText(docu.getString("description"))
                         binding.price.setText(docu.getLong("price").toString())


                }

            }
        }

        binding.updatebook.setOnClickListener {

            updatedata( binding.namebook.text.toString() , binding.auther.text.toString()
                , binding.description.text.toString() ,   binding.price.text.toString().toLong())
        }

        return binding.root
    }
    private fun updatedata(name :String , auther :String , description : String , price : Long) {
        db = Firebase.firestore
        var activity2=activity as AppCompatActivity
        var namesend = arguments?.getString("name")
        db.collection("library").get().addOnSuccessListener { query ->
            for (docu in query) {
                if (namesend == docu.getString("name")) {
                    var id = docu.id
                    Log.e("nada", namesend.toString())
                    db.collection("library").document(id).update(
                        "name", name, "auther", auther,
                        "description", description,
                        "price", price

                    )
                        .addOnSuccessListener {
                            activity2.supportFragmentManager.beginTransaction()
                                .replace(R.id.mainContainer, listAllBook()).addToBackStack(null)
                                .commit()
                        }
                        .addOnFailureListener {
                            Log.e("nada", "failure")
                        }

                }
            }


        }
    }

}