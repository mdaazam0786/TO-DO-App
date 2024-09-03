package com.example.to_do.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do.util.Converters

@Database(
    entities = [Todo::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TodoDatabase: RoomDatabase() {

    abstract val dao: TodoDao
}