package org.example.project.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.acao_adulto
import alimentandofasesapp.composeapp.generated.resources.acao_crianca
import alimentandofasesapp.composeapp.generated.resources.acao_idoso
import alimentandofasesapp.composeapp.generated.resources.comunidade
import alimentandofasesapp.composeapp.generated.resources.ebook_infantil
import alimentandofasesapp.composeapp.generated.resources.ebook_lanches
import alimentandofasesapp.composeapp.generated.resources.ebook_terceira_idade
import alimentandofasesapp.composeapp.generated.resources.fasesdavida
import alimentandofasesapp.composeapp.generated.resources.jogo
import alimentandofasesapp.composeapp.generated.resources.origemalimentar
import alimentandofasesapp.composeapp.generated.resources.receitas

enum class ImageResource {
    ACAO_CRIANCA,
    ACAO_ADULTO,
    ACAO_IDOSO,
    FASES_VIDA,
    ORIGEM_ALIMENTAR,
    RECEITAS,
    COMUNIDADE,
    JOGO,
    EBOOK_LANCHES,
    EBOOK_TERCEIRA_IDADE,
    EBOOK_INFANTIL;

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter {
        return when (this) {
            ACAO_CRIANCA -> painterResource(Res.drawable.acao_crianca)
            ACAO_ADULTO -> painterResource(Res.drawable.acao_adulto)
            ACAO_IDOSO -> painterResource(Res.drawable.acao_idoso)
            FASES_VIDA -> painterResource(Res.drawable.fasesdavida)
            ORIGEM_ALIMENTAR -> painterResource(Res.drawable.origemalimentar)
            RECEITAS -> painterResource(Res.drawable.receitas)
            COMUNIDADE -> painterResource(Res.drawable.comunidade)
            JOGO -> painterResource(Res.drawable.jogo)
            EBOOK_LANCHES -> painterResource(Res.drawable.ebook_lanches)
            EBOOK_TERCEIRA_IDADE -> painterResource(Res.drawable.ebook_terceira_idade)
            EBOOK_INFANTIL -> painterResource(Res.drawable.ebook_infantil)
        }
    }
}