package com.rafif.suitmediaapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafif.suitmediaapp.data.model.DataItem
import com.rafif.suitmediaapp.data.remote.network.ApiService

class UserPagingSource(private val apiService: ApiService) : PagingSource<Int, DataItem>() {

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            Log.d("UserPagingSource", "Loading page: $page")

            val response = apiService.getUsers(page, params.loadSize)
            val responseData = response.body()?.data

            if (responseData != null) {
                val nextKey = if (responseData.size < params.loadSize) null else page + 1
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(NullPointerException("User data is null"))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}