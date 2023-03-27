package com.dnguy38.mathquiz.models

import java.util.function.BinaryOperator
import java.util.function.IntBinaryOperator
import kotlin.random.Random

enum class Operation(val symbol: String): BinaryOperator<Int>, IntBinaryOperator {
    Addition("+") {
        override fun apply(p0: Int, p1: Int): Int = p0 + p1
    },
    Subtraction("-") {
        override fun apply(p0: Int, p1: Int): Int = p0 - p1
    },
    Multiplication("*") {
        override fun apply(p0: Int, p1: Int): Int = p0 * p1
    };

    override fun applyAsInt(p0: Int, p1: Int) = apply(p0, p1)
}

data class Problem(val operation: Operation, val operand1: Int, val operand2: Int, val result: Int)

class MathQuiz {
    companion object {
        fun newProblemSet(numberOfProblems: Int, operation: Operation, operandLimit: Int): List<Problem> {
            val problemSet = List<Problem>(numberOfProblems) {
                val operand1 = Random.nextInt(0, operandLimit + 1)
                val operand2 = when (operation) {
                    // TODO: support negatives in settings
                    Operation.Subtraction -> Random.nextInt(0, operand1 + 1)
                    else -> Random.nextInt(0, operandLimit + 1)
                }

                Problem(
                    operation,
                    operand1,
                    operand2,
                    operation.apply(operand1, operand2)
                )
            }

            return problemSet
        }

    }
}