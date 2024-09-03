package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

        val btnhabitacion = findViewById<Button>(R.id.btnHabitacion)
        val btnExpediente = findViewById<Button>(R.id.btnExpediente)
        val btnHorario = findViewById<Button>(R.id.btnHorario)
        val btnMedicamento = findViewById<Button>(R.id.btnMedicina)
        val btnConsulta = findViewById<Button>(R.id.btnConsulta)
        val btnReceta =findViewById<Button>(R.id.btnReceta)


        fun traerID(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet =
                statement?.executeQuery("SELECT ID_rol FROM rol WHERE nombre_rol = 'admin'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("ID_rol")
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

        }
    }
}