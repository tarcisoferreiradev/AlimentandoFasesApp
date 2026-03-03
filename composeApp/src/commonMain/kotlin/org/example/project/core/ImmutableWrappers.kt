package org.example.project.core

import androidx.compose.runtime.Immutable

/**
 * [ImmutableListWrapper] é um encapsulador de tipos genérico que utiliza a anotação [@Immutable]
 * para informar ao compilador do Compose que o conteúdo desta lista não sofrerá mutações
 * após a sua criação.
 *
 * Decisão Arquitetural: O uso deste wrapper é mandatório para todas as coleções enviadas
 * do domínio/ViewModel para a camada de UI. Isso previne recomposições desnecessárias
 * (recomposição instável) durante o scroll de listas longas, garantindo que o Compose
 * ignore a verificação de igualdade profunda a cada frame, otimizando o consumo de CPU.
 *
 * @param items A lista imutável de itens do tipo [T].
 */
@Immutable
data class ImmutableListWrapper<T>(
    val items: List<T>
)

/**
 * Função de extensão utilitária para converter uma [List] padrão em um [ImmutableListWrapper].
 */
fun <T> List<T>.toImmutableList(): ImmutableListWrapper<T> = ImmutableListWrapper(this)
