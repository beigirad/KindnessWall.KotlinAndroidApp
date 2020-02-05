package com.farshidabz.kindnesswall.view.charity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshidabz.kindnesswall.data.local.dao.charity.CharityModel
import com.farshidabz.kindnesswall.data.model.CustomResult
import com.farshidabz.kindnesswall.data.repository.CharityRepo

class CharityListViewModel(private val charityRepo: CharityRepo) : ViewModel() {
    private var lastId = 0L

    val charityItems: LiveData<CustomResult<List<CharityModel>>> by lazy {
        charityRepo.getCharityFirstPage(viewModelScope)
    }

    fun getCharityItemsFromServer(): LiveData<CustomResult<List<CharityModel>>> {
        lastId = charityItems.value?.data?.last()?.id ?: 0
        return charityRepo.getCharities(viewModelScope, lastId)
    }
}