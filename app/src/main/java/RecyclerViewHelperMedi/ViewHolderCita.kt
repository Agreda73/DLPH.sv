package RecyclerViewHelperMedi

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

class ViewHolderCita (view: View) : RecyclerView.ViewHolder(view) {
    val txt_doc = view.findViewById<TextView>(R.id.txt_doc)
    val txt_Hora = view.findViewById<TextView>(R.id.txt_hora)
}