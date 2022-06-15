package com.taller.ngen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        Thread.sleep(2000)
        setTheme(R.style.Theme_Ngen)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        versione()
    }

    private fun versione(){
        title = "                          Inugami"

        var email=findViewById<EditText>(R.id.emailText)
        var pass= findViewById<EditText>(R.id.passText)
        var access= findViewById<Button>(R.id.loginBtn)

        access.setOnClickListener{
            if (email.text.isNotEmpty() && pass.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),
                    pass.text.toString()).addOnCompleteListener {

                    if (it.isSuccessful){
                        db.collection("users").document(email.text.toString()).get()
                            .addOnSuccessListener {
                                var permiso = it.data?.get("permiso").toString()


                                if (permiso == "admin") {
                                    ShowHomeAdmin()
                                } else {
                                    if (permiso == "utp") {
                                        ShowHomeUtp()
                                    } else {
                                        ShowAlertPermiso()
                                    }
                                }
                            }
                    }else{
                        ShowAlertAccess()
                    }

                }
            }else{
                ShowAlertAccess2()
            }
        }
    }


    private fun ShowHomeUtp(){
        var email = findViewById<EditText>(R.id.emailText)

        val homeintent = Intent(this, Utp::class.java).apply {
            putExtra("email", email.text.toString())
        }
        startActivity(homeintent)

    }

    private fun ShowHomeAdmin(){
        var email = findViewById<EditText>(R.id.emailText)

        val homeintentProf = Intent(this, Admin::class.java).apply {
            putExtra("email", email.text.toString())
        }
        startActivity(homeintentProf)

    }

    private fun ShowAlertAccess(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Usuario o contraseña erroneos")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun ShowAlertAccess2(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Complete la información solicitada")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun ShowAlertPermiso(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("El usurio no posee permisos de acceso")
        builder.setPositiveButton("aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}