package com.example.conecta41

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.conecta41.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var tamano = 0

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtén el drawable desde los recursos
        val drawable: Drawable? = resources.getDrawable(R.drawable.border_red)
        binding.pequeno.setOnClickListener {
            binding.pequeno.background = drawable
            binding.medio.setBackgroundResource(0)
            binding.grande.setBackgroundResource(0)
            tamano = 1
        }
        binding.medio.setOnClickListener {
            binding.medio.background = drawable
            binding.pequeno.setBackgroundResource(0)
            binding.grande.setBackgroundResource(0)
            tamano = 2
        }
        binding.grande.setOnClickListener {
            binding.grande.background = drawable
            binding.medio.setBackgroundResource(0)
            binding.pequeno.setBackgroundResource(0)
            tamano = 3
        }
        binding.buttonContinuar.setOnClickListener {
            Log.i("MyTag", "Boton apretado")
            // Ir a GameActivity

            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("TAMANO", tamano)

            startActivity(intent)

        }

    }
}