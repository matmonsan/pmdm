import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// En MainActivity: setContent { MiAppTheme { PantallaFormulario() } }

@Composable
fun PantallaFormulario() {
    var nombre by rememberSaveable { mutableStateOf("") }
    var aceptado by rememberSaveable { mutableStateOf(false) }
    var mensaje by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CampoNombre(
            nombre = nombre,
            onNombreChange = { nombre = it },
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = aceptado,
                onCheckedChange = { aceptado = it },
            )
            Text("Acepto las condiciones")
        }

        Button(
            onClick = { mensaje = "Registro enviado: $nombre" },
            enabled = aceptado && nombre.isNotBlank(),
        ) {
            Text("Enviar")
        }
        Text(mensaje)

        Button(onClick = {
            nombre = ""
            aceptado = false
            mensaje = ""
        }) {
            Text("Borrar")
        }
    }
}

@Composable
fun CampoNombre(nombre: String, onNombreChange: (String) -> Unit) {
    OutlinedTextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = { Text("Nombre") },
    )
}
