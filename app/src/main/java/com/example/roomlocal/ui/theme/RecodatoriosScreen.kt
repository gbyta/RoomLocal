package com.example.roomlocal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomlocal.data.RecordatorioEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordatoriosScreen(viewModel: RecordatoriosViewModel) {
    val itemsGuardados by viewModel.listaRecordatorios.collectAsState()
    var asuntoTexto by remember { mutableStateOf("") }
    var categoriaTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("RoomLocal - Recordatorios") })
        }
    ) { insets ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = asuntoTexto,
                onValueChange = { asuntoTexto = it },
                label = { Text("Asunto") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = categoriaTexto,
                onValueChange = { categoriaTexto = it },
                label = { Text("Categoría") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    viewModel.registrarNuevo(asuntoTexto, categoriaTexto)
                    asuntoTexto = ""
                    categoriaTexto = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Text("Agregar", modifier = Modifier.padding(start = 6.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (itemsGuardados.isEmpty()) {
                Text(
                    text = "Sin recordatorios almacenados en local.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(items = itemsGuardados, key = { it.id }) { registro ->
                        TarjetaRecordatorio(
                            registro = registro,
                            onBorrar = { viewModel.quitarRecordatorio(registro) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaRecordatorio(
    registro: RecordatorioEntity,
    onBorrar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = registro.asunto,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Categoría: ${registro.categoria}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onBorrar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar recordatorio"
                )
            }
        }
    }
}