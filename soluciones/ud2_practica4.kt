import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

// En MainActivity: setContent { MiAppTheme { PantallaPerfil() } }

@Composable
fun PantallaPerfil() {
    var nombre by rememberSaveable { mutableStateOf("Ana") }
    var seguidores by rememberSaveable { mutableIntStateOf(0) }
    var siguiendo by rememberSaveable { mutableStateOf(false) }
    var modoEdicion by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Perfil",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        TarjetaPerfil(nombre, seguidores, siguiendo)

        BotonSeguir(
            siguiendo = siguiendo,
            onClick = {
                siguiendo = !siguiendo
                if (siguiendo) {
                    seguidores++
                } else {
                    seguidores--
                }
            },
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = modoEdicion,
                onCheckedChange = { modoEdicion = it },
            )
            Text("Editar perfil")
        }

        if (modoEdicion) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
            )
        }

        Button(onClick = {
            nombre = "Ana"
            seguidores = 0
            siguiendo = false
            modoEdicion = false
        }) {
            Text("Restablecer")
        }
    }
}

@Composable
fun TarjetaPerfil(nombre: String, seguidores: Int, siguiendo: Boolean) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                // first() falla con el texto vacío, por eso se comprueba antes
                if (nombre.isNotEmpty()) {
                    Text(nombre.first().toString())
                }
            }
            Column {
                Text(nombre, style = MaterialTheme.typography.titleMedium)
                Text("$seguidores seguidores", style = MaterialTheme.typography.bodyMedium)
                if (siguiendo) {
                    Text("Siguiendo")
                } else {
                    Text("No sigues a este usuario")
                }
            }
        }
    }
}

@Composable
fun BotonSeguir(siguiendo: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick) {
        if (siguiendo) {
            Text("Dejar de seguir")
        } else {
            Text("Seguir")
        }
    }
}
