package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AgregarInventario : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_inventario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imgmenuIn = findViewById<ImageView>(R.id.imgmenuIn)
        val imagMedi = findViewById<ImageView>(R.id.imgMedi)
        val btnTomarFotoMedi = findViewById<Button>(R.id.btnTomarFotoMedi)
        val btnSubirFotoMedi = findViewById<Button>(R.id.btnSubirFotoMedi)
        val txtNombreMedi = findViewById<EditText>(R.id.txtNombreMedi)
        val txtMarcaMedi = findViewById<EditText>(R.id.txtMarcaMedi)
        val spnMedicamento = findViewById<Spinner>(R.id.spnCantidadMedi)
        val btnAgregarMedi = findViewById<Button>(R.id.btnAgregarMedi)
        val txtSpinnerError = findViewById<TextView>(R.id.txtSpinnerError)

        val spnCantidadMedi = arrayOf("1","2","3","4","5","6","7","8","9","10")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spnCantidadMedi)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnMedicamento.adapter = adapter

        val rcvItems = findViewById<RecyclerView>(R.id.rcvItems)
        rcvItems.layoutManager = LinearLayoutManager(this)

        btnAgregarMedi.setOnClickListener {
            val intent = Intent(this, InventarioMedicamento::class.java)
            startActivity(intent)

            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val addMedicamento = objConexion?.prepareStatement("INSERT INTO Inventario(UUID_INVENTARIO,NOMBRE,MARCA ,CANTIDAD,FOTO_MEDICAMENTO)VALUES (?, ?, ?, ?, ?)")!!
                addMedicamento.setString(1,txtNombreMedi.text.toString())
                addMedicamento.setString(2,txtMarcaMedi.text.toString())
                addMedicamento.setString(3,imagMedi.textDirection.toString())
                val cantidadSeleccionada = spnMedicamento.selectedItem.toString()
                addMedicamento.setString(4, cantidadSeleccionada)
                //imagen ruta
                addMedicamento.setString(5, "")
                addMedicamento.executeUpdate()

                var hayError = false
                if (txtNombreMedi.text.toString().isEmpty()) {
                    txtNombreMedi.error = "El nombre es obligatorio"
                    hayError = true
                } else {
                    txtNombreMedi.error = null
                }
                if (txtMarcaMedi.text.toString().isEmpty()) {
                    txtMarcaMedi.error = "La marca es obligatoria"
                    hayError = true
                } else {
                    txtMarcaMedi.error = null
                }
                if (spnMedicamento.selectedItem == null || spnMedicamento.selectedItem.toString().isEmpty()) {
                    txtSpinnerError.visibility = TextView.VISIBLE
                    hayError = true
                } else {
                    txtSpinnerError.visibility = TextView.GONE
                }
            }
        }
    }
}