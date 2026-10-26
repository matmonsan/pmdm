package com.ejemplo.miapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class PresentacionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MiAppTheme {
                PantallaPresentacion()
            }
        }
    }
}

@Composable
fun PantallaPresentacion() {
    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "MiAgenda",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text("Una aplicación para guardar tus contactos y tareas.")

        Card(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Guarda tus contactos y consúltalos en cualquier momento.",
                modifier = Modifier.padding(16.dp),
            )
        }

        Button(onClick = {
            contexto.startActivity(Intent(contexto, LoginActivity::class.java))
            (contexto as Activity).finish()
        }) {
            Text("Cerrar sesión")
        }
    }
}
