package com.example.booksfirebase.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booksfirebase.Model.databook
import com.example.booksfirebase.R
import com.example.booksfirebase.communicator
import com.example.booksfirebase.databinding.FragmentDisgenListbookBinding
import com.example.booksfirebase.updateBook
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class showBookAdapter(var activity: Activity,
                          var data: ArrayList<databook>
) : RecyclerView.Adapter<showBookAdapter.MyView>() {
    private  lateinit var db : FirebaseFirestore
    private  lateinit var communicator: communicator

    class  MyView(var binding : FragmentDisgenListbookBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  MyView {
        val binding = FragmentDisgenListbookBinding.inflate(activity.layoutInflater, parent, false)



        return MyView(binding) }





    override fun onBindViewHolder(holder:  MyView, position: Int) {
        db = Firebase.firestore

        holder.binding.namebook2.setText(data[position].name)
        holder.binding.auther.setText(data[position].auther)
        holder.binding.description.setText( data[position].description)
        holder.binding.price.setText(data[position].price.toString())
        holder.binding.trash.setOnClickListener {
            deletebook(data[position].name)
        }
        holder.binding.update.setOnClickListener {
            communicator =  activity as communicator

          communicator.postData(data[position].name)
          }

    }


     private fun updatedata(name :String , auther :String , description : String , price : Long){
         db = Firebase.firestore
         db.collection("library").get().addOnSuccessListener {
                 query ->
             for (docu in query) {
                      var id = docu.id
                     Log.e("nada" , name)
                     db.collection("library").document(id).update("name" , name
                        , "auther"  , auther  ,
                         "description" , description ,
                         "price"  , price

                      )
                         .addOnSuccessListener {
                             Log.e("nada" , name)
                         }
                         .addOnFailureListener{
                             Log.e("nada" , "failure")
                         }

             }


         }

       }
    override fun getItemCount(): Int {
        return data.size

    }
    private fun deletebook(name : String) {
        db = Firebase.firestore
        db.collection("library").get().addOnSuccessListener {
                query ->
            for (docu in query) {
                if (name == docu.getString("name")){
                    var id = docu.id
                    db.collection("library").document(id).delete()
                        .addOnSuccessListener {
                            Log.e("nada" , "success")
                        }
                        .addOnFailureListener{
                            Log.e("nada" , "failure")
                        }
                 }
              }


        }
}

}
