package com.example.homeassistantoff.List

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeassistantoff.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException

class ListActivity : AppCompatActivity() {

    public lateinit var dataRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val listView = findViewById<ListView>(R.id.list_listView)
        listView.setBackgroundColor(Color.parseColor("#FFFF00"))

        listView.adapter = MyCustomAdapter(this)

        // Initialize Database
        //dataRef = Firebase.database.reference.child("2022-01-15 09:20:00")

        //dataRef = Firebase.database.reference
        //dataRef.addValueEventListener(logListener)
        Toast.makeText(baseContext, "TESTE LIST", Toast.LENGTH_LONG).show()
    }

    private class MyCustomAdapter(context : Context) : BaseAdapter() {

        private val mContext : Context = context

        private val tempArray = arrayListOf<String>(
            "40", "10", "20"
        )

        // numero de linhas
        override fun getCount(): Int {
            return tempArray.size
        }

        override fun getItem(p0: Int): Any {
            return "TESTE"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        // renderiza cada linha
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val layoutInflater = LayoutInflater.from(mContext)

            //val temp = mContext.dataRef?.temp

            val rowList = layoutInflater.inflate(R.layout.row_listview, p2, false)

            val positionTextView = rowList.findViewById<TextView>(R.id.createdDateTime)
            positionTextView.text = "2022-05-01 22:00:32"

            // NAMES
            val nameTextView = rowList.findViewById<TextView>(R.id.temp)
            nameTextView.text = tempArray.get(p0)

            return rowList
        }

    }
}