package org.example.project.screens.contenthub

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.bolinho
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// [DATA CORRECTION] - A estrutura de dados foi corrigida para usar uma `List` em vez de `Map`.
// Isso garante a ordem dos elementos e previne que itens sejam perdidos, como aconteceu na versão anterior.
private val bentoItems = listOf(
    ContentHubItem("Fases da Vida", "life_stages", BentoGridSpan(2, 2), "/content/life-stages"),
    ContentHubItem("Rotulagem Nutricional", "labeling", BentoGridSpan(1, 1), "/content/labeling"),
    ContentHubItem("Origem Alimentar", "food_source", BentoGridSpan(1, 1), "/content/food-source"),
    ContentHubItem("Higiene de Alimentos", "hygiene", BentoGridSpan(2, 1), "/content/hygiene")
)
private data class FundamentalGuideItem(val title: String)
private val fundamentalGuides = listOf(
    FundamentalGuideItem("O que é a Janela Imunológica?"),
    FundamentalGuideItem("Açúcar antes dos 2 Anos"),
    FundamentalGuideItem("O Papel do Ferro no Desenvolvimento")
)

/**
 * Tela principal que exibe o hub de conteúdos.
 * A arquitetura utiliza uma LazyColumn para compor o layout estático do Bento Grid
 * com as seções de conteúdo dinâmico abaixo, de forma performática.
 *
 * [REFACTOR ARQUITETURAL] - O modificador `background` foi ajustado para usar
 * a mesma cor da HomeScreen (Color(0xFFe6dfca)) para garantir consistência visual imediata.
 * Idealmente, esta cor deveria ser movida para o MaterialTheme.
 */
@Composable
fun ContentHubScreen(onTitleChange: (String?) -> Unit) {
    LaunchedEffect(Unit) {
        onTitleChange("Conteúdo")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            // [THEME ALIGNMENT] - Utiliza a cor de fundo da HomeScreen para consistência.
            .background(Color(0xFFe6dfca))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            BentoGridStaticLayout()
        }
        item {
            FundamentalGuideSection(guides = fundamentalGuides)
        }
    }
}

/**
 * Implementa o layout do Bento Grid com alinhamento vertical determinístico.
 * A estrutura utiliza IntrinsicSize para garantir que a altura da coluna da direita
 * corresponda exatamente à altura do card maior à esquerda, resolvendo desalinhamentos.
 * Os pesos (`weight`) são usados para distribuir o espaço de forma previsível e resiliente.
 */
@Composable
private fun BentoGridStaticLayout() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Linha Superior: [Card 2x2 Esquerda] | [Coluna com 2 Cards 1x1 Direita]
        Row(
            modifier = Modifier.height(IntrinsicSize.Min), // Garante uma altura de referência consistente.
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Grande (2x2) - Define a altura intrínseca da Row.
            bentoItems[0].let { item ->
                BentoCard(
                    item = item,
                    modifier = Modifier.weight(0.66f).aspectRatio(1f)
                )
            }

            // Coluna com 2 Cards menores (1x1) - Preenche a altura da Row.
            Column(
                modifier = Modifier
                    .weight(0.34f)
                    .fillMaxHeight(), // Expande para a altura da Row.
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                bentoItems[1].let { item ->
                    BentoCard(
                        item = item,
                        // Ocupa 50% da altura disponível na coluna.
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
                bentoItems[2].let { item ->
                    BentoCard(
                        item = item,
                        // Ocupa os 50% restantes da altura.
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }
        }

        // Linha Inferior: Card Largo (2x1) - Permanece inalterado.
        bentoItems[3].let { item ->
            BentoCard(
                item = item,
                modifier = Modifier.fillMaxWidth().aspectRatio(2f)
            )
        }
    }
}


/**
 * Renderiza um card para o layout Bento. O modificador é passado como parâmetro
 * para permitir que o chamador defina seu tamanho e peso no layout.
 */
@Composable
private fun BentoCard(item: ContentHubItem, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(24.dp)).clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Renderiza a seção horizontal rolável "Guia Fundamental".
 * Este componente permanece inalterado, demonstrando a componentização eficaz.
 */
@Composable
private fun FundamentalGuideSection(guides: List<FundamentalGuideItem>) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Guia Fundamental",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(guides) { guide ->
                GuideCard(guide = guide)
            }
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun GuideCard(guide: FundamentalGuideItem) {
    Card(
        modifier = Modifier
            .width(250.dp) // Largura fixa para os cards na LazyRow
            .height(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painter = painterResource(Res.drawable.bolinho),
                contentDescription = guide.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = guide.title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 2
            )
        }
    }
}
