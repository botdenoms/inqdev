package com.denomsdevs.inqdev.persistance

import androidx.room.*
import com.denomsdevs.inqdev.models.Prompt

@Dao
interface PromptsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(prompts: List<Prompt>)

    @Query("SELECT * FROM prompts")
    fun getAll(): List<Prompt>
}