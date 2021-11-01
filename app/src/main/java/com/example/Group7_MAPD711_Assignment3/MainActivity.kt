/**
 * MAPD711
 * Assignment 2 - Group 7
 * Quoc Phong Ngo studentId:301148406
 * Feiliang Zhou studentId:301216989
 */
package com.example.Group7_MAPD711_Assignment3

import Group7_MAPD711_Assignment3.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment3", Context.MODE_PRIVATE)


        val listView: ListView = findViewById(R.id.list_view)

        ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listView.adapter = adapter
        }
        var citySelected = ""
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                citySelected = "Toronto"
            }
            if (position == 1) {
                citySelected = "Mississauga"
            }
            if (position == 2) {
                citySelected = "Oakville"
            }
            if (position == 3) {
                citySelected = "Hamilton"
            }
            if (position == 4) {
                citySelected = "North York"
            }
            Toast.makeText(this,citySelected+" was selected",Toast.LENGTH_LONG).show()
            sharedPreferences.edit().putString(
                "city_selected", citySelected
            ).apply()
        }

        val submit = findViewById<View>(R.id.submit_button) as Button
        submit.setOnClickListener {
            var city_selected: String? = sharedPreferences.getString("city_selected", "")
            if (city_selected == "") {
                Toast.makeText(this,"Please select one city",Toast.LENGTH_LONG).show()
            } else {
                val i = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(i)
            }
        }

    }

}