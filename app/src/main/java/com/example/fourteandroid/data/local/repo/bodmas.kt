import android.util.Log
import java.util.Stack

/*
import java.util.Stack

fun isOperator(c: String): Boolean {
    return when (c) {
        "+", "-", "*", "/", -> true
        else -> false
    }
}

// Function to return precedence of operators
fun precedence(op: String): Int {
    return when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        else -> 0
    }
}

// Function to perform arithmetic operations
fun applyOp(a: Int, b: Int, op: String): Int {
    return when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> throw UnsupportedOperationException("Unknown operator: $op")
    }
}

// Function to convert infix expression to postfix
fun infixToPostfix(tokens: List<String>): List<String>? {
    if (!validateExpression(tokens)) return null

    val concatenatedTokens = concatenateNumbers(tokens)

    val stack = Stack<String>()
    val result = mutableListOf<String>()

    for (token in concatenatedTokens) {
        when {
            token == "(" -> stack.push(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.peek() != "(") {
                    result.add(stack.pop())
                }
                stack.pop() // Remove '(' from stack
            }
            isOperator(token) -> {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    result.add(stack.pop())
                }
                stack.push(token)
            }
            else -> result.add(token) // Operand
        }
    }

    while (stack.isNotEmpty()) {
        result.add(stack.pop())
    }

    return result
}

// Function to evaluate postfix expression
fun evaluatePostfix(tokens: List<String>): Int? {
    if (tokens == null) return null

    val stack = Stack<Int>()

    for (token in tokens) {
        when {
            isOperator(token) -> {
                if (stack.size < 2) return null // Not enough operands
                val b = stack.pop()
                val a = stack.pop()
                stack.push(applyOp(a, b, token))
            }
            else -> {
                try {
                    stack.push(token.toInt())
                } catch (e: NumberFormatException) {
                    return null // Invalid number format
                }
            }
        }
    }

    return if (stack.size == 1) stack.pop() else null
}

// Function to validate the expression list
fun validateExpression(tokens: List<String>): Boolean {
    var lastWasOperator = true // Start as true to handle leading operators
    var balance = 0 // To check balanced parentheses

    for (token in tokens) {
        when {
            token == "(" -> balance++
            token == ")" -> balance--
            isOperator(token) -> {
                if (lastWasOperator) return false // Two operators in a row
                lastWasOperator = true
            }
            else -> {
                lastWasOperator = false
            }
        }

        // More closing parentheses than opening at any point
        if (balance < 0) return false
    }

    // Unbalanced parentheses or ends with an operator
    return balance == 0 && !lastWasOperator
}

// Function to concatenate consecutive numbers
fun concatenateNumbers(tokens: List<String>): List<String> {
    val result = mutableListOf<String>()
    var i = 0
    while (i < tokens.size) {
        if (!isOperator(tokens[i]) && tokens[i] != "(" && tokens[i] != ")") {
            var number = tokens[i]
            while (i + 1 < tokens.size && !isOperator(tokens[i + 1]) && tokens[i + 1] != "(" && tokens[i + 1] != ")") {
                number += tokens[++i]
            }
            result.add(number)
        } else {
            result.add(tokens[i])
        }
        i++
    }
    return result
}

// Main function to solve the expression
fun main() {
    val expression = listOf("+")
    val postfix = infixToPostfix(expression)
    if (postfix == null) {
        println("Invalid expression")
    } else {
        println("Postfix: $postfix") // Debugging line to show the postfix expression
        val result = evaluatePostfix(postfix)
        if (result == null) {
            println("Invalid expression")
        } else {
            println("Result: $result")
        }
    }
}
*/

fun isOperator(c: String): Boolean {
    return when (c) {
        "+", "-", "*", "/", -> true
        else -> false
    }
}

// Function to return precedence of operators
fun precedence(op: String): Int {
    return when (op) {
        "+", "-" -> 1
        "*", "/" -> 2
        else -> 0
    }
}

// Function to perform arithmetic operations
fun applyOp(a: Int, b: Int, op: String): Int {
    return when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> throw UnsupportedOperationException("Unknown operator: $op")
    }
}

// Function to convert infix expression to postfix
fun infixToPostfix(tokens: List<String>): List<String>? {
    if (!validateExpression(tokens)) return null

    val concatenatedTokens = concatenateNumbers(tokens)

    val stack = Stack<String>()
    val result = mutableListOf<String>()

    for (token in concatenatedTokens) {
        when {
            token == "(" -> stack.push(token)
            token == ")" -> {
                while (stack.isNotEmpty() && stack.peek() != "(") {
                    result.add(stack.pop())
                }
                stack.pop() // Remove '(' from stack
            }
            isOperator(token) -> {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(token)) {
                    result.add(stack.pop())
                }
                stack.push(token)
            }
            else -> result.add(token) // Operand
        }
    }

    while (stack.isNotEmpty()) {
        result.add(stack.pop())
    }

    return result
}

// Function to evaluate postfix expression
fun evaluatePostfix(tokens: List<String>): Int? {
    if (tokens == null) return null

    val stack = Stack<Int>()

    for (token in tokens) {
        when {
            isOperator(token) -> {
                if (stack.size < 2) return null // Not enough operands
                val b = stack.pop()
                val a = stack.pop()
                Log.i("step","Apply operator $token on $a and $b")
                stack.push(applyOp(a, b, token))
            }
            else -> {
                try {
//                    println("Push number $token onto stack")
                    stack.push(token.toInt())
                } catch (e: NumberFormatException) {
                    return null // Invalid number format
                }
            }
        }
    }
    Log.i("step","computer ends____")
    return if (stack.size == 1) stack.pop() else null
}

// Function to validate the expression list
fun validateExpression(tokens: List<String>): Boolean {
    var lastWasOperator = true // Start as true to handle leading operators
    var balance = 0 // To check balanced parentheses

    for (token in tokens) {
        when {
            token == "(" -> balance++
            token == ")" -> balance--
            isOperator(token) -> {
                if (lastWasOperator) return false // Two operators in a row
                lastWasOperator = true
            }
            else -> {
                lastWasOperator = false
            }
        }

        // More closing parentheses than opening at any point
        if (balance < 0) return false
    }

    // Unbalanced parentheses or ends with an operator
    return balance == 0 && !lastWasOperator
}

// Function to concatenate consecutive numbers
fun concatenateNumbers(tokens: List<String>): List<String> {
    val result = mutableListOf<String>()
    var i = 0
    while (i < tokens.size) {
        if (!isOperator(tokens[i]) && tokens[i] != "(" && tokens[i] != ")") {
            var number = tokens[i]
            while (i + 1 < tokens.size && !isOperator(tokens[i + 1]) && tokens[i + 1] != "(" && tokens[i + 1] != ")") {
                number += tokens[++i]
            }
            result.add(number)
        } else {
            result.add(tokens[i])
        }
        i++
    }
    return result
}

// Main function to solve the expression
fun main() {
    val expression = listOf("5", "6", "+", "7")
    println("Original Expression: $expression")
    val postfix = infixToPostfix(expression)
    if (postfix == null) {
        println("Invalid expression")
    } else {
        println("Postfix: $postfix") // Debugging line to show the postfix expression
        val result = evaluatePostfix(postfix)
        if (result == null) {
            println("Invalid expression")
        } else {
            println("Result: $result")
        }
    }
}
