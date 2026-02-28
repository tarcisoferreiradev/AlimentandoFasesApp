package org.example.project.components

import androidx.compose.runtime.Composable

@Composable
actual fun BackButtonHandler(enabled: Boolean, onBack: () -> Unit) {
    // AINDA NÃO IMPLEMENTADO: O iOS não possui um botão de "voltar" universal como o Android.
    // A navegação de retorno é geralmente tratada por elementos da UI (ex: botões de "voltar" em
    // barras de navegação), que já invocam a lógica de `onBack` diretamente. Este handler
    // pode ser implementado no futuro, se necessário, para observar gestos de sistema ou
    // eventos de teclado.
}