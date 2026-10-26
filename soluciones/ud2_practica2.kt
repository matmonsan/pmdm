import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// En MainActivity: setContent { MiAppTheme { PantallaContador() } }

@Composable
fun PantallaContador() {
    var contador by rememberSaveable { mutableIntStateOf(0) }

    val mensaje = if (contador == 0) {
        "Vacío"
    } else {
        "Has pulsado $contador veces"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MostrarContador(contador)
        Text(mensaje)

        BotonesContador(
            onSumar = { contador++ },
            onRestar = { contador-- },
        )
        Button(onClick = { contador = 0 }) {
            Text("Reiniciar")
        }
    }
}

@Composable
fun MostrarContador(contador: Int) {
    Text("$contador", style = MaterialTheme.typography.displayMedium)
}

@Composable
fun BotonesContador(onSumar: () -> Unit, onRestar: () -> Unit) {
    Button(onClick = onSumar) { Text("Sumar uno") }
    Button(onClick = onRestar) { Text("Restar uno") }
}
