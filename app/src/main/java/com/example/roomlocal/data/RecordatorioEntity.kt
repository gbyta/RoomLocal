package com.example.roomlocal.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordatorios")
data class RecordatorioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "asunto")
    val asunto: String,
    @ColumnInfo(name = "categoria")
    val categoria: String,
    @ColumnInfo(name = "registrado_en")
    val registradoEn: Long = System.currentTimeMillis()
)