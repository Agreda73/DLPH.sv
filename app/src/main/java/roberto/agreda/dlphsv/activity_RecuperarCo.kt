package roberto.agreda.dlphsv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_RecuperarCo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_co)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtCodigoRe = findViewById<EditText>(R.id.txtCodigoRe)
        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val codigoRecuEnviado = intent.getStringExtra("codigoRecu")


        btnConfirmar.setOnClickListener {
            val txtCodigoRe = txtCodigoRe.text.toString()
            var hayError = false
            if (codigoRecuEnviado == codigoRecuEnviado) {
                Toast.makeText(this, "Código correcto", Toast.LENGTH_SHORT).show()
            } else {
                // Código incorrecto, mostrar mensaje de error
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}