package com.taller.ngen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class AdminPantalla2 : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_pantalla2)

        fundAdmin2()
    }

    private fun fundAdmin2(){
        title="Otras Funciones del Administrador"

        val bundle2 = intent.extras
        var email2 = bundle2?.getString("email")

        var nameUser2 = findViewById<TextView>(R.id.nameUser2)


        db.collection("users").document(email2.toString()).get()
            .addOnSuccessListener {
                var name2 = it.data?.get("nombre").toString()
                nameUser2.setText(name2)
            }
    }
}