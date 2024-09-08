package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import Modelos.INVENTARIO
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InventarioMedicamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inventario_medicamento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnAgregarInventario = findViewById<Button>(R.id.btnAgregarInve)
        val rcvItems = findViewById<RecyclerView>(R.id.rcvItems)
       // val immenuInve = findViewById<ImageView>(R.id.immenuInve)
        rcvItems.layoutManager= LinearLayoutManager(this)
        //falta agregar codigo de regresar al menu principal



        fun cargarDatos(): List<INVENTARIO> {
            val listaMedicamentos = mutableListOf<INVENTARIO>()
            val objConexion = ClaseConexion().cadenaConexion()

            objConexion?.use { connection ->
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM medicamento")

                resultSet.use { resultSet ->
            while (resultSet!!.next()) {
                val uuidInventario = resultSet.getString("id_medicamento")
                val nombre = resultSet.getString("NOMBRE")
                val marca = resultSet.getString("MARCA")
                val cantidad = resultSet.getString("CANTIDAD")/*
                val fotoMedicamento = resultSet.getString("FOTO_MEDICAMENTO")

                val medicamento = INVENTARIO(uuidInventario, nombre, marca, cantidad, fotoMedicamento)
                listaMedicamentos.add(medicamento)*/
            }
                }
                }
            return listaMedicamentos
        }


        btnAgregarInventario.setOnClickListener {
            val intent = Intent(this, AgregarInventario::class.java)
            startActivity(intent)
        }

        }
    }
