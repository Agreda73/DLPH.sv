package RecyclerViewHelperMedi

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import roberto.agreda.dlphsv.R

class ViewHolderMedi(view: View) : RecyclerView.ViewHolder(view) {
    val txtNombreMedi = view.findViewById<TextView>(R.id.txtNamem)
    val txtMarcaMedi = view.findViewById<TextView>(R.id.txtMarcam)
    val txtCantidadMedi = view.findViewById<TextView>(R.id.txtCantidadMedi)
    val imgBorrar = view.findViewById<ImageView>(R.id.img_borrar)
    val imgEditar = view.findViewById<ImageView>(R.id.img_editar)
    val imgMedi = view.findViewById<ImageView>(R.id.imgmedi)


}