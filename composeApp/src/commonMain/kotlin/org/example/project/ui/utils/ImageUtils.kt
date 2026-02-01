package org.example.project.ui.utils

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.acao_adulto
import alimentandofasesapp.composeapp.generated.resources.acao_crianca
import alimentandofasesapp.composeapp.generated.resources.acao_idoso
import alimentandofasesapp.composeapp.generated.resources.comunidade
import alimentandofasesapp.composeapp.generated.resources.ebook_infantil
import alimentandofasesapp.composeapp.generated.resources.ebook_lanches
import alimentandofasesapp.composeapp.generated.resources.ebook_terceira_idade
import alimentandofasesapp.composeapp.generated.resources.fasesdavida
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun getDrawableResource(name: String): Painter {
    return when (name) {
        "fases_da_vida.png" -> painterResource(Res.drawable.fasesdavida)
        "acao_idoso.png" -> painterResource(Res.drawable.acao_idoso)
        "acao_adulto.png" -> painterResource(Res.drawable.acao_adulto)
        "comunidade.png" -> painterResource(Res.drawable.comunidade)
        "acao_crianca.png" -> painterResource(Res.drawable.acao_crianca)
        "ebook_lanches.png" -> painterResource(Res.drawable.ebook_lanches)
        "ebook_terceira_idade.png" -> painterResource(Res.drawable.ebook_terceira_idade)
        "ebook_infantil.png" -> painterResource(Res.drawable.ebook_infantil)
        else -> painterResource(Res.drawable.fasesdavida) // Fallback
    }
}
