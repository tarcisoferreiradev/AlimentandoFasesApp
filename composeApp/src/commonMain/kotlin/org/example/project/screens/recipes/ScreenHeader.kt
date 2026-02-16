package org.example.project.screens.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * [ScreenHeader] exibe a principal proposta de valor da tela, servindo
 * como um gancho inspiracional para o usuário.
 *
 * @see org.example.project.screens.RecipesScreen Onde este componente é utilizado.
 */
@Composable
fun ScreenHeader() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Descubra Sabores, Nutra a Vida.",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            // [CORREÇÃO DE UI] A cor do texto é definida explicitamente como preta
            // para garantir alto contraste e legibilidade contra o fundo claro da tela (0xFFe6dfca),
            // resolvendo o problema de invisibilidade.
            color = Color.Black
        )
    }
}
