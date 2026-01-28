package org.example.project.domain.usecase

import org.example.project.domain.model.WaterIntakeParams
import org.example.project.domain.model.WaterIntakeResult

/**
 * Encapsula a regra de negócio para calcular a ingestão diária de água.
 * Sendo uma lógica pura e sem estado, uma classe não é estritamente necessária,
 * mas pode ser útil para injeção de dependência em ViewModels.
 * Vamos usar uma classe para melhor escalabilidade.
 */
class CalculateDailyWaterIntakeUseCase {

    /**
     * Fatores de cálculo baseados em ml/kg por faixa etária.
     * Estes valores devem ser validados pela equipe de produto/nutrição.
     * Fonte de referência: [ÍNDICE DA PESQUISA]
     */
    private object WaterIntakeFactors {
        const val YOUTH_FACTOR = 40 // ml/kg para idade <= 17
        const val ADULT_FACTOR = 35 // ml/kg para idade 18-55
        const val SENIOR_FACTOR = 30 // ml/kg para idade > 55
    }

    /**
     * Executa o cálculo da ingestão de água.
     * @param params Contém o peso (kg) e a idade (anos) do usuário.
     * @return Um [Result] que encapsula o sucesso ([WaterIntakeResult]) ou uma falha ([IllegalArgumentException]).
     */
    operator fun invoke(params: WaterIntakeParams): Result<WaterIntakeResult> {
        if (params.weight <= 0 || params.age <= 0) {
            return Result.failure(IllegalArgumentException("Peso e idade devem ser valores positivos."))
        }

        val intakeMl = when {
            params.age <= 17 -> params.weight * WaterIntakeFactors.YOUTH_FACTOR
            params.age <= 55 -> params.weight * WaterIntakeFactors.ADULT_FACTOR
            else -> params.weight * WaterIntakeFactors.SENIOR_FACTOR
        }

        return Result.success(WaterIntakeResult(intakeMl.toInt()))
    }
}