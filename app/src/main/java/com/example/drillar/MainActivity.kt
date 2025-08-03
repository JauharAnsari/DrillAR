package com.example.drillar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerDrills: Spinner

    private val drillList = listOf("Select Drill", "Drill 1", "Drill 2", "Drill 3")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spinnerDrills = findViewById(R.id.spinnerDrills)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, drillList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDrills.adapter = adapter
        spinnerDrills.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedDrill = drillList[position]

                if (position == 0) return
                val intent = Intent(this@MainActivity, DrillDetailActivity::class.java)
                intent.putExtra("drill_name", selectedDrill)

                when (selectedDrill) {
                    "Drill 1" -> {
                        intent.putExtra("drill_image", R.drawable.ar_one)
                        intent.putExtra("drill_description", "Drill 1 is designed to help users understand basic object placement in an AR environment. This drill focuses on accuracy and spatial awareness.")
                        intent.putExtra("tip1", "Ensure good lighting for better plane detection.")
                        intent.putExtra("tip2", "Move your phone slowly to detect surfaces.")
                        intent.putExtra("tip3", "Tap once to place the object.")
                    }
                    "Drill 2" -> {
                        intent.putExtra("drill_image", R.drawable.drill_two)
                        intent.putExtra("drill_description", "Drill 2 introduces object replacement and interaction. It allows you to swap one 3D object with another by tapping a new model selector.")
                        intent.putExtra("tip1", "Only one object can be placed at a time.")
                        intent.putExtra("tip2", "Tap on a new drill type to replace the current one.")
                        intent.putExtra("tip3", "Use a flat surface for best performance.")
                    }
                    "Drill 3" -> {
                        intent.putExtra("drill_image", R.drawable.drill_three)
                        intent.putExtra("drill_description", "Drill 3 emphasizes on exploring different 3D shapes like cubes and cones and placing them in various positions to simulate real-world training setups.")
                        intent.putExtra("tip1", "Try using different angles for placing objects.")
                        intent.putExtra("tip2", "Explore different areas in your room to test tracking.")
                        intent.putExtra("tip3", "You can long-tap to reset placement.")
                    }
                }

                startActivity(intent)
                spinnerDrills.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}