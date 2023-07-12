package com.rafif.suitmediaapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rafif.suitmediaapp.data.UserPagingSource
import com.rafif.suitmediaapp.data.model.DataItem
import com.rafif.suitmediaapp.data.remote.network.ApiService

class Repository private constructor(
    private val apiService: ApiService,
){
    fun getUser(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                UserPagingSource(apiService)
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(
                    apiService
                )
            }.also { instance = it }
    }
}