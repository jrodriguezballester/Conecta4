package com.example.conecta41

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.conecta41.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var tablero: Array<Array<Casilla>>
    private lateinit var filasGanadoras: Array<Int>
    private lateinit var columnasGanadoras: Array<Int>

    var numFilas = 6
    var numColumnas = 7
    var width = 0
    var height = 0
    var finalizado = false

    // jugador tomará valores -1 (amarillo),1 (red)
    var jugador = -1
    var puntuacionAmarillo = 0
    var puntuacionRed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir los valores pasados desde la actividad anterior
        val tamano = intent.getIntExtra("TAMANO", 1)
        // Distintas configuraciones en funcion del tamano
        val tamanoToConfig = mapOf(
            1 to Config(6, 7, R.dimen.image_dimensionL),
            2 to Config(7, 8, R.dimen.image_dimensionM),
            3 to Config(8, 9, R.dimen.image_dimensionS)
        )

        val config = tamanoToConfig[tamano]

        if (config != null) {
            numFilas = config.numFilas
            numColumnas = config.numColumnas
            width = resources.getDimensionPixelSize(config.imageDimension)
            height = resources.getDimensionPixelSize(config.imageDimension)
        } else {
            numFilas =6
            numColumnas = 7
            width = resources.getDimensionPixelSize(R.dimen.image_dimensionL)
            height = resources.getDimensionPixelSize(R.dimen.image_dimensionL)
        }


        binding.puntuacionYellow.text = puntuacionAmarillo.toString()
        binding.puntuacionRed.text = puntuacionRed.toString()

        crearFlechas() // Botones para donde tirar ficha
        crearTablero()
        binding.buttonReiniciarDatos.setOnClickListener {
            puntuacionAmarillo = 0
            puntuacionRed = 0
            binding.puntuacionYellow.text = puntuacionAmarillo.toString()
            binding.puntuacionRed.text = puntuacionRed.toString()
        }
        binding.buttonReiniciarJuego.setOnClickListener {
            finalizado = false
            tablero = emptyArray()
            binding.linearLayoutTablero.removeAllViews()
            crearTablero()

        }
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
                linearLayout.addView(pintarCasilla(fila, columna, true).imageView)
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

    private fun pintarCasilla(fila: Int, columna: Int, empty: Boolean): Casilla {
        val casilla = tablero[fila][columna]
        val imageView = ImageView(this)
        if (empty) {
            imageView.setImageResource(R.drawable.empty_piece)
            //   casilla.imageView.setImageResource(R.drawable.empty_piece)
        }
//        else {
//       //     Log.i("MyTag", "else pintando resaltado vacio $fila, $columna")
//       //     casilla.imageView.setImageResource(R.drawable.empty_piece)
//        }

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
            if (jugador == -1) {
                binding.linearLayoutFlechas.setBackgroundColor(this.getColor(R.color.yelow))
            }

            binding.linearLayoutFlechas.addView(imageViewsFlechas[i])
            //////////////////////////////////////////
            // Donde comienza el juego como tal
            imageViewsFlechas[i].setOnClickListener {
                // comprobar si no ha finalizado el juego poner otra ficha
                if (!finalizado) echarFicha(it)
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
            // comprobar ganar
            comprobarGanar()
            // cambiar turno jugador
            cambiarTurno()

        } else {
            Log.i("MyTag", "Columna llena")
        }
    }

    private fun cambiarTurno() {
        // cambio de jugador
        jugador = -jugador
        // cambio de fondo de color de las flechas
        if (jugador == -1) {
            binding.tvJugador.text = "Ficha Amarilla"
            binding.linearLayoutFlechas.setBackgroundColor(this.getColor(R.color.yelow))
        } else {
            binding.tvJugador.text = "Ficha Roja"
            binding.linearLayoutFlechas.setBackgroundColor(this.getColor(R.color.red))
        }
    }


    /**
     * Pinta la ficha en la primera casilla vacia empezando desde abajo
     */
    private fun ponerFicha(columna: Int) {
        // Encontrar la primera casilla vacia
        var casillaVaciaEncontrada = false
        // fila Empieza por 0 y de arriba a abajo (fila 0 la mas alta)
        var fila = numFilas - 1
        while (!casillaVaciaEncontrada && fila >= 0) {
            val casilla = tablero[fila][columna]
            if (casilla.vacia) { // si la casilla esta vacia
                casilla.jugador = jugador // la casilla ahora es de un jugador
                casilla.vacia = false // la casilla ahora no esta vacia
                // pintamos casilla
                casilla.imageView.setImageResource(if (jugador == 1) R.drawable.red_piece else R.drawable.yellow_piece)
                // Indicar que se encontró una casilla vacía cierra el bucle
                casillaVaciaEncontrada = true
            }
            //      Log.i("MyTag", "casillaVaciaEncontrada:$casillaVaciaEncontrada Fila:$fila")
            fila--
        }
    }

    /**
     * Comprueba si hay 4 fichas contiguas iguales
     */
    private fun comprobarGanar() {
        if (comprobarHorizontal() || comprobarVertical() || comprobarDiagonal() || comprobarDiagonalInversa()) {
            Log.i("MyTag", "GANA")
            // TODO Que hacer cuando ha ganado
            haGanado()
        } else {  // solo comprobar se puede eliminar el elsa
            Log.i("MyTag", "No gana, seguir jugando")
        }
    }

    private fun haGanado() {
        Log.i("MyTag", "pintando ha ganados")
        finalizado = true
        // Animacion
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade)

        for (i in filasGanadoras.indices) {
            tablero[filasGanadoras[i]][columnasGanadoras[i]].imageView.startAnimation(animation)
        }
        if (jugador == -1) {
            puntuacionAmarillo += 1
            binding.puntuacionYellow.text = puntuacionAmarillo.toString()

        } else {
            puntuacionRed += 1
            binding.puntuacionRed.text = puntuacionRed.toString()
        }

    }

    /**
     * Recorre todas las filas , por cada columna comprueba si esa casilla y las 3 siguentes son del jugador
     * (ojo bucles -3 para no obtener errores; son 4 fichas en linea)
     */
    fun comprobarHorizontal(): Boolean {
        for (fila in 0 until numFilas) {
            for (columna in 0 until numColumnas - 3) {
                if (tablero[fila][columna].jugador == jugador &&
                    tablero[fila][columna + 1].jugador == jugador &&
                    tablero[fila][columna + 2].jugador == jugador &&
                    tablero[fila][columna + 3].jugador == jugador
                ) {
                    filasGanadoras = arrayOf(fila, fila, fila, fila)
                    columnasGanadoras = arrayOf(columna, columna + 1, columna + 2, columna + 3)
                    Log.i(
                        "MyTag",
                        "Gana Horizontal ${filasGanadoras.toString()}, $columnasGanadoras "
                    )
                    return true
                }
            }
        }
        return false
    }

    /**
     * Recorre todas las filas , por cada columna comprueba si esa casilla y las 3 siguentes (en vertical) son del jugador
     *(ojo bucles -3 para no obtener errores; son 4 fichas en linea)
     */
    fun comprobarVertical(): Boolean {
        Log.i("MyTag", "DIAGONAL")
        for (fila in 0 until numFilas - 3) {
            for (columna in 0 until numColumnas) {
                if (tablero[fila][columna].jugador == jugador &&
                    tablero[fila + 1][columna].jugador == jugador &&
                    tablero[fila + 2][columna].jugador == jugador &&
                    tablero[fila + 3][columna].jugador == jugador
                ) {
                    filasGanadoras = arrayOf(fila, fila + 1, fila + 2, fila + 3)
                    columnasGanadoras = arrayOf(columna, columna, columna, columna)
                    Log.i(
                        "MyTag",
                        "Gana Vertical ${filasGanadoras.toString()}, $columnasGanadoras "
                    )
                    return true
                }
            }
        }
        return false
    }

    /**
     * Recorre todas las filas , por cada columna comprueba la matriz de casillas en diagonal \ )
     *(ojo bucles -3 para no obtener errores; son 4 fichas en linea)
     */
    fun comprobarDiagonal(): Boolean {
        for (fila in 0 until numFilas - 3) {
            for (columna in 0 until numColumnas - 3) {
                if (tablero[fila][columna].jugador == jugador &&
                    tablero[fila + 1][columna + 1].jugador == jugador &&
                    tablero[fila + 2][columna + 2].jugador == jugador &&
                    tablero[fila + 3][columna + 3].jugador == jugador
                ) {
                    filasGanadoras = arrayOf(fila, fila + 1, fila + 2, fila + 3)
                    columnasGanadoras = arrayOf(columna, columna + 1, columna + 2, columna + 3)
                    Log.i(
                        "MyTag",
                        "Gana Diagonal ${filasGanadoras.toString()}, $columnasGanadoras "
                    )
                    Log.i("MyTag", "DIAGONAL GANA")
                    return true
                }
            }
        }
        return false
    }

    /**
     * Recorre todas las filas , por cada columna comprueba la matriz de casillas en diagonal / )
     *(ojo bucles -3 para no obtener errores; son 4 fichas en linea)
     */
    fun comprobarDiagonalInversa(): Boolean {
        for (fila in 0 until numFilas - 3) {
            for (columna in 3 until numColumnas) {
                if (tablero[fila][columna].jugador == jugador &&
                    tablero[fila + 1][columna - 1].jugador == jugador &&
                    tablero[fila + 2][columna - 2].jugador == jugador &&
                    tablero[fila + 3][columna - 3].jugador == jugador
                ) {
                    filasGanadoras = arrayOf(fila, fila + 1, fila + 2, fila + 3)
                    columnasGanadoras = arrayOf(columna, columna - 1, columna - 2, columna - 3)
                    Log.i(
                        "MyTag",
                        "Gana Diagonal inversa ${filasGanadoras.toString()}, $columnasGanadoras "
                    )
                    Log.i("MyTag", "DIAGONAL inversa GANA")
                    return true
                }
            }
        }
        return false
    }
}

