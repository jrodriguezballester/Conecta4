package com.example.conecta41

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.conecta41.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var tablero: Array<Array<Casilla>>
    val numFilas = 6
    val numColumnas = 7
    var width = 0
    var height = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        width = resources.getDimensionPixelSize(R.dimen.image_dimension)
        height = resources.getDimensionPixelSize(R.dimen.image_dimension)

        crearTablero()
        // Log.i("MyTag","Tablero ${tablero.size}")
    }

    private fun crearTablero(): Array<Array<Casilla>> {
        // creamos el tablero para la logica
        tablero = Array(numFilas) { fila ->
            Array(numColumnas) { columna ->
                // Creamos las casillas
                Casilla(fila, columna, true, 0, ImageView(this))
            }
        }
        // Creamos el tablero para pintarlo
        // Iterar para crear 6 LinearLayouts
        for (fila in 0 until numFilas) {
            val linearLayout = createLinearLayout()
            // Iterar para crear 7 ImageViews dentro de cada LinearLayout
            for (columna in 0 until numColumnas) {
                // Agregar ImageView al LinearLayout actual
                linearLayout.addView(createCasillaEmpty(fila, columna).imageView)
            }
            // Agregar el LinearLayout de las casillas al diseño raíz
            binding.linearLayoutTablero.addView(linearLayout)
        }
        return tablero
    }

    /**
     *  Creamos un linearLayout Horizontal  donde insertar las casillas
     *  (uno por fila de fichas)
     */
    private fun createLinearLayout(): LinearLayout {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return linearLayout
    }

    /**
     * Asignamos la imagen a las casillas vacias del tablero
     */
    private fun createCasillaEmpty(fila: Int, columna: Int): Casilla {
        val casilla = tablero[fila][columna]
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.empty_piece) // Reemplaza con la imagen deseada
        imageView.layoutParams = ViewGroup.LayoutParams(width, height)
        casilla.imageView = imageView
        return casilla
    }

}