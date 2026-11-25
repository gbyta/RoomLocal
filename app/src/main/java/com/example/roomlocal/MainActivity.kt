package com.example.roomlocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomlocal.data.RoomLocalDatabase
import com.example.roomlocal.ui.RecordatoriosScreen
import com.example.roomlocal.ui.RecordatoriosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val baseDatos = RoomLocalDatabase.obtenerInstancia(applicationContext)

        setContent {
            val vmRecordatorios: RecordatoriosViewModel = viewModel(
                factory = RecordatoriosViewModel.Proveedor(baseDatos.recordatorioDao())
            )
            RecordatoriosScreen(viewModel = vmRecordatorios)
        }
    }
}