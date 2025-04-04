package com.denomsdevs.inqdev.models

import java.util.Date

data class Prompts(
    val date: Date,
    val error: Boolean,
    val prompt: String,
    val response: String,
)
