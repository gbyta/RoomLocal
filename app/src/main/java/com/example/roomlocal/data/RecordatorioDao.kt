package com.example.roomlocal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordatorioDao {

    @Query("SELECT * FROM recordatorios ORDER BY registrado_en DESC")
    fun consultarTodos(): Flow<List<RecordatorioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarRecordatorio(item: RecordatorioEntity)

    @Update
    suspend fun modificarRecordatorio(item: RecordatorioEntity)

    @Delete
    suspend fun borrarRecordatorio(item: RecordatorioEntity)
}