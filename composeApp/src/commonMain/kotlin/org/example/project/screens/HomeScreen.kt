package org.example.project.screens

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
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// =====================================================================================
// CAMADAS DE DADOS E DOMÍNIO (MOCK)
// Diretriz Arquitetural: Em uma aplicação real, estas classes e objetos seriam
// movidos para seus próprios arquivos nos módulos `data` e `domain` para
// aderir aos princípios de Clean Architecture e Separação de Responsabilidades.
// =====================================================================================

@OptIn(ExperimentalResourceApi::class)
private object AppResources {
    val carouselItems = listOf(
        Res.drawable.fasesdavida,
        Res.drawable.origemalimentar,
        Res.drawable.receitas,
        Res.drawable.comunidade,
        Res.drawable.jogo
    )

    val ebooks = listOf(
        Ebook(imageRes = Res.drawable.ebook_lanches, contentDescription = "Capa do e-book Guia de Lanches Saudáveis"),
        Ebook(imageRes = Res.drawable.ebook_terceira_idade, contentDescription = "Capa do e-book Nutrição na Terceira Idade"),
        Ebook(imageRes = Res.drawable.ebook_infantil, contentDescription = "Capa do e-book Criança Bem Nutrida, Futuro Saudável")
    )

    val actions = listOf(
        Action(
            date = "25 DEZ",
            imageRes = Res.drawable.acao_crianca,
            title = "Ação das crianças",
            description = "Nossa Ação de Nutrição Infantil foi um sucesso! Levamos aprendizado e diversão para a sala de aula com atividades lúdicas, incluindo um \"teste cego\" de paladar.",
            location = "Escola Municipal Sociólogo Gilberto Freyre"
        ),
        Action(
            date = "15 JAN",
            imageRes = Res.drawable.acao_adulto,
            title = "Ação dos Adultos",
            description = "Nossa Ação Adulto promoveu saúde no trabalho no CDC | CENTRO DE DESENVOLVIMENTO E CIDADANIA. Fizemos atendimentos nutricionais e uma palestra sobre alimentação e estresse.",
            location = "CDC"
        ),
        Action(
            date = "08 MAR",
            imageRes = Res.drawable.acao_idoso,
            title = "Ação dos idosos",
            description = "Um bate-papo com especialistas sobre Sarcopenia, Hidratação e a importância das Proteínas na Terceira Idade.",
            location = "Parque Santana"
        )
    )
}

private data class Action(val date: String, val title: String, val description: String, val location: String, val imageRes: DrawableResource)
private data class Ebook(val imageRes: DrawableResource, val contentDescription: String)
private data class WaterIntakeParams(val weight: Int, val age: Int)
private data class WaterIntakeResult(val amountInMl: Int)

private class CalculateDailyWaterIntakeUseCase {
    operator fun invoke(params: WaterIntakeParams): Result<WaterIntakeResult> {
        if (params.weight <= 0) return Result.failure(IllegalArgumentException("O peso deve ser um valor positivo."))

        val multiplier = when (params.age) {
            in 0..17 -> 40
            in 18..55 -> 35
            in 56..65 -> 30
            else -> 25
        }
        return Result.success(WaterIntakeResult(params.weight * multiplier))
    }
}

// =====================================================================================
// CAMADA DE UI (COMPOSABLES)
// DIRETRIZ ARQUITETURAL: O uso de Scaffold é introduzido para gerenciar
// corretamente a estrutura da tela e os WindowInsets, permitindo o efeito
// Edge-to-Edge sem que as barras do sistema obstruam o conteúdo interativo.
// =====================================================================================

@Composable
expect fun isLandscape(): Boolean

@Composable
fun HomeScreen() {
    if (isLandscape()) {
        LandscapeHomeScreen()
        return
    }

    PortraitHomeScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitHomeScreen() {
    Scaffold(
        containerColor = Color(0xFFe6dfca),
        contentWindowInsets = WindowInsets(0.dp)
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            item { CarouselSection(items = AppResources.carouselItems) }
            item { EbooksSection(ebooks = AppResources.ebooks) }
            item { ActionsSection(actions = AppResources.actions) }
            item { HydrationCalculatorBlock(modifier = Modifier.padding(vertical = 24.dp)) }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LandscapeHomeScreen() {
    Scaffold(
        containerColor = Color(0xFFe6dfca),
        contentWindowInsets = WindowInsets(0.dp)
    ) { contentPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(modifier = Modifier.fillMaxHeight().weight(1f)) {
                CarouselSection(items = AppResources.carouselItems)
            }
            LazyColumn(
                modifier = Modifier.fillMaxHeight().weight(1f).padding(horizontal = 24.dp),
                contentPadding = PaddingValues(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { EbooksSection(ebooks = AppResources.ebooks) }
                item { ActionsSection(actions = AppResources.actions) }
                item { HydrationCalculatorBlock() }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CarouselSection(items: List<DrawableResource>) {
    val pageCount = Int.MAX_VALUE
    val startingPoint = pageCount / 2
    val initialPage = startingPoint - (startingPoint % items.size)
    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }
    val scope = rememberCoroutineScope()
    val navigationButtonBackgroundColor = Color.Black.copy(alpha = 0.2f)

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
        modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
            CarouselCard(imageRes = items[page % items.size])
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage - 1,
                            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                        )
                    }
                },
                modifier = Modifier.size(40.dp).clip(CircleShape).background(navigationButtonBackgroundColor)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Página anterior", tint = Color.White, modifier = Modifier.size(24.dp))
            }
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                        )
                    }
                },
                modifier = Modifier.size(40.dp).clip(CircleShape).background(navigationButtonBackgroundColor)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Próxima página", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }

        Row(
            Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.indices.forEach { index ->
                val color = if ((pagerState.currentPage % items.size) == index) Color.White else Color.Gray.copy(alpha = 0.5f)
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CarouselCard(imageRes: DrawableResource, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun EbooksSection(ebooks: List<Ebook>) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 60.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nossos Materiais Exclusivos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(ebooks) { ebook ->
                EbookCard(ebook = ebook, onDownloadClick = { /*TODO*/ })
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EbookCard(ebook: Ebook, onDownloadClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(180.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Image(
                painter = painterResource(ebook.imageRes),
                contentDescription = ebook.contentDescription,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(
            onClick = onDownloadClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5A8E5A))
        ) {
            Text("Baixar E-book", fontSize = 14.sp, color = Color.White)
        }
    }
}

@Composable
private fun ActionsSection(actions: List<Action>) {
    // Implementação de Early Return para garantir resiliência contra listas vazias.
    if (actions.isEmpty()) {
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Nossas Ações",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // RECORRIGIDO: Utiliza LazyRow para criar um carrossel horizontal.
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento entre os cards.
        ) {
            items(actions) { action ->
                ActionCard(action = action)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ActionCard(action: Action, modifier: Modifier = Modifier) {
    /**
     * Componente para exibição de uma única ação social em um carrossel.
     * Possui largura fixa para garantir um layout consistente e compacto.
     * Inclui animação de feedback visual no botão de ação.
     *
     * @param action O objeto de dados imutável contendo as informações da ação.
     * @param modifier Modificador para customizações externas.
     */
    Card(
        modifier = modifier.width(280.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                painter = painterResource(action.imageRes),
                contentDescription = "Imagem ilustrativa da ${action.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 10f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5A8E5A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ícone de localização",
                            tint = Color(0xFF5A8E5A),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = action.location,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                    }

                    // --- INÍCIO DA MODIFICAÇÃO PARA ANIMAÇÃO E RIPPLE ---

                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()

                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.95f else 1f,
                        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                        label = "ButtonScaleAnimation"
                    )

                    OutlinedButton(
                        onClick = { /* TODO: Implementar navegação */ },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color(0xFF5A8E5A)),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        interactionSource = interactionSource,
                        modifier = Modifier.graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                    ) {
                        Text("Veja aqui", fontSize = 12.sp, color = Color(0xFF333333))
                    }
                    // --- FIM DA MODIFICAÇÃO ---
                }
            }
        }
    }
}

@Composable
private fun HydrationCalculatorBlock(modifier: Modifier = Modifier) {
    var weight by remember { mutableStateOf(70) }
    var age by remember { mutableStateOf(30) }
    var result by remember { mutableStateOf<Result<WaterIntakeResult>?>(null) }
    val useCase = remember { CalculateDailyWaterIntakeUseCase() }

    fun calculate() {
        result = useCase(WaterIntakeParams(weight, age))
    }

    LaunchedEffect(Unit) {
        calculate()
    }

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Calcule sua Hidratação Diária", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF333333), modifier = Modifier.padding(bottom = 24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFBFA)),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Seu peso (kg)", fontWeight = FontWeight.Medium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (weight > 1) weight-- }) { Icon(Icons.Default.Remove, "Diminuir peso", tint = Color(0xFFA52A2A)) }
                        Text(weight.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { weight++ }) { Icon(Icons.Default.Add, "Aumentar peso", tint = Color(0xFFA52A2A)) }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sua idade", fontWeight = FontWeight.Medium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (age > 1) age-- }) { Icon(Icons.Default.Remove, "Diminuir idade", tint = Color(0xFFA52A2A)) }
                        Text(age.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { age++ }) { Icon(Icons.Default.Add, "Aumentar idade", tint = Color(0xFFA52A2A)) }
                    }
                }
                OutlinedButton(
                    onClick = { calculate() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFDCD6C8))
                ) {
                    Text("Calcular", color = Color(0xFFA52A2A), fontWeight = FontWeight.Bold)
                }
                result?.let { resultData ->
                    val resultText = resultData.fold(
                        onSuccess = { successData -> "${successData.amountInMl} ml/dia" },
                        onFailure = { exception -> exception.message ?: "Erro desconhecido" }
                    )
                    val resultColor = if (resultData.isSuccess) Color(0xFF006400) else Color.Red
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = resultText, color = resultColor, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
