package roberto.agreda.dlphsv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class Citas : AppCompatActivity() {


private val recyclerView: RecyclerView? = null
private val adaptadorCita: AdaptadorCita? = null
private val citasList: MutableList<Cita> = mutableListOf() // Lista de citas


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_citas)


    val mainView = findViewById<View>(R.id.main)

    ViewCompat.setOnApplyWindowInsetsListener(mainView, ViewCompat.OnApplyWindowInsetsListener { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    })

    recyclerView = findViewById(R.id.recyclerView)


    recyclerView?.adapter = adaptadorCita
}

private fun enableEdgeToEdge() {
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            )
}
}


class AdaptadorCita(private val citasList: MutableList<Cita>) : RecyclerView.Adapter<AdaptadorCita.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_citas_card, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citasList[position]
        holder.txt_doc.text = cita.titulo
        holder.txt_Hora.text = cita.descripcion
    }


    override fun getItemCount(): Int {
        return citasList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_doc: TextView = itemView.findViewById(R.id.txt_doc)
        val txt_Hora: TextView = itemView.findViewById(R.id.txt_hora)
    }
}

data class Cita(val titulo: String, val descripcion: String)