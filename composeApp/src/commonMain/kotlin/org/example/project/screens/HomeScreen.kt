package org.example.project.screens

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.acao_adulto
import alimentandofasesapp.composeapp.generated.resources.acao_crianca
import alimentandofasesapp.composeapp.generated.resources.acao_idoso
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
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.MockData.actionsData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// Cores extraídas do design do site para manter a consistência
private object HomeScreenDefaults {
    val BackgroundColor = Color(0xFFe7dfc9) // Novo fundo claro
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
        "acao-crianca" -> painterResource(Res.drawable.acao_crianca)
        "acao-adulto" -> painterResource(Res.drawable.acao_adulto)
        "acao-idoso" -> painterResource(Res.drawable.acao_idoso)
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitHomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeScreenDefaults.BackgroundColor),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CarouselSection()
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nossos Materiais Exclusivos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
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

        item {
            ActionsSection() // SEU COMPONENTE EXISTENTE.
        }

        item {
            HydrationCalculatorBlock()
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
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            CarouselSection()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Nossos Materiais Exclusivos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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

            item {
                ActionsSection()
            }

            item {
                HydrationCalculatorBlock()
            }
        }
    }
}

@Composable
private fun HydrationCalculatorBlock() {
    // --- SEÇÃO DA CALCULADORA DE HIDRATAÇÃO ---
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Aumenta o espaçamento após remover o texto
    ) {
        // Título Principal da Seção
        Text(
            text = "Calculadora de Hidratação",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333) // <<< CORRIGIDO PARA O PADRÃO DE TÍTULO
        )
        // O componente da calculadora em si
        WaterIntakeCalculatorSection()
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

@Composable
fun ActionsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nossas Ações",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333) // Cor do texto ajustada para fundo claro
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(actionsData) { action ->
                ActionCard(
                    date = action.date,
                    title = action.title,
                    description = action.description,
                    location = action.location,
                    imageRes = action.imageRes
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActionCard(date: String, title: String, description: String, location: String, imageRes: String) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(350.dp), // Altura ajustada para acomodar o botão confortavelmente
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2E2E2E) // Fundo da área de texto
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // --- 1. ÁREA DA IMAGEM ---
            Image(
                painter = getDrawableResource(imageRes),
                contentDescription = "Imagem da ação: $title",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )

            // --- 2. ÁREA DO CONTEÚDO ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = date.uppercase(),
                    color = Color(0xFFE0E0E0),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB0B0B0),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Spacer para empurrar o conteúdo inferior para o final do card
                Spacer(modifier = Modifier.weight(1f))

                // --- 3. SEÇÃO INFERIOR: LOCALIZAÇÃO E BOTÃO ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Localização (alinhada à esquerda)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f) // Ocupa o espaço disponível, empurrando o botão
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ícone de localização",
                            tint = Color(0xFFB0B0B0),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFB0B0B0),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Botão "Veja aqui" (alinhado à direita)
                    OutlinedButton(
                        onClick = { /* TODO: Implementar navegação */ },
                        shape = RoundedCornerShape(50.dp),
                        border = BorderStroke(1.dp, Color(0xFF4CAF50)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "Veja aqui",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

// INÍCIO DO NOVO COMPONENTE - SUBSTITUA O ANTIGO COMPLETAMENTE

// Definição de Tipos para clareza e segurança
private data class AgeBracket(val label: String, val multiplier: Int)
private enum class AgeGroup { JUNIOR, ADULT_1, ADULT_2, SENIOR }

// Cores e Estilos extraídos do design de referência
private object CalculatorDefaults {
    val CardBackground = Color(0xFFFBF5E5)
    val ButtonColor = Color(0xFF006400) // Verde Correto
    val TextPrimary = Color(0xFF3D3D3D)
    val TextSecondary = Color(0xFF5A5A5A)
    val ResultDisplayBackground = Color(0xFFE9F2F1)
    val ResultTextColor = Color(0xFF006400) // Verde Correto
    val BorderColor = Color(0xFFD3B8B8)
    val SelectedChipColor = Color(0xFF006400) // Verde Correto
    val UnselectedChipColor = Color.Transparent
}

@Composable
private fun WaterIntakeCalculatorSection() {
    // --- ESTADO DO COMPONENTE ---
    var weight by remember { mutableStateOf(70) }
    var selectedAgeGroup by remember { mutableStateOf(AgeGroup.ADULT_2) }
    var resultLiters by remember { mutableStateOf<Float?>(null) }

    val ageBrackets = mapOf(
        AgeGroup.JUNIOR to AgeBracket("Jovens (até 17)", 40),
        AgeGroup.ADULT_1 to AgeBracket("Adultos (18-55)", 35),
        AgeGroup.ADULT_2 to AgeBracket("Adultos (55-65)", 30),
        AgeGroup.SENIOR to AgeBracket("Idosos (66+)", 25)
    )

    // --- ESTRUTURA PRINCIPAL (CARD) ---
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CalculatorDefaults.CardBackground),
        border = BorderStroke(1.dp, CalculatorDefaults.BorderColor)
    ) {
        // --- NOVO LAYOUT COM DUAS COLUNAS ---
        Row(
            modifier = Modifier.padding(vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- COLUNA ESQUERDA: CONTROLES ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                 // Título Interno
                Text(
                    text = "Calcule sua Meta Pessoal",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = CalculatorDefaults.TextPrimary,
                    textAlign = TextAlign.Center
                )
                WeightControl(
                    weight = weight,
                    onIncrement = { if (weight < 200) weight++ },
                    onDecrement = { if (weight > 10) weight-- }
                )
                AgeGroupSelector(
                    ageBrackets = ageBrackets,
                    selectedGroup = selectedAgeGroup,
                    onGroupSelected = { selectedAgeGroup = it }
                )
                Button(
                    onClick = {
                        val multiplier = ageBrackets[selectedAgeGroup]?.multiplier ?: 35
                        resultLiters = (weight * multiplier) / 1000f
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CalculatorDefaults.ButtonColor)
                ) {
                    Text("CALCULAR META", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // --- DIVISOR VERTICAL ---
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                color = CalculatorDefaults.BorderColor
            )

            // --- COLUNA DIREITA: RESULTADO ---
            ResultDisplay(
                resultLiters = resultLiters,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, end = 24.dp)
            )
        }
    }
}
// FIM DA SUBSTITUIÇÃO


// --- SUBCOMPONENTES PRIVADOS ---

@Composable
private fun WeightControl(weight: Int, onIncrement: () -> Unit, onDecrement: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Seu peso (kg):", style = MaterialTheme.typography.bodyLarge, color = CalculatorDefaults.TextSecondary)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            IconButton(onClick = onDecrement, modifier = Modifier.background(CalculatorDefaults.ButtonColor, CircleShape)) {
                Icon(Icons.Default.Remove, contentDescription = "Diminuir peso", tint = Color.White)
            }
            Text(
                text = weight.toString(),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = CalculatorDefaults.TextPrimary
            )
            IconButton(onClick = onIncrement, modifier = Modifier.background(CalculatorDefaults.ButtonColor, CircleShape)) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar peso", tint = Color.White)
            }
        }
    }
}

@Composable
private fun AgeGroupSelector(
    ageBrackets: Map<AgeGroup, AgeBracket>,
    selectedGroup: AgeGroup,
    onGroupSelected: (AgeGroup) -> Unit
) {
    val groups = ageBrackets.entries.toList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Sua faixa de idade:",
            style = MaterialTheme.typography.bodyLarge,
            color = CalculatorDefaults.TextSecondary
        )
        // Linha 1 do Grid
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AgeChip(
                text = groups[0].value.label,
                isSelected = selectedGroup == groups[0].key,
                onClick = { onGroupSelected(groups[0].key) }
            )
            AgeChip(
                text = groups[1].value.label,
                isSelected = selectedGroup == groups[1].key,
                onClick = { onGroupSelected(groups[1].key) }
            )
        }
        // Linha 2 do Grid
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AgeChip(
                text = groups[2].value.label,
                isSelected = selectedGroup == groups[2].key,
                onClick = { onGroupSelected(groups[2].key) }
            )
            AgeChip(
                text = groups[3].value.label,
                isSelected = selectedGroup == groups[3].key,
                onClick = { onGroupSelected(groups[3].key) }
            )
        }
    }
}

@Composable
private fun AgeChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) CalculatorDefaults.SelectedChipColor else CalculatorDefaults.UnselectedChipColor
    val textColor = if (isSelected) Color.White else CalculatorDefaults.TextSecondary
    val chipBorder = if (isSelected) BorderStroke(1.dp, Color.Transparent) else BorderStroke(1.dp, CalculatorDefaults.BorderColor)

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = chipBorder,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor)
    ) {
        Text(text, color = textColor, fontSize = 12.sp)
    }
}

@Composable
private fun ResultDisplay(resultLiters: Float?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Sua meta diária recomendada é:",
            style = MaterialTheme.typography.bodyMedium,
            color = CalculatorDefaults.TextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (resultLiters == null) {
            Text(
                "(Insira seus dados para calcular.)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        } else {
            val resultText = ((resultLiters * 100).roundToInt() / 100.0).toString().replace('.', ',')
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                    color = CalculatorDefaults.ResultTextColor
                )
                Text(
                    text = " Litros",
                    style = MaterialTheme.typography.headlineSmall,
                    color = CalculatorDefaults.ResultTextColor,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
