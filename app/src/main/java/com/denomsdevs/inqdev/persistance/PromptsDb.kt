package com.denomsdevs.inqdev.persistance

import android.content.Context
import androidx.room.*
import com.denomsdevs.inqdev.models.Prompt

@Database(
    entities = [Prompt::class],
    version = 1,
    exportSchema = false)
abstract class PromptsDb : RoomDatabase(){
    abstract val dao: PromptsDao
    companion object{
        @Volatile
        private var INSTANCE: PromptsDao? = null

        fun getDaoInstance(context: Context): PromptsDao {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }

        private fun buildDatabase(context: Context): PromptsDb = Room.databaseBuilder(
                context.applicationContext,
                PromptsDb::class.java,
                "prompts_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}