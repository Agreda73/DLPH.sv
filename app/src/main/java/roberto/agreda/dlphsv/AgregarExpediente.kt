package roberto.agreda.dlphsv

import android.os.Bundle
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AgregarExpediente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_expediente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val txtAgregarExpediente = findViewById<EditText>(R.id.txtAgregarExpediente)

        if (txtAgregarExpediente.text.isEmpty() || txtAgregarExpediente.text.trim().isEmpty()) {
            txtAgregarExpediente.error = "Campo requerido"
        } else if (txtAgregarExpediente.text.length < 25) {
            txtAgregarExpediente.error = "Mínimo 25 caracteres"
        }

        val sphabitacionAE = findViewById<Spinner>(R.id.sphabitacionAE)

        if (sphabitacionAE.selectedItemPosition == 0) {
        }

        val txtDiagnostiAe = findViewById<EditText>(R.id.txtDiagnostiAe)

        if (txtDiagnostiAe.text.isEmpty() || txtDiagnostiAe.text.trim().isEmpty()) {
            txtDiagnostiAe.error = "Campo requerido"
        } else if (txtDiagnostiAe.text.length < 50) {
            txtDiagnostiAe.error = "Mínimo 50 caracteres"
        }

        val txtHistoriaExpediente = findViewById<EditText>(R.id.txtHistorialExpediente)

        if ( txtHistoriaExpediente.text.isEmpty() || txtHistoriaExpediente.text.trim().isEmpty()) {
             txtHistoriaExpediente.error = "Campo requerido"
        } else if ( txtHistoriaExpediente.text.length < 25) {
            txtHistoriaExpediente.error = "Mínimo 25 caracteres"
        }

    }
}