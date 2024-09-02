package RecyclerViewHelperExpediente

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

class ViewHolderExpediente (view: View) : RecyclerView.ViewHolder(view) {
    val imgExp = view.findViewById<ImageView>(R.id.imgExp)
    val txtNameExp = view.findViewById<TextView>(R.id.txtnombreH)
    val txtCategoriaExp = view.findViewById<TextView>(R.id.txtCategoriaExp)
    val txtHabitacionExp = view.findViewById<TextView>(R.id.txthabitacion)
    val img_editarH = view.findViewById<ImageView>(R.id.img_editarH)
    val img_borrarH = view.findViewById<ImageView>(R.id.img_borrarH)
}