package org.example.project.screens.recipes

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.nutricaobanner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * [MainBanner] é um componente dedicado a exibir o banner visual principal
 * da tela de receitas.
 *
 * @see org.example.project.screens.RecipesScreen Onde este componente é utilizado.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainBanner() {
    Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Image(
            painter = painterResource(Res.drawable.nutricaobanner),
            contentDescription = "Banner Principal de Nutrição",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                // [DIRETRIZ DE UI] Adiciona cantos arredondados para suavizar a integração
                // visual do banner com os demais componentes da tela, que seguem
                // um padrão de design com bordas suaves.
                .clip(RoundedCornerShape(12.dp))
        )
    }
}
