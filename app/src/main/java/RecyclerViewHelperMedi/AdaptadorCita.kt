package RecyclerViewHelperMedi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

    class AdaptadorCita(private val citas: List<String>, private val horas: List<String>) : RecyclerView.Adapter<AdaptadorCita.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_citas_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textViewDoc.text = citas[position]
            holder.textViewHora.text = horas[position]
        }

        override fun getItemCount(): Int {
            return citas.size
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textViewDoc: TextView = view.findViewById(R.id.txt_doc)
            val textViewHora: TextView = view.findViewById(R.id.txt_hora)
        }

    }

