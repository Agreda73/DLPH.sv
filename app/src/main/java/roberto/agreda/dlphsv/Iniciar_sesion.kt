package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
class Iniciar_sesion : AppCompatActivity() {
    lateinit var valorRolUsuario: String
    companion object variablesLogin {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContra = findViewById<EditText>(R.id.txtContra)
        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)
        val menu = Intent(this, Menu::class.java)
        val btnRecuperarContra= findViewById<Button>(R.id.btnOlvidarContra)
        val recuperarContra =Intent(this,activity_recuperarContrasena::class.java)


        btnIniciar.setOnClickListener {
            val correo = txtCorreo.text.toString()
            val contrasena = txtContra.text.toString()

            if(correo.isEmpty()) {
                txtCorreo.error = "El correo es obligatorio"
            } else {
                txtCorreo.error = null
            }

            if(contrasena.isEmpty()) {
                txtContra.error = "la contraseña es obligatoria"
            } else {
                txtContra.error = null
            }

            GlobalScope.launch(Dispatchers.IO){
                val objConexion = ClaseConexion().cadenaConexion()
                val comprobarUsuario =
                    objConexion?.prepareStatement("Select * from Usuarios where nombre =? and contrasenaUsuario= ?")!!
                comprobarUsuario.setString(1, txtCorreo.text.toString())
                comprobarUsuario.setString(2, txtContra.text.toString())
                val resultado = comprobarUsuario.executeQuery()
                valorRolUsuario = resultado.getString("rollUsuario")
                if (resultado.next()) {
                    valorRolUsuario = resultado.getString("rollUsuario")
                    btnIniciar.setOnClickListener{
                    startActivity(menu)}
                } else {
                    println("credenciales incorrectas")
                }
            }
        }
        btnRecuperarContra.setOnClickListener{
            startActivity(recuperarContra)
        }
    }
}