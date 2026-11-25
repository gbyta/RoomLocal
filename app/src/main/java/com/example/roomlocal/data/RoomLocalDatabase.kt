package com.example.roomlocal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RecordatorioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RoomLocalDatabase : RoomDatabase() {

    abstract fun recordatorioDao(): RecordatorioDao

    companion object {
        @Volatile
        private var instancia: RoomLocalDatabase? = null

        fun obtenerInstancia(contexto: Context): RoomLocalDatabase {
            return instancia ?: synchronized(this) {
                val creada = Room.databaseBuilder(
                    contexto.applicationContext,
                    RoomLocalDatabase::class.java,
                    "room_local_db"
                ).build()
                instancia = creada
                creada
            }
        }
    }
}