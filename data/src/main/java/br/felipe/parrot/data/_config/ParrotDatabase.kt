package br.felipe.parrot.data._config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.felipe.parrot.data.BuildConfig
import br.felipe.parrot.data.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Database(
    version = BuildConfig.VERSION_CODE,
    exportSchema = false,
    entities = [
        UserEntity::class
    ]
)
abstract class ParrotDatabase: RoomDatabase() {

    companion object {

        private var instance: ParrotDatabase? = null
        private var dropJob: Job? = null

        fun getInstance(context: Context): ParrotDatabase {
            return instance ?: synchronized(this) {
                instance ?: synchronized(this) {
                    instance = buildDatabase(context)
                    instance!!
                }
            }
        }

        suspend fun dropDatabase () {
            dropJob = GlobalScope.launch(Dispatchers.Default) {
                instance?.runInTransaction { instance?.clearAllTables() }
            }.apply { join() }
        }

        private fun buildDatabase(context: Context): ParrotDatabase {
            return Room.databaseBuilder(context, ParrotDatabase::class.java, "PARROT_DB")
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}