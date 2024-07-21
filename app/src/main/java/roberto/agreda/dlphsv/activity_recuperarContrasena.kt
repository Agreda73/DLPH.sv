package roberto.agreda.dlphsv

import android.os.Bundle
import android.os.PerformanceHintManager
import android.se.omapi.Session
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.PasswordAuthentication
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class activity_recuperarContrasena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasena)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val txtCorreoRe = findViewById<EditText>(R.id.txtCorreoRe)


        btnEnviar.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val codigoRecu = (100000..999999).random()
                enviarCorreo ( "dlphcontac@gmail.com, Recuperación de contraseña", "Recuperacion", "Su código de recuperación es: $codigoRecu")
            }
        }

        btnEnviar.setOnClickListener {
            val correo = txtCorreoRe.text.toString()
            var hayError = false
            if (!correo.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+[.]+[a-z]+"))) {
                txtCorreoRe.error = "El correo es obligatorio"
                hayError = true
            } else {
                txtCorreoRe.error = null
            }
            CoroutineScope(Dispatchers.Main).launch {
                val codigoRecu = (100000..999999).random()
                enviarCorreo(txtCorreoRe.text.toString(), "Recuperación de contraseña", "Su código de recuperación es: $codigoRecu")
            }


        }
    }
}
