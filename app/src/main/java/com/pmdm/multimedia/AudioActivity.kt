package com.pmdm.multimedia

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pmdm.multimedia.databinding.ActivityAudioBinding

class AudioActivity : AppCompatActivity() {
    // Binding de la vista de audio
    private lateinit var vinculoAudio: ActivityAudioBinding

    // Reproductor de la canción seleccionada
    private var reproductorCancion: MediaPlayer? = null

    // ID del recurso de del audio que está cargado
    private var idAudioActual: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vinculoAudio = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(vinculoAudio.root)

        // Botón de Play: inicia o continua la canción seleccionada
        vinculoAudio.btnPlayAudio.setOnClickListener {
            val idSeleccionado = vinculoAudio.rgCanciones.checkedRadioButtonId
            if (idSeleccionado == -1) {
                Toast.makeText(this, "Selecciona una canción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val resAudio = when (idSeleccionado) {
                R.id.rb_cancion1 -> R.raw.ichwill
                R.id.rb_cancion2 -> R.raw.liese
                R.id.rb_cancion3 -> R.raw.los
                else             -> return@setOnClickListener
            }
            if (idAudioActual != resAudio) {
                reproductorCancion?.release()
                reproductorCancion = MediaPlayer.create(this, resAudio)
                idAudioActual = resAudio
            }
            reproductorCancion?.start()
        }

        // Botón de Pause: si hay algo sonando, lo pausa
        vinculoAudio.btnPauseAudio.setOnClickListener {
            if (reproductorCancion?.isPlaying == true) {
                reproductorCancion?.pause()
            } else {
                Toast.makeText(this, "Nada que pausar", Toast.LENGTH_SHORT).show()
            }
        }

        //Botón de Stop: detiene el reproductor y lo libera
        vinculoAudio.btnStopAudio.setOnClickListener {
            if (reproductorCancion != null) {
                reproductorCancion?.stop()
                reproductorCancion?.release()
                reproductorCancion = null
                idAudioActual = null
            } else {
                Toast.makeText(this, "Nada que parar", Toast.LENGTH_SHORT).show()
            }
        }

        vinculoAudio.btnAtrasAudio.setOnClickListener {
            finish()  // cierra AudioActivity y vuelve a MainActivity
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        reproductorCancion?.release()
    }
}