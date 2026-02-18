package org.example.project.screens.contenthub

import androidx.compose.ui.unit.IntSize

/**
 * Define as dimensões de um card no Bento Grid.
 * Representa a quantidade de células que o card ocupa na largura e altura.
 */
data class BentoGridSpan(val columnSpan: Int, val rowSpan: Int) {
    fun toSpannedIntSize() = IntSize(columnSpan, rowSpan)
}

/**
 * Representa um item de conteúdo a ser exibido no Bento Grid.
 *
 * @property title O título exibido no card.
 * @property category A categoria do conteúdo, usada para identificação.
 * @property span As dimensões do card no grid.
 * @property route O destino de navegação ao clicar no card.
 */
data class ContentHubItem(
    val title: String,
    val category: String,
    val span: BentoGridSpan,
    val route: String // Rota para navegação futura
)
