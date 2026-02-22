package org.example.project.screens.contenthub

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.conteudofasesdavida
import alimentandofasesapp.composeapp.generated.resources.conteudohigiene
import alimentandofasesapp.composeapp.generated.resources.conteudoorigemalimentar
import alimentandofasesapp.composeapp.generated.resources.conteudorotulagem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private data class BentoItem(
    val title: String,
    val subtitle: String? = null,
    val imageRes: DrawableResource? = null
)

@OptIn(ExperimentalResourceApi::class)
private val bentoItems = listOf(
    BentoItem(title = "Fases da Vida", imageRes = Res.drawable.conteudofasesdavida),
    BentoItem(title = "Rotulagem\nNutricional", imageRes = Res.drawable.conteudorotulagem),
    BentoItem(title = "Origem\nAlimentar", imageRes = Res.drawable.conteudoorigemalimentar),
    BentoItem(title = "Higiene de\nAlimentos", imageRes = Res.drawable.conteudohigiene)
)

private data class FundamentalGuideItem(val title: String)

private val fundamentalGuides = listOf(
    FundamentalGuideItem("O que é a Janela Imunológica?"),
    FundamentalGuideItem("Açúcar antes dos 2 Anos"),
    FundamentalGuideItem("O Papel do Ferro no Desenvolvimento")
)

@Composable
fun ContentHubScreen(onTitleChange: (String?) -> Unit) {
    LaunchedEffect(Unit) {
        onTitleChange("Conteúdo")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFe6dfca))
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            BentoGridStaticLayout()
        }
        item {
            FundamentalGuideSection(guides = fundamentalGuides)
        }
    }
}

@Composable
private fun BentoGridStaticLayout() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BentoCard(
                item = bentoItems[0],
                modifier = Modifier.weight(0.66f).fillMaxHeight()
            )

            Column(
                modifier = Modifier.weight(0.34f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BentoCard(
                    item = bentoItems[1],
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                )
                BentoCard(
                    item = bentoItems[2],
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                )
            }
        }

        BentoCard(
            item = bentoItems[3],
            modifier = Modifier.fillMaxWidth().aspectRatio(2f)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BentoCard(
    item: BentoItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4C5238))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            item.imageRes?.let { resource ->
                Image(
                    painter = painterResource(resource),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

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

@Composable
private fun GuideCard(guide: FundamentalGuideItem) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(120.dp).background(Color.Gray))
            Text(
                text = guide.title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 2
            )
        }
    }
}
