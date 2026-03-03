package org.example.project.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Representa o estado imutável da UI seguindo o padrão de SoC (Separation of Concerns).
 */
sealed interface UIState<out T> {
    data object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val message: String, val exception: Throwable? = null) : UIState<Nothing>
}

/**
 * [CommonErrorBoundary] implementa um mecanismo de resiliência na UI.
 * Utiliza um padrão de Wrapper para capturar falhas lógicas e exibir um Fallback seguro,
 * impedindo que erros em componentes isolados causem o crash da aplicação completa.
 *
 * @param state O estado atual da UI (Success, Error ou Loading).
 * @param onRetry Ação de recuperação a ser executada em caso de erro.
 * @param content O conteúdo a ser renderizado em caso de sucesso.
 */
@Composable
fun <T> CommonErrorBoundary(
    state: UIState<T>,
    onRetry: () -> Unit,
    content: @Composable (T) -> Unit
) {
    when (state) {
        is UIState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UIState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ops! Algo deu errado.",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = onRetry) {
                    Text("Tentar Novamente")
                }
            }
        }
        is UIState.Success -> content(state.data)
    }
}
