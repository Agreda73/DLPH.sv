package roberto.agreda.dlphsv

import Modelos.ClaseConexion
import android.annotation.SuppressLint
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

class AgregarInventario : AppCompatActivity() {
   /* val codigo_opcion_galeria = 5
    val codigo_opcion_tomar_foto = 6
    val CAMARA_REQUEST_CODE = 0
    val STORAGE_REQUEST_CODE = 1*/

    //lateinit var imageView: ImageView
   // lateinit var miPath: String
   lateinit var txtNombreMedi : EditText
    lateinit var txtMarcaMedi: EditText
    lateinit var txtCantidad: EditText

  //  val uuid = UUID.randomUUID().toString()


   @SuppressLint("WrongViewCast")
   override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_inventario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
       //imageView = findViewById(R.id.imgmedi)
        val imgmenuIn = findViewById<ImageView>(R.id.imgmenuIn)
       val menu =Intent(this,Menu::class.java)
        val btnTomarFotoMedi = findViewById<Button>(R.id.btnTomarFotoMedi)
        val btnSubirFotoMedi = findViewById<Button>(R.id.btnSubirFotoMedi)
        val txtNombreMedi = findViewById<EditText>(R.id.txtNombreMedi)
        val txtMarcaMedi = findViewById<EditText>(R.id.txtMarcaMedi)
        val txtCantidad = findViewById<EditText>(R.id.txtCantidad)
        val btnAgregarMedi = findViewById<Button>(R.id.btnAgregarMedi)


       imgmenuIn.setOnClickListener{
           startActivity(menu)
       }
       /*btnSubirFotoMedi.setOnClickListener {
           checkStoragePermission()

       }

       btnTomarFotoMedi.setOnClickListener {
           checkCameraPermission()
       }*/
       btnAgregarMedi.setOnClickListener {
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
           if (txtCantidad.text.toString().isEmpty()) {
               txtCantidad.error = "La cantidad es obligatoria"
               hayError = true
           } else {
               txtMarcaMedi.error = null
           }
       if (!hayError) {
           CoroutineScope(Dispatchers.IO).launch {
               val exito = guardarCambios()
               if (exito) {
                   runOnUiThread {
                       Toast.makeText(this@AgregarInventario, "Datos guardados con éxito", Toast.LENGTH_SHORT).show()
                       val intent = Intent(this@AgregarInventario, InventarioMedicamento::class.java)
                       startActivity(intent)
                   }
               } else {
                   runOnUiThread {
                       Toast.makeText(this@AgregarInventario, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }
      }
   }
    private fun guardarCambios(): Boolean {
        return try {
            val connection: Connection? = ClaseConexion().cadenaConexion()
            if (connection != null) {
                val agregamedicamento= "insert into medicamento(nombre,marca,cantidad)values(?,?,?)"
                val addMedicamento = connection.prepareStatement(agregamedicamento)
                addMedicamento.setString(1, txtNombreMedi.text.toString())
                addMedicamento.setString(2, txtMarcaMedi.text.toString())
                addMedicamento.setString(3, txtCantidad.text.toString())
                addMedicamento.executeUpdate()
                addMedicamento.close()
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

       /*private fun checkCameraPermission() {
           if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
               pedirPermisoCamara()
           } else {
               val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
               startActivityForResult(intent,codigo_opcion_tomar_foto)
           }
       }*/

       //private fun checkStoragePermission() {
         //  if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
           //    pedirPermisoAlmacenamiento()
           //} else {

               //val intent = Intent(Intent.ACTION_PICK)
         //      intent.type = "image/*"
           //    startActivityForResult(intent, codigo_opcion_galeria)
           //}
       }
    /*private fun pedirPermisoCamara(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)
        ) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),CAMARA_REQUEST_CODE)

        }

    }*/

    /*private fun pedirPermisoAlmacenamiento(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else{

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
        }
    }*/
     //override fun onRequestPermissionsResult(
      //  requestCode: Int,
     //   permissions: Array< String>,
       // grantResults: IntArray
//    ) {
    //    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      //  when (requestCode) {
        //    CAMARA_REQUEST_CODE -> {
          //      if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            //        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
              //      startActivityForResult(intent, codigo_opcion_tomar_foto)
                // } else {
          //          Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            //    }
              //  return
           // }

      //      STORAGE_REQUEST_CODE -> {
            //    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
        //            val intent = Intent(Intent.ACTION_PICK)
          //          intent.type = "image/*"
            //        startActivityForResult(intent, codigo_opcion_galeria)
              //  } else {
                //    Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_SHORT)
                  //      .show()
   //             }
     //       }

       //     else -> {

       //     }
//
  //      }

//    }*/
  //  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    //    super.onActivityResult(requestCode, resultCode, data)
      //  if (requestCode == Activity.RESULT_OK) {
        //    when (requestCode) {
//
  //              codigo_opcion_galeria -> {
    //                val imageUri: Uri? = data?.data
      //             imageUri.let {
       //                val imagenBitmap =
        //                    MediaStore.Images.Media.getBitmap(contentResolver, it)
          //              subirimagenFirebase(imagenBitmap) { url ->
            //                miPath = url
              //              imageView.setImageURI(it)
                //        }
                  //  }
           //     }

               /* codigo_opcion_tomar_foto -> {
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
            Toast.makeText(this@AgregarInventario, "Error al subir la imagen", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())

            }
        }

    }*/












