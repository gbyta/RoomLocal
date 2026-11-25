package com.example.roomlocal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.roomlocal.data.RecordatorioDao
import com.example.roomlocal.data.RecordatorioEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecordatoriosViewModel(private val dao: RecordatorioDao) : ViewModel() {

    val listaRecordatorios: StateFlow<List<RecordatorioEntity>> = dao.consultarTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun registrarNuevo(asunto: String, categoria: String) {
        val asuntoLimpio = asunto.trim()
        val categoriaLimpia = categoria.trim()
        if (asuntoLimpio.isEmpty() && categoriaLimpia.isEmpty()) return

        viewModelScope.launch {
            dao.guardarRecordatorio(
                RecordatorioEntity(
                    asunto = asuntoLimpio.ifEmpty { "Recordatorio general" },
                    categoria = categoriaLimpia.ifEmpty { "General" }
                )
            )
        }
    }

    fun quitarRecordatorio(item: RecordatorioEntity) {
        viewModelScope.launch {
            dao.borrarRecordatorio(item)
        }
    }

    class Proveedor(private val dao: RecordatorioDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecordatoriosViewModel(dao) as T
        }
    }
}