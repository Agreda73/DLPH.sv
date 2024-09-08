package RecyclerViewHelperIn

import Modelos.ClaseConexion
import Modelos.INVENTARIO
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import roberto.agreda.dlphsv.R

class AdaptadorInven (var Datos: List<INVENTARIO>): RecyclerView.Adapter<ViewHolderIn>(){

    fun actualizarLista(nuevaLista: List<INVENTARIO>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarDatos(UUID_INVENTARIO: String,nombre: String,MARCA: String,CANTIDAD: Int) {
        val listadatos = Datos.toMutableList()
        listadatos.removeAt(CANTIDAD)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val eliminarM= objConexion?.prepareStatement("delete medicamento where nombre=?")!!
            eliminarM.setString(1, nombre)
            eliminarM.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listadatos.toList()
        notifyItemRemoved(CANTIDAD)
        notifyDataSetChanged()

    }
    fun ActualizarDatos(titulo: String, numcard: Int) {


        GlobalScope.launch(Dispatchers.IO) {


            val objConexion = ClaseConexion().cadenaConexion()


            val updateTicket =
                objConexion?.prepareStatement("UPDATE TB_medicamento SET nombre = ? WHERE numcard = ?")!!
            updateTicket.setString(1, titulo)
            updateTicket.setInt(2, numcard)
            updateTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()


            withContext(Dispatchers.Main) {
                actualizarLista(Datos)
            }
        }
        fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIn {
            val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_medicamento_card, parent, false)
            return ViewHolderIn(vista)
        }

        fun getItemCount() = Datos.size

        fun onBindViewHolder(holder: ViewHolderIn, position: Int) {
            val item = Datos[position]
            holder.txtNombreMedi.text = item.NOMBRE
            holder.txtMarcaMedi.text = item.MARCA
            holder.txtCantidadMedi.text = item.CANTIDAD.toString()


            holder.img_borrarH.setOnClickListener {

                val context = holder.itemView.context
                val builder = AlertDialog.Builder(context)
                builder.setTitle("¿estas seguro?")
                builder.setMessage("quieres eliminar el registro")
                builder.setPositiveButton("si") { dialog, wich ->
                    eliminarDatos(item.UUID_INVENTARIO.toString(),item.NOMBRE,item.MARCA,item.CANTIDAD)
                }

                builder.setNegativeButton("no") { dialog, wich ->

                }

                val alertDialog = builder.create()


                alertDialog.show()

                holder.img_editarH.setOnClickListener {

                    val context = holder.itemView.context


                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Editar nombre")

                    val cuadritoNuevoNombre = EditText(context)
                    cuadritoNuevoNombre.setHint(item.NOMBRE)
                    builder.setView(cuadritoNuevoNombre)


                    builder.setPositiveButton("Actualizar ") { dialog, wich ->
                        ActualizarDatos(
                            cuadritoNuevoNombre.text.toString(),
                            item.CANTIDAD,
                        )
                    }

                    builder.setNegativeButton("Cancelar") { dialog, wich ->
                        dialog.dismiss()
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderIn {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderIn, position: Int) {
        TODO("Not yet implemented")
    }
}