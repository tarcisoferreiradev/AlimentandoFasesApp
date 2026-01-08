package org.example.project.screens

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.comunidade
import alimentandofasesapp.composeapp.generated.resources.ebook_infantil
import alimentandofasesapp.composeapp.generated.resources.ebook_lanches
import alimentandofasesapp.composeapp.generated.resources.ebook_terceira_idade
import alimentandofasesapp.composeapp.generated.resources.fases_da_vida
import alimentandofasesapp.composeapp.generated.resources.jogo
import alimentandofasesapp.composeapp.generated.resources.origem_alimentar
import alimentandofasesapp.composeapp.generated.resources.receitas
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// Cores extraídas do design do site para manter a consistência
private object HomeScreenDefaults {
    val BackgroundColor = Color(0xFF121412) // Novo fundo preto suave
}

private val carouselItems = listOf(
    "fases_da_vida.png",
    "origem_alimentar.png",
    "receitas.png",
    "comunidade.png",
    "jogo.png"
)

@Composable
expect fun isLandscape(): Boolean

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun getDrawableResource(name: String): Painter {
    return when (name) {
        "fases_da_vida.png" -> painterResource(Res.drawable.fases_da_vida)
        "origem_alimentar.png" -> painterResource(Res.drawable.origem_alimentar)
        "receitas.png" -> painterResource(Res.drawable.receitas)
        "comunidade.png" -> painterResource(Res.drawable.comunidade)
        "jogo.png" -> painterResource(Res.drawable.jogo)
        "ebook_lanches.png" -> painterResource(Res.drawable.ebook_lanches)
        "ebook_terceira_idade.png" -> painterResource(Res.drawable.ebook_terceira_idade)
        "ebook_infantil.png" -> painterResource(Res.drawable.ebook_infantil)
        else -> painterResource(Res.drawable.fases_da_vida) // Fallback
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val isLandscape = isLandscape()

    if (isLandscape) {
        LandscapeHomeScreen()
    } else {
        PortraitHomeScreen()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CarouselCard(imageRes: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = getDrawableResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Gradiente para garantir a legibilidade do texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 400f // Começa o gradiente mais para baixo
                    )
                )
        )
    }
}


@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
private fun PortraitHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeScreenDefaults.BackgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Seção Principal (Carrossel) ---
        CarouselSection()

        // --- Conteúdo abaixo do banner ---
        Column(
            modifier = Modifier
                .fillMaxWidth() // Ocupa a largura toda
                .padding(top = 60.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título, Spacer e LazyRow dos e-books (código existente)
            Text(
                text = "Nossos Materiais Exclusivos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE0E0E0) // Branco suave para texto de título
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Itens dos e-books (código existente)
                item {
                    EbookCard(
                        imageRes = "ebook_lanches.png",
                        contentDescription = "Capa do e-book Guia de Lanches Saudáveis",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
                item {
                    EbookCard(
                        imageRes = "ebook_terceira_idade.png",
                        contentDescription = "Capa do e-book Nutrição na Terceira Idade",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
                item {
                    EbookCard(
                        imageRes = "ebook_infantil.png",
                        contentDescription = "Capa do e-book Criança Bem Nutrida, Futuro Saudável",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
private fun LandscapeHomeScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeScreenDefaults.BackgroundColor)
    ) {
        // Lado Esquerdo: Banner
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            CarouselSection()
        }


        // Lado Direito: Materiais
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f) // Ocupa os outros 50%
                .verticalScroll(rememberScrollState()) // Permite scroll no lado direito
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centraliza verticalmente
        ) {
            Text(
                text = "Nossos Materiais Exclusivos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE0E0E0) // Branco suave para texto de título
            )
            Spacer(modifier = Modifier.height(16.dp))
            // No modo paisagem, LazyRow ainda é a melhor opção.
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Itens dos e-books (código existente)
                item {
                    EbookCard(
                        imageRes = "ebook_lanches.png",
                        contentDescription = "Capa do e-book Guia de Lanches Saudáveis",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
                item {
                    EbookCard(
                        imageRes = "ebook_terceira_idade.png",
                        contentDescription = "Capa do e-book Nutrição na Terceira Idade",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
                item {
                    EbookCard(
                        imageRes = "ebook_infantil.png",
                        contentDescription = "Capa do e-book Criança Bem Nutrida, Futuro Saudável",
                        onDownloadClick = { /* Lógica de download */ }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSection() {// 1. NÚMERO DE PÁGINAS INFINITO
    val pageCount = Int.MAX_VALUE
    // 2. AJUSTE NA PÁGINA INICIAL PARA COMEÇAR NO ÍNDICE 0
    // Calcula um ponto de partida que seja múltiplo do número de itens, garantindo que comece na primeira imagem.
    val startingPoint = pageCount / 2
    val initialPage = startingPoint - (startingPoint % carouselItems.size)
    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }

    val scope = rememberCoroutineScope()

    // Efeito de auto-scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            scope.launch {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f),
        contentAlignment = Alignment.Center
    ) {
        // O Carrossel de Imagens
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val itemIndex = page % carouselItems.size
            CarouselCard(imageRes = carouselItems[itemIndex])
        }

        // 3. NOVAS SETAS DE NAVEGAÇÃO REFINADAS
        // Seta da Esquerda
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart) // Alinha na borda esquerda
                .padding(start = 16.dp) // Distância da borda
                .background(Color.Black.copy(alpha = 0.3f), CircleShape) // Fundo circular semitransparente
                .clip(CircleShape)
                .clickable {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, // <<< ÍCONE ALTERADO
                contentDescription = "Página Anterior",
                tint = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp) // Padding interno do ícone
                    .size(20.dp)
                    .rotate(180f) // <<< ROTAÇÃO ADICIONADA
            )
        }

        // Seta da Direita
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd) // Alinha na borda direita
                .padding(end = 16.dp) // Distância da borda
                .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                .clip(CircleShape)
                .clickable {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Próxima Página",
                tint = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .size(20.dp)
            )
        }

        // Indicadores (barrinhas)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(carouselItems.size) { iteration ->
                val color = if (pagerState.currentPage % carouselItems.size == iteration) Color.White else Color.Gray.copy(alpha = 0.5f)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color)
                        .size(width = 24.dp, height = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EbookCard(
    imageRes: String,
    contentDescription: String,
    onDownloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Column para agrupar a imagem e o botão
    Column(
        modifier = modifier.width(180.dp), // Largura base do conjunto
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp) // Espaço entre a imagem e o botão
    ) {
        // Card agora contém apenas a imagem
        Card(
            modifier = Modifier.height(300.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Image(
                painter = getDrawableResource(imageRes),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        // Botão separado, abaixo da imagem
        Button(
            onClick = onDownloadClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A8E5A) // Verde dos botões do site
            )
        ) {
            Text("Baixar E-book", fontSize = 14.sp, color = Color.White)
        }
    }
}
