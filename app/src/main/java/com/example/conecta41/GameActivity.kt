package com.example.conecta41

import android.os.Bundle
import android.util.Log
import android.view.View
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

    // jugador tomará valores -1,1
    var jugador = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        width = resources.getDimensionPixelSize(R.dimen.image_dimension)
        height = resources.getDimensionPixelSize(R.dimen.image_dimension)

        crearFlechas()
        crearTablero()

    }

    /**
     * Funcion para crear y pintar el tablero basado en una matriz de casillas
     */
    private fun crearTablero(): Array<Array<Casilla>> {
        // creamos el tablero para la logica
        tablero = Array(numFilas) { fila ->
            Array(numColumnas) { columna ->
                // Creamos las casillas
                Casilla(fila, columna, true, 0, ImageView(this))
            }
        }
        // Creamos el tablero para pintarlo

        // Iterar para crear filas LinearLayouts
        for (fila in 0 until numFilas) {
            val linearLayout = createLinearLayout()
            // Iterar para dibujar casillas ( ImageViews) dentro de cada LinearLayout
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

    /**
     * Pintar flechas que sirven de boton para echar las fichas
     */
    private fun crearFlechas() {
        val imageViewsFlechas = Array<ImageView>(numColumnas) { ImageView(this) }

        for (i in imageViewsFlechas.indices) {
            // Configurar propiedades de ImageView si es necesario
            imageViewsFlechas[i].layoutParams = ViewGroup.LayoutParams(width, height)
            imageViewsFlechas[i].setImageResource(R.drawable.flecha)
            imageViewsFlechas[i].id = (i + 1)
            //    imageViewsFlechas[i].setBackgroundResource(R.drawable.border_red)

            binding.linearLayoutFlechas.addView(imageViewsFlechas[i])

            imageViewsFlechas[i].setOnClickListener {
                echarFicha(it)
            }
        }
    }

    /**
     * Comprueba si se puede poner la ficha y la pone
     */
    private fun echarFicha(flecha: View) {
        val columna = flecha.id - 1
        // comprobar columna vacia
        val isVacia = tablero[0][columna].vacia
        if (isVacia) {
            Log.i("MyTag", "Columna vacia")
            ponerFicha(columna)

        } else {
            Log.i("MyTag", "Columna llena")
        }
    }

    /**
     * Pinta la ficha en la primera casilla vacia empezando desde abajo
     */
    private fun ponerFicha(columna: Int) {
        // Encontrar la primera casilla vacia
        var casillaVaciaEncontrada = false

        var fila = numFilas - 1 // en programacion se empieza por 0 y así es mas didactico
        while (!casillaVaciaEncontrada && fila >= 0) {
            val casilla = tablero[fila][columna]
            if (casilla.vacia) {
                casilla.vacia = false
                casilla.jugador = jugador
                casilla.imageView.setImageResource(if (jugador == 1) R.drawable.red_piece else R.drawable.yellow_piece)
                casillaVaciaEncontrada = true // Indicar que se encontró una casilla vacía
            }
            fila--
        }
    }
}

