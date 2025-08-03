package com.example.drillar

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.drillar.databinding.ActivityDrillDetailBinding

class DrillDetailActivity : AppCompatActivity() {
  lateinit var binding: ActivityDrillDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDrillDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val position = intent.getStringExtra("drill_name")
        binding.drillTitle.text = "$position"

        val drillName = intent.getStringExtra("drill_name")
        val drillImage = intent.getIntExtra("drill_image", R.drawable.ic_launcher_foreground)
        val description = intent.getStringExtra("drill_description")
        val tip1 = intent.getStringExtra("tip1")
        val tip2 = intent.getStringExtra("tip2")
        val tip3 = intent.getStringExtra("tip3")
        binding.drillImage.setImageResource(drillImage)


        binding.drillDescription.text = description
        binding.tip1TextView.text=tip1
        binding.tip2TextView.text=tip2
        binding.tip3TextView.text=tip3

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.startDrillButton.setOnClickListener {
            val intent = Intent(this, ARActivity::class.java)
            startActivity(intent)

        }



    }
}