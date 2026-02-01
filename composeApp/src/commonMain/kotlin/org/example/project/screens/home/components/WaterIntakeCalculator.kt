package org.example.project.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import org.example.project.services.rememberNotifier

/**
 * State holder para o [WaterIntakeCalculator] para consolidar o estado da UI.
 * A utilização de um state holder melhora a legibilidade e a manutenibilidade.
 */
private data class WaterIntakeState(
    val weight: String = "",
    val age: String = "",
    val resultText: String = "",
    val errorText: String? = null
)

/**
 * Um componente de UI que permite aos usuários calcular a ingestão diária de água recomendada.
 *
 * Este Composable segue os princípios da Clean Architecture:
 * 1. Delega a lógica de negócio para [CalculateDailyWaterIntakeUseCase].
 * 2. Utiliza o padrão `expect/actual` através de [rememberNotifier] para desacoplar
 *    as chamadas de notificação da plataforma.
 *
 * @param modifier O modificador a ser aplicado ao componente.
 */
@Composable
fun WaterIntakeCalculator(modifier: Modifier = Modifier) {
    val calculateUseCase = remember { CalculateDailyWaterIntakeUseCase() }
    val notifier = rememberNotifier()

    var state by remember { mutableStateOf(WaterIntakeState()) }

    fun handleCalculation() {
        val weightValue = state.weight.toIntOrNull()
        val ageValue = state.age.toIntOrNull()

        // Princípio de Early Return para validação de entrada.
        if (weightValue == null || ageValue == null) {
            state = state.copy(
                errorText = "Por favor, insira valores válidos para peso e idade.",
                resultText = ""
            )
            return
        }

        val params = WaterIntakeParams(weight = weightValue, age = ageValue)

        calculateUseCase(params)
            .onSuccess { result ->
                state = state.copy(
                    resultText = "Sua necessidade diária é de ${result.amountInMl} ml de água.",
                    errorText = null
                )
            }
            .onFailure { exception ->
                state = state.copy(
                    errorText = exception.message ?: "Ocorreu um erro desconhecido.",
                    resultText = ""
                )
            }
    }

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Calcule sua Necessidade de Água", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.weight,
                onValueChange = { state = state.copy(weight = it.filter(Char::isDigit)) },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = state.age,
                onValueChange = { state = state.copy(age = it.filter(Char::isDigit)) },
                label = { Text("Idade (anos)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            state.errorText?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = ::handleCalculation, modifier = Modifier.fillMaxWidth()) {
                Text("CALCULAR")
            }

            if (state.resultText.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                Text(state.resultText, style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                notifier.showNotification("Olá!", "Esta é uma notificação de teste.")
            }, modifier = Modifier.fillMaxWidth()) {
                Text("TESTAR NOTIFICAÇÃO")
            }
        }
    }
}
