package roberto.agreda.dlphsv

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Receta : AppCompatActivity() {

    private lateinit var txtNombrepR: EditText
    private lateinit var txtTratamientoR: EditText
    private lateinit var spHabitacionR: Spinner
    private lateinit var txtidPacienteR: EditText
    private lateinit var btnGuardarR: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receta)

        txtNombrepR = findViewById(R.id.txtNombrepR)
        txtTratamientoR = findViewById(R.id.txtTratamientoR)
        spHabitacionR = findViewById(R.id.spHabitacionR)
        txtidPacienteR = findViewById(R.id.txtidPacienteR)
        btnGuardarR = findViewById(R.id.btnGuardarR)

        btnGuardarR.setOnClickListener {
            val nombreR = txtNombrepR.text.toString()
            val tratamientoR = txtTratamientoR.text.toString()
            val idPacienteR = txtidPacienteR.text.toString()
            val habitacionR = spHabitacionR.selectedItem.toString()

            Toast.makeText(this, "Datos guardados: $nombreR, $tratamientoR, $idPacienteR, $habitacionR", Toast.LENGTH_SHORT).show()

            if (nombreR.isEmpty()) {
                txtNombrepR.error = "Ingrese un nombre"
                return@setOnClickListener
            }

            if (tratamientoR.isEmpty()) {
                txtTratamientoR.error = "Ingrese un tratamiento"
                return@setOnClickListener
            }

            if (idPacienteR.isEmpty()) {
                txtidPacienteR.error = "Ingrese un ID de paciente"
                return@setOnClickListener
            }

            if (habitacionR.isEmpty()) {
                Toast.makeText(this, "Seleccione una habitación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            spHabitacionR.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val habitacion = parent.getItemAtPosition(position).toString()

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }

            txtNombrepR.text.clear()
            txtTratamientoR.text.clear()
            txtidPacienteR.text.clear()
            spHabitacionR.setSelection(0)

        }
    }

}




