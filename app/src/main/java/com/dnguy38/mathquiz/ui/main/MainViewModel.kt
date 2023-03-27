package com.dnguy38.mathquiz.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.mathquiz.models.Configuration

class MainViewModel : ViewModel() {
    private val _configurationList = MutableLiveData<Array<Configuration>>()
    var configurationList: LiveData<Array<Configuration>> = _configurationList

    init {
        _configurationList.value = Configuration.values()
    }
}