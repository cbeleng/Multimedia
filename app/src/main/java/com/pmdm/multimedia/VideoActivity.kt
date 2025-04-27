package com.pmdm.multimedia

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pmdm.multimedia.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {
    // Binding de la vista del vídeo
    private lateinit var vinculoVideo: ActivityVideoBinding

    // URI del vídeo que esta cagado
    private var uriVideoActual: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vinculoVideo = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(vinculoVideo.root)

        // Botón de Play: carga y reproduce el vídeo seleccionado
        vinculoVideo.btnPlayVideo.setOnClickListener {
            val idSeleccionado = vinculoVideo.rgVideos.checkedRadioButtonId
            if (idSeleccionado == -1) {
                Toast.makeText(this, "Selecciona un vídeo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uriSeleccionada = when (idSeleccionado) {
                R.id.rb_londres    -> Uri.parse("android.resource://$packageName/${R.raw.londres}")
                R.id.rb_ny         -> Uri.parse("android.resource://$packageName/${R.raw.nuevayork}")
                R.id.rb_edimburgo  -> Uri.parse("android.resource://$packageName/${R.raw.edimburgo}")
                else               -> return@setOnClickListener
            }
            if (uriVideoActual != uriSeleccionada) {
                vinculoVideo.videoView.stopPlayback()
                vinculoVideo.videoView.setVideoURI(uriSeleccionada)
                vinculoVideo.videoView.setMediaController(MediaController(this))
                uriVideoActual = uriSeleccionada
            }
            vinculoVideo.videoView.start()
        }

        // Botón de Pause: pausa la reproducción (si está activa)
        vinculoVideo.btnPauseVideo.setOnClickListener {
            if (vinculoVideo.videoView.isPlaying) {
                vinculoVideo.videoView.pause()
            } else {
                Toast.makeText(this, "No hay nada que pausar", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de Stop: detiene la reproducción y resetea la URI
        vinculoVideo.btnStopVideo.setOnClickListener {
            if (vinculoVideo.videoView.isPlaying) {
                vinculoVideo.videoView.stopPlayback()
                uriVideoActual = null
            } else {
                Toast.makeText(this, "No hay nada que detener", Toast.LENGTH_SHORT).show()
            }
        }

        vinculoVideo.btnAtrasVideo.setOnClickListener {
            finish()
        }
    }
}