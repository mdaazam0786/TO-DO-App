package com.example.to_do.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity
data class Todo(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    val date : String,
    @PrimaryKey val id: Int? = null,

)
