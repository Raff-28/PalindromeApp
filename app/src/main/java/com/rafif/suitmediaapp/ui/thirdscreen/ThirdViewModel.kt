package com.rafif.suitmediaapp.ui.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafif.suitmediaapp.data.model.DataItem
import com.rafif.suitmediaapp.data.repository.Repository

class ThirdViewModel(repository: Repository) : ViewModel() {
    val user: LiveData<PagingData<DataItem>> =
        repository.getUser().cachedIn(viewModelScope)
}