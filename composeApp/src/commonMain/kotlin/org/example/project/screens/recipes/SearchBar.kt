package org.example.project.screens.recipes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.AppDefaults

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppDefaults.BegeNavegacao, RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Ícone de pesquisa",
            tint = Color.Gray
        )
        Text(
            text = "Search",
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        IconButton(onClick = { /* TODO: Ação do filtro */ }) {
            // [CORREÇÃO TÉCNICA] A referência ao recurso "ic_filter" foi comentada
            // para evitar falha no build, pois o drawable ainda não existe.
            // Adicione o ícone a 'composeResources/drawable' e descomente a linha abaixo.
            /*
             Icon(
                 painter = painterResource(Res.drawable.ic_filter),
                 contentDescription = "Filtro",
                 tint = Color.Black
             )
            */
        }
    }
}
