package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Menu : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btPerfil = findViewById<AppCompatImageButton>(R.id.btnPefil)
        val perfil =Intent(this,Perfil::class.java)
        val btnVisita = findViewById<AppCompatImageButton>(R.id.btnVisita)
        val visita =Intent(this,Citas::class.java)
        val btnCerrarSesion = findViewById<AppCompatImageButton>(R.id.btnCerrarSesion)
        val cerrarSesion =Intent(this,Iniciar_sesion::class.java)
        val btnhabitacion = findViewById<AppCompatImageButton>(R.id.btnHabitacion)
        val habitacion =Intent(this,HabitacionesVista::class.java)
        val btnExpediente = findViewById<AppCompatImageButton>(R.id.btnExpediente)
        val Expediente =Intent(this,Expediente::class.java)
        val btnHorario = findViewById<AppCompatImageButton>(R.id.btnHorario)
        val Horario =Intent(this,Horario::class.java)
        val btnMedicamento = findViewById<AppCompatImageButton>(R.id.btnMedicina)
        val medicamento  =Intent(this,InventarioMedicamento::class.java)
        val btnConsulta = findViewById<AppCompatImageButton>(R.id.btnConsulta)
        val consulta =Intent(this,Citas::class.java)
        val btnReceta =findViewById<AppCompatImageButton>(R.id.btnReceta)
        val receta =Intent(this,Receta::class.java)

/**
        fun traerID(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet =
                statement?.executeQuery("SELECT id_rol FROM rol WHERE nombre_rol = 'admin'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("id_rol")
            }
            return uuidRol
        }
        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = Iniciar_sesion.valorRolUsuario
            val RolUsuarioMainActivity = traerID()
            withContext(Dispatchers.Main) {
                if (txtcorreoiniciarval == RolUsuarioMainActivity) {
                    btnhabitacion.visibility = View.VISIBLE
                    btnMedicamento.visibility = View.VISIBLE
                    btnConsulta.visibility = View.VISIBLE
                    btnHorario.visibility = View.VISIBLE
                    btnExpediente.visibility = View.VISIBLE
                    btnReceta.visibility = View.VISIBLE


                } else {
                    btnhabitacion.visibility = View.GONE
                    btnMedicamento.visibility = View.GONE
                    btnConsulta.visibility = View.GONE
                    btnHorario.visibility = View.GONE
                    btnExpediente.visibility = View.GONE
                    btnReceta.visibility = View.GONE
                }
            }
        **/
            btPerfil.setOnClickListener{
                startActivity(perfil)
            }
            btnVisita.setOnClickListener{
                startActivity(visita)
            }
            btnCerrarSesion.setOnClickListener{
                startActivity(cerrarSesion)
            }
            btnhabitacion.setOnClickListener{
                startActivity(habitacion)
            }
            btnExpediente.setOnClickListener{
                startActivity(Expediente)
            }
            btnHorario.setOnClickListener{
                startActivity(Horario)
            }
            btnMedicamento.setOnClickListener{
                startActivity(medicamento)
            }
            btnConsulta.setOnClickListener{
                startActivity(consulta)
            }
            btnReceta.setOnClickListener{
                startActivity(receta)
            }
        }
    }
