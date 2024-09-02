package RecyclerViewHelperExpediente

import Modelos.ClaseConexion
import Modelos.Expediente
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

class AdaptadorExp (var Datos: List<Expediente>): RecyclerView.Adapter<ViewHolderExpediente>(){

    fun actualizarLista(nuevaLista: List<Expediente>) {
        Datos = nuevaLista
        notifyDataSetChanged()
    }
    fun eliminarDatos (NUM_EXPEDIENTE: String, NombreExp: String, imgExp: String, Categoria: Int, HabitacionExp: Int, Diagnostico: String, HistorialExp: String) {
        val listadatos = Datos.toMutableList()
        listadatos.removeAt(Categoria)

        GlobalScope.launch(Dispatchers.IO) {

            val objConexion = ClaseConexion().cadenaConexion()

            val eliminarM= objConexion?.prepareStatement("delete EXPEDIENTE where NOMBRE=?")!!
            eliminarM.setString(1, NombreExp)
            eliminarM.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listadatos.toList()
        notifyItemRemoved(Categoria)
        notifyDataSetChanged()
    }

    fun ActualizarDatos(titulo: String, NUM_EXPEDIENTE: Int) {

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()


            val updateTicket =
                objConexion?.prepareStatement("UPDATE TB_Expediente SET Título = ? WHERE NUM_EXPEDIENTE = ?")!!
            updateTicket.setString(1, titulo)
            updateTicket.setInt(2, NUM_EXPEDIENTE)
            updateTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()


            withContext(Dispatchers.Main) {
                actualizarLista(Datos)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderExpediente {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.cardexpediente, parent, false)
        return ViewHolderExpediente(vista)
    }
    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderExpediente, position: Int) {
        val item = Datos[position]

        holder.imgExp.setImageResource(R.layout.activity_expediente)
        holder.txtNameExp.text = item.NombreExp
        holder.txtCategoriaExp.text = item.Categoria.toString()
        holder.txtHabitacionExp.text = item.HabitacionExp.toString()


        holder.img_borrarH.setOnClickListener {

            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Estas seguro?")
            builder.setMessage("quieres eliminar el registro")
            builder.setPositiveButton("si") { dialog, wich ->
                eliminarDatos(item.Categoria.toString(),item.NombreExp,item.HabitacionExp.toString(),item.imgExp)
            }

            builder.setNegativeButton("no") { dialog, wich ->
            }

            val alertDialog = builder.create()


            alertDialog.show()

        }
        holder.img_editarH.setOnClickListener {

            val context = holder.itemView.context


            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar nombre")

            val cuadritoNuevoNombre = EditText(context)
            cuadritoNuevoNombre.setHint(item.NombreExp)
            builder.setView(cuadritoNuevoNombre)


            builder.setPositiveButton("Actualizar ") { dialog, wich ->
                ActualizarDatos(
                    cuadritoNuevoNombre.text.toString(),
                    item.Categoria,
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