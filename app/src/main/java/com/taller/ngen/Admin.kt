package com.taller.ngen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class Admin : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        setOption()

    }

    private fun setOption(){
        title = "Bienvenid@ a la sección para administradores"

        val bundle = intent.extras
        var email = bundle?.getString("email")

        var emailName = findViewById<TextView>(R.id.nameUser)
        var btnOtra = findViewById<Button>(R.id.btnOtrAdmin)

        db.collection("users").document(email.toString()).get()
            .addOnSuccessListener {
                var nameUserAdmin = it.data?.get("nombre").toString()
                emailName.setText(nameUserAdmin)
            }

        btnOtra.setOnClickListener(){
            val goAdmin2 = Intent(this, AdminPantalla2::class.java).apply {
                putExtra("email", email.toString())

            }
            startActivity(goAdmin2)

        }
    }
}