package RecyclerViewHelperHabitacion

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

class ViewHolderHab (view: View) : RecyclerView.ViewHolder(view) {
    val txtNombrePacienteHab = view.findViewById<TextView>(R.id.txtNombrePacienteHab)
    val spnHabitaciones = view.findViewById<TextView>(R.id.spnHabitaciones)
    val img_borrarH = view.findViewById<ImageView>(R.id.img_borrarH)
    val img_editarH = view.findViewById<ImageView>(R.id.img_editarH)
    }