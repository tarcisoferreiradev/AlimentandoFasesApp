package org.example.project.ui.theme

import androidx.compose.runtime.Composable

/**
 * Declaração `expect` para um Composable que controla a aparência da barra de status do sistema.
 *
 * Esta abstração permite que cada tela individualmente dite se os ícones da barra de status
 * devem ser renderizados em modo claro ou escuro, garantindo o contraste adequado
 * contra o plano de fundo da tela.
 *
 * @param isLight Se `true`, os ícones da barra de status serão desenhados para um fundo claro (ou seja, ícones escuros).
 *                Se `false`, os ícones serão desenhados para um fundo escuro (ou seja, ícones claros).
 */
@Composable
expect fun SystemAppearance(isLight: Boolean)
