package com.pmdm.multimedia

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pmdm.multimedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding de la vista principal
    private lateinit var binding: ActivityMainBinding

    //reproductor para el sonido gole
    private var sonidoGolpe: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //botón para reproducir el sondo del golpe
        binding.btnSonido.setOnClickListener {
            sonidoGolpe?.release()
            sonidoGolpe = MediaPlayer.create(this, R.raw.golpe)
            sonidoGolpe?.start()
        }

        //botón para abrir la pantalla de audio
        binding.btnAudio.setOnClickListener {
            startActivity(Intent(this, AudioActivity::class.java))
        }

        //botón para abrir la pantalla de video
        binding.btnVideo.setOnClickListener{
            startActivity(Intent(this, VideoActivity::class.java))
        }

        //botón para mostrar el diálogo de salida
        binding.btnSalir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.btn_salir))
                .setMessage(getString(R.string.dialogo_salir))
                .setPositiveButton("Si") { _, _ -> finishAffinity() }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sonidoGolpe?.release()
    }
}

