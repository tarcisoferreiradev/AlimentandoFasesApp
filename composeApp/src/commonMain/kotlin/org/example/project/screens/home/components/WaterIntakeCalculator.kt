package org.example.project.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.WaterIntakeParams
import org.example.project.domain.usecase.CalculateDailyWaterIntakeUseCase

@Composable
fun WaterIntakeCalculator(modifier: Modifier = Modifier) {
    // Injeção de dependência manual. Em um app maior, isso viria de um ViewModel/DI Framework.
    val calculateUseCase = remember { CalculateDailyWaterIntakeUseCase() }

    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    fun handleCalculation() {
        val weightValue = weight.toIntOrNull()
        val ageValue = age.toIntOrNull()

        if (weightValue == null || ageValue == null) {
            errorText = "Por favor, insira valores válidos para peso e idade."
            resultText = ""
            return // Early return
        }

        val params = WaterIntakeParams(weight = weightValue, age = ageValue)

        calculateUseCase(params)
            .onSuccess { result ->
                resultText = "Sua necessidade diária é de ${result.amountInMl} ml de água."
                errorText = null
            }
            .onFailure { exception ->
                errorText = exception.message ?: "Ocorreu um erro desconhecido."
                resultText = ""
            }
    }

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Calcule sua Necessidade de Água", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))

            // Inputs
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it.filter { char -> char.isDigit() } },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it.filter { char -> char.isDigit() } },
                label = { Text("Idade (anos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Tratamento de erro visível para o usuário
            errorText?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = ::handleCalculation, modifier = Modifier.fillMaxWidth()) {
                Text("CALCULAR")
            }

            // Resultado
            if (resultText.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                Text(resultText, style = MaterialTheme.typography.body1)
            }
        }
    }
}