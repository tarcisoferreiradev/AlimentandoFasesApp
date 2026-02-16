package org.example.project.screens.recipes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * [CategoryTabs] exibe uma lista horizontal de categorias de receitas como filtros selecionáveis.
 * O estilo visual se adapta com base no estado de seleção para dar ênfase à categoria ativa,
 * conforme o design de referência.
 *
 * @see org.example.project.screens.RecipesScreen Onde este componente é utilizado.
 */
@Composable
fun CategoryTabs() {
    val categories = listOf("Vegano", "Sem Glúten", "Aproveitamento Integral", "Sem Lactose")
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    // [DIRETRIZ DE UI] A cor primária é definida como uma variável local para fácil manutenção
    // e para garantir consistência. O ideal é que venha do `MaterialTheme`.
    val primaryColor = Color(0xFF2d644c)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory

            // [REFINAMENTO ARQUITETURAL] Utiliza-se `OutlinedButton` para todos os estados,
            // controlando o preenchimento e a cor da borda dinamicamente. Esta é a abordagem
            // canônica do Material 3 para botões com estilos variantes de borda/preenchimento.
            OutlinedButton(
                onClick = { selectedCategory = category },
                shape = RoundedCornerShape(50), // Formato de pílula para consistência.
                // [DIRETRIZ DE UI] A borda só é visível (com cor primária) quando o botão
                // *não* está selecionado. Quando selecionado, a borda é nula, pois
                // o fundo sólido já fornece o contorno visual.
                border = if (isSelected) null else BorderStroke(1.dp, primaryColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    // [DIRETRIZ DE UI] O container (fundo) só tem cor quando o botão está selecionado.
                    // Caso contrário, é transparente para revelar o fundo da tela.
                    containerColor = if (isSelected) primaryColor else Color.Transparent,
                    // [DIRETRIZ DE UI] A cor do conteúdo (texto) é branca no estado selecionado
                    // para contrastar com o fundo verde, e verde nos outros casos.
                    contentColor = if (isSelected) Color.White else primaryColor
                )
            ) {
                Text(category)
            }
        }
    }
}
