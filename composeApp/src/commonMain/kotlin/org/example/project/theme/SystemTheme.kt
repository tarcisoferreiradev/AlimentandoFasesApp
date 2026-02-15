package org.example.project.theme

import androidx.compose.runtime.Composable

/**
 * [ARQUITETURA KMP] Declaração `expect` para o tema das barras de sistema.
 *
 * Define um contrato no código comum (`commonMain`) que deve ser implementado
 * por cada plataforma de destino (`androidMain`, `iosMain`, etc.), permitindo que
 * o código compartilhado invoque APIs específicas da plataforma de forma segura.
 */
@Composable
internal expect fun SystemTheme(isDarkIcons: Boolean)
