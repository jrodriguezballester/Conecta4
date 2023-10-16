package com.example.conecta41

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.conecta41.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonContinuar.setOnClickListener {
            Log.i("MyTag","Boton apretado")
            // Ir a GameActivity

            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }
}