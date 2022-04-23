package com.example.booksfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksfirebase.Model.databook
import com.example.booksfirebase.adapter.showBookAdapter
import com.example.booksfirebase.databinding.FragmentAddBookBinding
import com.example.booksfirebase.databinding.FragmentListAllBookBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class listAllBook : Fragment() {
     private  lateinit var db : FirebaseFirestore
     private lateinit var binding: FragmentListAllBookBinding
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListAllBookBinding.inflate(inflater, container, false)

          db = Firebase.firestore
         getAllUser()
         binding.refresh.setOnRefreshListener {
             if (binding.refresh.isRefreshing){
                 binding.refresh.isRefreshing = false
             }
             getAllUser()
         }
          binding.addItem.setOnClickListener {
              requireActivity().supportFragmentManager.beginTransaction()
                  .replace(R.id.mainContainer,  addBook()).addToBackStack(null).commit()
          }


        return  binding.root
     }
     fun getAllUser(){
       var arrayList  = ArrayList<databook>()
         db.collection("library").get().addOnSuccessListener {
                 query ->
             for (document in query) {
                 var  result = document.toObject(databook::class.java)
                 arrayList.add(result)
             }
             binding.listAllItem.apply {
                 layoutManager =
                     LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                 adapter = showBookAdapter(requireActivity() , arrayList)
             }

     }

 }}