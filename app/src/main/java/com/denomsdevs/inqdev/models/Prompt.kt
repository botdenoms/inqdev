package com.denomsdevs.inqdev.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prompts")
class Prompt (
    @PrimaryKey
    @ColumnInfo(name = "p_id")
    val id: Long,
    @ColumnInfo(name = "p_date")
    val date: Long,
    @ColumnInfo(name = "p_error")
    val error: Boolean,
    @ColumnInfo(name = "p_prompt")
    val prompt: String,
    @ColumnInfo(name = "p_response")
    val response: String
)