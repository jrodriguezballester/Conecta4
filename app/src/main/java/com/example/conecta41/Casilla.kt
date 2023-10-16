package com.example.conecta41

import android.widget.ImageView

data class Casilla (
    var fila:Int,
    var columna:Int,
    var vacia: Boolean,
    var jugador:Int,
    var imageView: ImageView
)
