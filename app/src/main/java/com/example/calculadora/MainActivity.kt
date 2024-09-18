package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentNumber: String = ""
    private var operator: String? = null
    private var firstNumber: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        // Set up listeners for each button
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot, R.id.buttonAdd,
            R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonEqual, R.id.buttonClear
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        when (val text = button.text.toString()) {
            in "0".."9", "." -> onNumberClick(text)
            in "+", "-", "*", "/" -> onOperatorClick(text)
            "=" -> onEqualClick()
            "C" -> onClearClick()
        }
    }

    private fun onNumberClick(number: String) {
        currentNumber += number
        updateDisplay(currentNumber)
    }

    private fun onOperatorClick(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toDouble()
            currentNumber = ""
            operator = op
            updateDisplay("")
        }
    }

    private fun onEqualClick() {
        if (currentNumber.isNotEmpty() && firstNumber != null && operator != null) {
            val secondNumber = currentNumber.toDouble()
            val result = when (operator) {
                "+" -> firstNumber!! + secondNumber
                "-" -> firstNumber!! - secondNumber
                "*" -> firstNumber!! * secondNumber
                "/" -> firstNumber!! / secondNumber
                else -> 0.0
            }
            updateDisplay(result.toString())
            currentNumber = result.toString()
            operator = null
            firstNumber = null
        }
    }

    private fun onClearClick() {
        currentNumber = ""
        operator = null
        firstNumber = null
        updateDisplay("0")
    }

    private fun updateDisplay(text: String) {
        display.text = text
    }
}
