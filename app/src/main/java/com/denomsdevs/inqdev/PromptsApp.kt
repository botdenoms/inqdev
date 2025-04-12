package com.denomsdevs.inqdev

import android.app.Application
import android.content.Context

class PromptsApp : Application() {
    init { app = this }
    companion object{
        private lateinit var app: PromptsApp
        fun getAppContext(): Context {
            return app.applicationContext
        }
    }
}