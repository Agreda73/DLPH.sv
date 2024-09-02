package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import Modelos.Habitaciones
import RecyclerViewHelperHabitacion.AdaptadorHab
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class habitaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitaciones)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgmenuHab = findViewById<ImageView>(R.id.imgmenuHab)
        val btnAgregarHab = findViewById<Button>(R.id.btnAgregarHab)
        val btnActualizarHab = findViewById<Button>(R.id.btnActualizarHab)
        val rcvHabitaciones = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rcvHabitaciones)
        val spnHabitaciones = findViewById<Spinner>(R.id.spnHabitaciones)
        val txtname = findViewById<EditText>(R.id.txtNombrePacienteHab)


        rcvHabitaciones.layoutManager =  LinearLayoutManager(this)


        fun obtenerDatos(): List<Habitaciones> {
            val cardH = mutableListOf<Habitaciones>()
            val objConexion = ClaseConexion().cadenaConexion()

            objConexion?.use { connection ->
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery("SELECT * FROM TB_HABITACIONES")

                resultSet.use { rs ->
                    while (rs.next()) {
                        val numCardH = rs.getInt("numCardH")
                        val NombreP = rs.getString("Nombre Paciente")
                        val habitacion = rs.getInt("Habitacion")

                        val Card = Habitaciones (numCardH,NombreP, habitacion)
                        cardH.add(Card)
                    }
                }
            }
            return cardH
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticketsBd = obtenerDatos()
            withContext(Dispatchers.Main) {
                val miAdapter = AdaptadorHab(ticketsBd)
                rcvHabitaciones.adapter = miAdapter
            }
        }
        btnAgregarHab.setOnClickListener {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val objConexion = ClaseConexion().cadenaConexion()

                    objConexion?.use { connection ->
                        val crearCard= connection.prepareStatement("INSERT INTO TB_HABITACIONES (NombreP, habitacion ) VALUES (?, ?)")

                        crearCard.setString(1, txtname.text.toString())
                        crearCard.setString(2, spnHabitaciones.selectedItem.toString())

                        crearCard.executeUpdate()
                    }

                    val nuevoTicket = obtenerDatos()
                    withContext(Dispatchers.Main){
                        (rcvHabitaciones.adapter as? AdaptadorHab)?.actualizarLista(nuevoTicket)
                    }
                }
            } catch (ex: Exception) {
                println("REGISTER: este es el error: $ex")
            }
        }
    }

}
