package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.sql.SQLException
import java.util.UUID

class Perfil : AppCompatActivity() {
    val codigo_opcion_galeria = 2
    val codigo_opcion_tomar_foto = 3
    val CAMARA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE = 1

    lateinit var imageView: ImageView
    lateinit var miPath:String
    lateinit var lblnombrePerfil: String
    lateinit var sprolPerfil: String
    lateinit var spgeneroPerfil: String
    lateinit var lblidPerfil: String

    val uuid = UUID.randomUUID().toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Perfil)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.imgPerfil)
        val btnGaleria = findViewById<Button>(R.id.btnSubirimg)
        val btnFoto = findViewById<Button>(R.id.btntomarFoto)
        val lblnombrePerfil = findViewById<TextView>(R.id.lblnombrePerfil)

        val btnbackP = findViewById<ImageView>(R.id.btnbackP)
        val Menu =Intent(this,Menu::class.java)


        //Falatan los dos spinner
        val lblidPerfil = findViewById<TextView>(R.id.lblidPerfil)

        btnbackP.setOnClickListener{
            startActivity(Menu)
        }

        btnGaleria.setOnClickListener {
            checkStoragePermission()
        }

        btnFoto.setOnClickListener {
            checkCameraPermission()

        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            pedirPermisoCamara()
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,codigo_opcion_tomar_foto )
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
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(this@Perfil, "Error al subir la imagen", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())

            }
        }

    }

    private fun guardarCambios(spgeneroPerfil:String,imgeView: ImageView ) {
        try {
            GlobalScope.launch (Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                val statement = objConexion?.prepareStatement("INSERT INTO Perfil (UUID, nombre, rol, FOTO_PERFIL, id_perfil, Sexo) VALUES (?, ?, ?, ?, ?, ? )")!!
                statement.setString(1, uuid)
                statement.setString(2, imageView.toString())
                statement.executeUpdate()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Perfil, "Datos guardados", Toast.LENGTH_SHORT).show()
                    imageView.setImageResource(0)
                    imageView.tag = null
                }
            }
        } catch (e: SQLException) {
            println("Error al guardar usuario: $e")
        }
    }

    override fun onResume() {
        super.onResume()

        val NombrePerfil: TextView = findViewById(R.id.lblnombrePerfil)
        val GeneroPerfil: Spinner = findViewById(R.id.spgeneroperfil)
        val IdPerfil: TextView = findViewById(R.id.lblidPerfil)
        val RolPerfil: Spinner = findViewById(R.id.sprolPerfil)

        NombrePerfil.text = lblnombrePerfil
        IdPerfil.text = lblidPerfil

        val generoAdapter = ArrayAdapter.createFromResource(this, R.array.generos, android.R.layout.simple_spinner_item)
        GeneroPerfil.adapter = generoAdapter

        GeneroPerfil.setSelection(generoAdapter.getPosition(spgeneroPerfil))

        IdPerfil.text = lblidPerfil

    }


}