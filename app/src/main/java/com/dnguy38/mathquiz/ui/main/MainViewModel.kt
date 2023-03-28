package com.dnguy38.mathquiz.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnguy38.mathquiz.models.Configuration
import com.dnguy38.mathquiz.models.Problem
import com.dnguy38.mathquiz.models.MathQuiz
import com.dnguy38.mathquiz.models.Operation

class MainViewModel : ViewModel() {
    private val _configurationList = MutableLiveData<Array<Configuration>>()
    var configurationList: LiveData<Array<Configuration>> = _configurationList
    private val _problemList = MutableLiveData<List<Problem>>()
    var problemList: LiveData<List<Problem>> = _problemList

    init {
        _configurationList.value = Configuration.values()
        println("Initialized")
    }

    fun generateProblemList(numberOfProblems: Int, operation: Operation, operandLimit: Int) {
        _problemList.value = MathQuiz.newProblemSet(numberOfProblems, operation, operandLimit)
    }
}