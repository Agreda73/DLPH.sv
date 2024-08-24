package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.sql.Connection
import java.util.UUID

class AgregarExpediente : AppCompatActivity() {
    val codigo_opcion_galeria = 8
    val codigo_opcion_tomar_foto = 9
    val CAMARA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE = 3

    lateinit var imageView: ImageView
    lateinit var miPath: String
    lateinit var txtAgregarExpediente: EditText
    lateinit var spCategoriaAe: Spinner
    lateinit var sphabitacionAE: Spinner
    lateinit var txtDiagnostiAe: EditText
    lateinit var txtHistoriaExpediente: EditText

    val uuide = UUID.randomUUID().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_expediente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        imageView = findViewById(R.id.imgAgregarExp)
        val imgmenuIn = findViewById<ImageView>(R.id.imgmenuexp)
        val btnTomarftexp = findViewById<Button>(R.id.btnTomarftexp)
        val btnSubirftexp = findViewById<Button>(R.id.btnSubirftexp)
        val txtAgregarExpediente = findViewById<EditText>(R.id.txtAgregarExpediente)
        val sphabitacionAE = findViewById<Spinner>(R.id.sphabitacionAE)
        val spCategoriaAe = findViewById<Spinner>(R.id.spCategoriaAe)
        val txtDiagnostiAe = findViewById<EditText>(R.id.txtDiagnostiAe)
        val txtHistoriaExpediente = findViewById<EditText>(R.id.txtHistorialExpediente)
        val btnAgregarExpediente = findViewById<Button>(R.id.btnAgregarExpediente)
        val txtSpinnerErrorExpC = findViewById<TextView>(R.id.txtSpinnerErrorExpCa)
        val txtSpinnerErrorExpH = findViewById<TextView>(R.id.txtSpinnerErrorExpH)

        btnSubirftexp.setOnClickListener {
            checkStoragePermission()

        }

        btnTomarftexp.setOnClickListener {
            checkCameraPermission()
        }

        btnAgregarExpediente.setOnClickListener {

            var hayError = false

            if (txtAgregarExpediente.text.isEmpty() || txtAgregarExpediente.text.trim().isEmpty()) {
                txtAgregarExpediente.error = "Campo requerido"
            } else if (txtAgregarExpediente.text.length < 25) {
                txtAgregarExpediente.error = "Mínimo 25 caracteres"
            }

            if (sphabitacionAE.selectedItem == null || sphabitacionAE.selectedItem.toString().isEmpty()) {
                txtSpinnerErrorExpH.visibility = TextView.VISIBLE
                hayError = true
            } else {
                txtSpinnerErrorExpH.visibility = TextView.GONE
            }

            if (spCategoriaAe.selectedItem == null || spCategoriaAe.selectedItem.toString().isEmpty()) {
                txtSpinnerErrorExpC.visibility = TextView.VISIBLE
                hayError = true
            } else {
                txtSpinnerErrorExpC.visibility = TextView.GONE
            }

            if (txtDiagnostiAe.text.isEmpty() || txtDiagnostiAe.text.trim().isEmpty()) {
                txtDiagnostiAe.error = "Campo requerido"
            } else if (txtDiagnostiAe.text.length < 50) {
                txtDiagnostiAe.error = "Mínimo 50 caracteres"
            }

            if ( txtHistoriaExpediente.text.isEmpty() || txtHistoriaExpediente.text.trim().isEmpty()) {
                txtHistoriaExpediente.error = "Campo requerido"
            } else if ( txtHistoriaExpediente.text.length < 25) {
                txtHistoriaExpediente.error = "Mínimo 25 caracteres"
            }
            if (!hayError) {
                CoroutineScope(Dispatchers.IO).launch {
                    val exito = GuardarCambios()
                    if (exito) {
                        runOnUiThread {
                            Toast.makeText(this@AgregarExpediente, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AgregarExpediente, Expediente::class.java)
                            startActivity(intent)
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@AgregarExpediente, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pedirPermisoCamara()
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,codigo_opcion_tomar_foto)
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            pedirPermisoAlmacenamiento()
        } else {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, codigo_opcion_galeria)
        }
    }
    private fun pedirPermisoCamara(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)
        ) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),CAMARA_REQUEST_CODE)

        }

    }

    private fun pedirPermisoAlmacenamiento(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else{

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array< String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMARA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, codigo_opcion_tomar_foto)
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }

            STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, codigo_opcion_galeria)
                } else {
                    Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            else -> {

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK) {
            when (requestCode) {

                codigo_opcion_galeria -> {
                    val imageUri: Uri? = data?.data
                    imageUri.let {
                        val imagenBitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirimagenFirebase(imagenBitmap) { url ->
                            miPath = url
                            imageView.setImageURI(it)
                        }
                    }
                }

                codigo_opcion_tomar_foto -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let {
                        subirimagenFirebase(it) { url ->
                            miPath = url
                            imageView.setImageBitmap(it)

                        }
                    }
                }

            }
        }
    }

    private fun subirimagenFirebase(bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuide}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(this@AgregarExpediente, "Error al subir la imagen", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())

            }
        }

    }
private fun GuardarCambios(): Boolean {
    return try {
        val connection: Connection? = ClaseConexion().cadenaConexion()
        if (connection != null) {
            val AgregaExpediente = "INSERT INTO Expediente(UUID_Expediente, nombre, habitacion, categoria ,diagnostico ,historial , FOTO_EXPEDIENTE) VALUES (?, ?, ?, ?, ?,?,?)"
            val AddExpediente = connection.prepareStatement(AgregaExpediente)
            AddExpediente.setString(1, txtAgregarExpediente.text.toString())
            AddExpediente.setString(2, spCategoriaAe.selectedItem.toString())
            AddExpediente.setString(3, sphabitacionAE.selectedItem.toString())
            AddExpediente.setString(4, txtDiagnostiAe.text.toString())
            AddExpediente.setString(5, txtDiagnostiAe.text.toString())
            AddExpediente.setString(6, "")
            AddExpediente.executeUpdate()
            AddExpediente.close()
            connection.close()
            true
        } else {
            false
        }
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}



}