package com.dnguy38.mathquiz.models

enum class Configuration(val operation: Operation, val operandLimit: Int) {
    Addition5(Operation.Addition, 5),
    Addition10(Operation.Addition, 10),
    Addition50(Operation.Addition, 50),
    Addition100(Operation.Addition, 100),
    Subtraction5(Operation.Subtraction, 5),
    Subtraction10(Operation.Subtraction, 10),
    Subtraction50(Operation.Subtraction, 50),
    Subtraction100(Operation.Subtraction, 100),
    Multiplication2(Operation.Addition, 2),
    Multiplication3(Operation.Addition, 3),
    Multiplication4(Operation.Addition, 4),
    Multiplication5(Operation.Addition, 5),
    Multiplication6(Operation.Addition, 6),
    Multiplication7(Operation.Addition, 7),
    Multiplication8(Operation.Addition, 8),
    Multiplication9(Operation.Addition, 9),
}