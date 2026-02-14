package org.example.project.screens

import org.example.project.core.WaterIntakeResult

/**
 * Define o contrato de comunicação estrito entre a HomeScreen (UI) e o HomeViewModel.
 * Esta interface garante a imutabilidade do estado e a clareza dos eventos,
 * desacoplando a lógica de negócio da apresentação.
 */
interface HomeContract {

    /**
     * Representa o estado imutável e completo da HomeScreen.
     *
     * @property weight O peso atual do usuário em kg.
     * @property selectedAgeIndex O índice da faixa etária selecionada.
     * @property waterIntakeResult O resultado do cálculo da meta de hidratação, encapsulado em um [Result].
     */
    data class State(
        val weight: Int = 70,
        val selectedAgeIndex: Int = 1,
        val waterIntakeResult: Result<WaterIntakeResult>? = null
    )

    /**
     * Define os eventos que a UI pode disparar em direção ao ViewModel.
     * O uso de uma sealed interface garante que apenas eventos definidos aqui
     * possam ser processados, prevenindo ações inesperadas.
     */
    sealed interface Event {
        data class OnWeightChanged(val newWeight: Int) : Event
        data class OnAgeIndexChanged(val newIndex: Int) : Event
        object OnCalculateClicked : Event
    }
}