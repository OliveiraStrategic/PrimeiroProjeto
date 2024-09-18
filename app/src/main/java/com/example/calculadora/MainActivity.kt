package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // TextView para exibir os números e resultados na calculadora
    private lateinit var display: TextView

    // Variável para armazenar o número atual digitado pelo usuário
    private var currentNumber: String = ""

    // Variável para armazenar o operador selecionado (+, -, *, /)
    private var operator: String? = null

    // Variável para armazenar o primeiro número inserido antes do operador
    private var firstNumber: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ligação do TextView do layout para exibir o número/resultado
        display = findViewById(R.id.tvDisplay)

        // IDs de todos os botões da calculadora para configurar seus listeners
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot, R.id.buttonAdd,
            R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonEqual, R.id.buttonClear
        )

        // Configuração de listeners para cada botão, chamando 'onButtonClick' quando clicado
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    // Função chamada quando qualquer botão é clicado
    private fun onButtonClick(button: Button) {
        // Identifica o texto do botão clicado
        when (val text = button.text.toString()) {
            // Se for um número ou ponto decimal, chama a função 'onNumberClick'
            in "0".."9", "." -> onNumberClick(text)

            // Se for um operador, chama a função 'onOperatorClick'
            in "+", "-", "*", "/" -> onOperatorClick(text)

            // Se for o botão igual, executa a operação com 'onEqualClick'
            "=" -> onEqualClick()

            // Se for o botão de limpar, chama 'onClearClick'
            "C" -> onClearClick()
        }
    }

    // Função para lidar com cliques nos botões numéricos e de ponto decimal
    private fun onNumberClick(number: String) {
        // Adiciona o número ou ponto ao número atual
        currentNumber += number
        // Atualiza a exibição com o número atual
        updateDisplay(currentNumber)
    }

    // Função para lidar com cliques nos botões de operadores (+, -, *, /)
    private fun onOperatorClick(op: String) {
        // Verifica se há um número atual
        if (currentNumber.isNotEmpty()) {
            // Armazena o número atual como 'firstNumber' e limpa 'currentNumber'
            firstNumber = currentNumber.toDouble()
            currentNumber = ""
            // Define o operador selecionado
            operator = op
            // Limpa a exibição para indicar que o operador foi selecionado
            updateDisplay("")
        }
    }

    // Função para executar a operação quando o botão "=" é pressionado
    private fun onEqualClick() {
        // Verifica se todas as variáveis necessárias estão definidas
        if (currentNumber.isNotEmpty() && firstNumber != null && operator != null) {
            // Converte o número atual em um Double para a operação
            val secondNumber = currentNumber.toDouble()
            // Executa a operação com base no operador
            val result = when (operator) {
                "+" -> firstNumber!! + secondNumber
                "-" -> firstNumber!! - secondNumber
                "*" -> firstNumber!! * secondNumber
                "/" -> firstNumber!! / secondNumber
                else -> 0.0
            }
            // Atualiza a exibição com o resultado
            updateDisplay(result.toString())
            // Armazena o resultado para futuras operações
            currentNumber = result.toString()
            // Reseta o operador e o primeiro número
            operator = null
            firstNumber = null
        }
    }

    // Função para limpar a calculadora quando o botão "C" é pressionado
    private fun onClearClick() {
        // Reseta as variáveis
        currentNumber = ""
        operator = null
        firstNumber = null
        // Atualiza a exibição para "0"
        updateDisplay("0")
    }

    // Função auxiliar para atualizar o display da calculadora
    private fun updateDisplay(text: String) {
        display.text = text
    }
}
