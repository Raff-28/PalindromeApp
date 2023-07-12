package com.rafif.suitmediaapp.injection

import android.content.Context
import com.rafif.suitmediaapp.data.remote.network.ApiConfig
import com.rafif.suitmediaapp.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(
            apiService,
        )
    }
}