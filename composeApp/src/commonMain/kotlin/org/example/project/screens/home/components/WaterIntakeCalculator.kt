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
import org.example.project.domain.model.WaterIntakeResult
import org.example.project.domain.usecase.CalculateDailyWaterIntakeUseCase
import org.example.project.rememberNotifier

/**
 * State holder para o [WaterIntakeCalculator].
 * Armazena o input do usuário e o resultado da operação de negócio (`Result`),
 * permitindo que a UI reaja de forma declarativa a sucessos ou falhas.
 * Aderindo ao Princípio da Responsabilidade Única, este objeto contém apenas
 * o estado, não a sua representação visual.
 */
private data class WaterIntakeState(
    val weight: String = "",
    val age: String = "",
    val calculationResult: Result<WaterIntakeResult>? = null
)

/**
 * Um componente de UI que permite aos usuários calcular a ingestão diária de água recomendada.
 *
 * Este Composable segue os princípios da Clean Architecture:
 * 1. Delega a lógica de negócio para [CalculateDailyWaterIntakeUseCase].
 * 2. Utiliza o padrão `expect/actual` através de [rememberNotifier] para desacoplar
 *    as chamadas de notificação da plataforma.
 * 3. A UI é uma função pura do [WaterIntakeState], tratando a renderização de
 *    sucesso e erro de forma declarativa.
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
                calculationResult = Result.failure(
                    IllegalArgumentException("Por favor, insira valores válidos para peso e idade.")
                )
            )
            return
        }

        val params = WaterIntakeParams(weight = weightValue, age = ageValue)

        // O estado é atualizado com o resultado direto do use case,
        // desacoplando a lógica de negócio da sua apresentação.
        state = state.copy(calculationResult = calculateUseCase(params))
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

            Spacer(Modifier.height(16.dp))
            Button(onClick = ::handleCalculation, modifier = Modifier.fillMaxWidth()) {
                Text("CALCULAR")
            }

            // [ARQUITETURA DE UI] A renderização do resultado é declarativa.
            // O Composable `fold` reage ao estado de sucesso ou falha,
            // aplicando a apresentação correta sem lógica imperativa.
            state.calculationResult?.let { result ->
                val message = result.fold(
                    onSuccess = { "Sua necessidade diária é de ${it.amountInMl} ml de água." },
                    onFailure = { it.message ?: "Ocorreu um erro desconhecido." }
                )
                val color = if (result.isSuccess) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                val style = if (result.isSuccess) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodySmall

                Spacer(Modifier.height(16.dp))
                Text(
                    text = message,
                    color = color,
                    style = style
                )
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
