package org.example.project.screens.recipes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * [SearchAndFilter] implementa a barra de busca e o botão de filtro.
 * O design utiliza um estilo transparente com bordas para se integrar
 * sutilmente ao layout.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun SearchAndFilter() {
    // [DIRETRIZ DE UI] A cor da borda foi alterada para preto (Color.Black)
    // para aumentar o contraste e a definição dos elementos de busca e filtro,
    // conforme a última solicitação de design.
    val borderColor = Color.Black
    val iconColor = Color.Gray
    val shape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .border(width = 1.dp, color = borderColor, shape = shape)
                .clip(shape)
                .clickable { /* TODO: Navegar para a tela de busca */ }
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Ícone de Busca", tint = iconColor)
            Spacer(modifier = Modifier.padding(4.dp))
            Text("Procurar receitas...", color = iconColor)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Ícone de Dropdown", tint = iconColor)
        }
        IconButton(
            onClick = { /* TODO: Abrir modal/tela de filtro */ },
            modifier = Modifier
                .fillMaxHeight()
                .border(width = 1.dp, color = borderColor, shape = shape)
                .clip(shape)
        ) {
            // [NOTA TÉCNICA] Usando um ícone padrão do Material como placeholder.
            // Substitua por `ImageVector.vectorResource(Res.drawable.ic_filter_alt)`
            // assim que o recurso vetorial for adicionado ao projeto.
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Botão de Filtro",
                tint = Color.Black
            )
        }
    }
}
