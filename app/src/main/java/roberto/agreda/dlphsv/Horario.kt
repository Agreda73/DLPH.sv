package roberto.agreda.dlphsv

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Horario : AppCompatActivity() {

    private lateinit var lblHoraEntrada: TextView
    private lateinit var lblHoraSalida: TextView
    private lateinit var clvFecha: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_horario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgbackHo = findViewById<ImageView>(R.id.imgbackHo)
        val Menu = Intent(this,Menu::class.java)

        imgbackHo.setOnClickListener{
            startActivity(Menu)
        }

        lblHoraEntrada = findViewById(R.id.lblHorarioEntrada)
        lblHoraSalida = findViewById(R.id.lblHorarioSalida)
        clvFecha = findViewById(R.id.clvHorario)

        val horaEntrada = obtenerHoraEntrada()
        val horaSalida = obtenerHoraSalida()

        lblHoraEntrada.text = horaEntrada
        lblHoraSalida.text = horaSalida


        val calendar = Calendar.getInstance()
        clvFecha.date = calendar.timeInMillis

    }

    private fun obtenerHoraEntrada(): String {
        return "08:00"
    }

    private fun obtenerHoraSalida(): String {
        return "17:00"
    }
}


