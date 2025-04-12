package com.denomsdevs.inqdev

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denomsdevs.inqdev.models.Prompt
import com.denomsdevs.inqdev.models.promptItems
import com.denomsdevs.inqdev.persistance.PromptsDao
import com.denomsdevs.inqdev.persistance.PromptsDb
import kotlinx.coroutines.*

class PromptViewModel : ViewModel() {

    val prompts = mutableStateOf(emptyList<Prompt>())
    val fetching = mutableStateOf(true)
    private var promptsDao: PromptsDao = PromptsDb.getDaoInstance(PromptsApp.getAppContext())
    
    private suspend fun getPromptsFake(): List<Prompt>{
         delay(3000)
         return promptItems
    }

    private suspend fun getPromptsPersisted():List<Prompt>{
        return promptsDao.getAll()
    }

    private suspend fun addPromptsPersisted(prompts: List<Prompt>){
        return promptsDao.addAll(prompts)
    }

    fun getPrompts() {
        fetching.value = true
        viewModelScope.launch(Dispatchers.IO) {
            // val items = getPromptsPersisted()
            val items = getPromptsFake()
            withContext(Dispatchers.Main){
                prompts.value = items
                fetching.value = false
            }
        }
    }
}