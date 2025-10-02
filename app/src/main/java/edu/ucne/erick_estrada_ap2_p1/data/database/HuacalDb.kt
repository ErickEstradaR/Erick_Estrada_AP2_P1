package edu.ucne.erick_estrada_ap2_p1.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalDao
import edu.ucne.erick_estrada_ap2_p1.data.huacal.HuacalEntity

@Database(
    entities = [
        HuacalEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class HuacalDb: RoomDatabase(){
    abstract fun HuacalDao(): HuacalDao
}