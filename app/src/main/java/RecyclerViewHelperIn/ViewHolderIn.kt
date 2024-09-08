package RecyclerViewHelperIn

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

class ViewHolderIn(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreMedi = view.findViewById<TextView>(R.id.txtNamem)
    val txtMarcaMedi = view.findViewById<TextView>(R.id.txtMarcam)
    val txtCantidadMedi = view.findViewById<TextView>(R.id.txtCantidadMedi)
    val img_borrarH = view.findViewById<ImageView>(R.id.img_borrarH)
    val img_editarH = view.findViewById<ImageView>(R.id.img_editarH)
}