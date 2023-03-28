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
        fun newProblemSet(numberOfProblems: Int, operation: Operation, operandLimit: Int, allowZero: Boolean, allowNegatives: Boolean): List<Problem> {
            val problemSet = List<Problem>(numberOfProblems) {
                val lowerBound = when (allowZero) {
                    true -> 0
                    false -> 1
                }
                val operandUntil = operandLimit + 1

                var operand1 = when (operation) {
                    Operation.Multiplication -> operandLimit
                    else -> Random.nextInt(lowerBound, operandUntil)
                }
                var operand2 = when (operation) {
                    Operation.Subtraction -> when (allowNegatives) {
                        true -> Random.nextInt(lowerBound, operandUntil)
                        false -> Random.nextInt(lowerBound, operand1 + 1)
                    }
                    Operation.Multiplication -> Random.nextInt(1, 10)
                    else -> Random.nextInt(lowerBound, operandUntil)
                }

                if (operation == Operation.Multiplication) {
                    if (allowNegatives && Random.nextBoolean()) {
                        operand2 *= -1
                    }

                    if (Random.nextBoolean()) {
                        operand1 = operand2.also { operand2 = operand1 }
                    }
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