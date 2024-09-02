package RecyclerViewHelperHabitacion

import Modelos.ClaseConexion
import Modelos.Habitaciones
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

class AdaptadorHab (var Datos: List<Habitaciones>) : RecyclerView.Adapter<RecyclerViewHelperHabitacion.ViewHolderHab>() {
    fun actualizarLista(nuevaLista: List<Habitaciones>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun actualizarDatos( habitacion:Int, titulo: String) {
        val index = Datos.indexOfFirst { it. habitacion ==  habitacion }
        Datos[index].NombreP = titulo
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHelperHabitacion.ViewHolderHab {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardhabitaciones, parent, false)
        return RecyclerViewHelperHabitacion.ViewHolderHab(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: RecyclerViewHelperHabitacion.ViewHolderHab, position: Int) {
        val item = Datos[position]
        holder.txtNombrePacienteHab.text = item.NombreP


        holder.img_borrarH.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)

            builder.setTitle("¿estas seguro?")

            builder.setMessage("quieres eliminar el registro")

            builder.setPositiveButton("Si") { dialog, wich ->
                eliminarTicket(item.NombreP, position)
            }

            builder.setNegativeButton("No") { dialog, wich ->

            }

            val alertDialog = builder.create()


            alertDialog.show()

        }

        holder.img_editarH.setOnClickListener {

            val context = holder.itemView.context


            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar nombre")

            val cuadritoNuevoNombre = EditText(context)
            cuadritoNuevoNombre.setHint(item.NombreP)
            builder.setView(cuadritoNuevoNombre)


            builder.setPositiveButton("Actualizar ") { dialog, wich ->
                ActualizarTicket(
                    cuadritoNuevoNombre.text.toString(),
                    item.numCardH
                )

            }

            builder.setNegativeButton("Cancelar") { dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }


    fun eliminarTicket(titulo: String, position: Int) {

        val listadatos = Datos.toMutableList()
        listadatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val eliminarTic = objConexion?.prepareStatement("delete TB_HABITACIONES where Título=?")!!
            eliminarTic.setString(1, titulo)
            eliminarTic.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listadatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

    fun ActualizarTicket(titulo: String, numTicket: Int) {


        GlobalScope.launch(Dispatchers.IO) {


            val objConexion = ClaseConexion().cadenaConexion()


            val updateTicket =
                objConexion?.prepareStatement("UPDATE TB_HABITACIONES SET Título = ? WHERE NombreP = ?")!!
            updateTicket.setString(1, titulo)
            updateTicket.setInt(2, numTicket)
            updateTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()


            withContext(Dispatchers.Main) {
                actualizarDatos(numTicket,titulo)
            }
        }
    }
}
