package Modelos

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.1.18:1521:xe"
            val usuario = "Agreda"
            val contrasena = "ITR2020"
            val connection = DriverManager.getConnection(url,usuario,contrasena)
            return connection
        }catch (e:Exception){
            println("error: $e")
            return null }
    }
}