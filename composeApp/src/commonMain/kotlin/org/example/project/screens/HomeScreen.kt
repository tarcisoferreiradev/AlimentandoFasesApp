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
import alimentandofasesapp.composeapp.generated.resources.ic_instagram_official
import alimentandofasesapp.composeapp.generated.resources.jogo
import alimentandofasesapp.composeapp.generated.resources.origemalimentar
import alimentandofasesapp.composeapp.generated.resources.receitas
import alimentandofasesapp.composeapp.generated.resources.rede_social
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.core.WaterIntakeParams
import org.example.project.core.WaterIntakeResult
import org.example.project.core.glassesOf250ml
import org.example.project.core.liters
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

// =====================================================================================
// PLATFORM-SPECIFIC (EXPECT)
// =====================================================================================

@Composable
expect fun isLandscape(): Boolean

@Composable
expect fun platformTestNotificationHandler(): () -> Unit

// =====================================================================================
// DATA AND DOMAIN LAYERS (MOCK)
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
            description = "Nossa Ação de Nutrição Infantil foi um sucesso! Levamos aprendizado e diversão para a sala de aula com atividades lúdicas, incluindo um 'teste cego' de paladar.",
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

private fun Float.toBrazilianDecimalFormat(): String {
    val integerPart = toInt()
    val decimalPart = ((this * 100).roundToInt() % 100)
    val decimalString = decimalPart.toString().padStart(2, '0')
    return "$integerPart,$decimalString"
}

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
// UI LAYER (COMPOSABLES)
// =====================================================================================

@Composable
fun HomeScreen() {
    var calculatorWeight by rememberSaveable { mutableStateOf(70) }
    var calculatorSelectedAgeIndex by rememberSaveable { mutableStateOf(1) }
    var calculatorResult: Result<WaterIntakeResult>? by rememberSaveable(stateSaver = resultSaver()) {
        mutableStateOf(null)
    }

    val useCase = remember { CalculateDailyWaterIntakeUseCase() }
    val calculatorAge = when (calculatorSelectedAgeIndex) {
        0 -> 16; 1 -> 30; 2 -> 60; else -> 70
    }

    fun onCalculate() {
        calculatorResult = useCase(WaterIntakeParams(calculatorWeight, calculatorAge))
    }

    val isLandscape = isLandscape()
    val onTestNotificationClick = platformTestNotificationHandler()

    if (isLandscape) {
        LandscapeHomeScreen(
            weight = calculatorWeight,
            selectedAgeIndex = calculatorSelectedAgeIndex,
            result = calculatorResult,
            onWeightChange = { calculatorWeight = it },
            onAgeIndexChange = { calculatorSelectedAgeIndex = it },
            onCalculateClick = ::onCalculate,
            onTestNotificationClick = onTestNotificationClick
        )
    } else {
        PortraitHomeScreen(
            weight = calculatorWeight,
            selectedAgeIndex = calculatorSelectedAgeIndex,
            result = calculatorResult,
            onWeightChange = { calculatorWeight = it },
            onAgeIndexChange = { calculatorSelectedAgeIndex = it },
            onCalculateClick = ::onCalculate,
            onTestNotificationClick = onTestNotificationClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PortraitHomeScreen(
    weight: Int,
    selectedAgeIndex: Int,
    result: Result<WaterIntakeResult>?,
    onWeightChange: (Int) -> Unit,
    onAgeIndexChange: (Int) -> Unit,
    onCalculateClick: () -> Unit,
    onTestNotificationClick: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFe6dfca),
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTestNotificationClick,
                containerColor = Color(0xFF5A8E5A),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Testar Notificação")
            }
        }
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
            item {
                HydrationCalculatorBlock(
                    modifier = Modifier.padding(vertical = 24.dp),
                    weight = weight,
                    selectedAgeIndex = selectedAgeIndex,
                    result = result,
                    onWeightChange = onWeightChange,
                    onAgeIndexChange = onAgeIndexChange,
                    onCalculateClick = onCalculateClick
                )
            }
            item { ContactCtaSection() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LandscapeHomeScreen(
    weight: Int,
    selectedAgeIndex: Int,
    result: Result<WaterIntakeResult>?,
    onWeightChange: (Int) -> Unit,
    onAgeIndexChange: (Int) -> Unit,
    onCalculateClick: () -> Unit,
    onTestNotificationClick: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFe6dfca),
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onTestNotificationClick,
                containerColor = Color(0xFF5A8E5A),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Testar Notificação")
            }
        }
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
                item {
                    HydrationCalculatorBlock(
                        weight = weight,
                        selectedAgeIndex = selectedAgeIndex,
                        result = result,
                        onWeightChange = onWeightChange,
                        onAgeIndexChange = onAgeIndexChange,
                        onCalculateClick = onCalculateClick
                    )
                }
                item { ContactCtaSection() }
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

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                }
            }
        }
    }
}

@Composable
private fun HydrationCalculatorBlock(
    weight: Int,
    selectedAgeIndex: Int,
    result: Result<WaterIntakeResult>?,
    onWeightChange: (Int) -> Unit,
    onAgeIndexChange: (Int) -> Unit,
    onCalculateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = Color(0xFF5A8E5A)
    val backgroundColor = Color(0xFFFDFCEC)
    val resultBackgroundColor = Color(0xFFEFFFF0)

    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Calcule sua Meta Pessoal",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.5f)),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                InputSection(
                    weight = weight,
                    onWeightChange = { newWeight -> onWeightChange(newWeight.coerceIn(10, 200)) },
                    selectedAgeIndex = selectedAgeIndex,
                    onAgeIndexChange = onAgeIndexChange,
                    onCalculateClick = onCalculateClick,
                    primaryColor = primaryColor,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(primaryColor.copy(alpha = 0.5f)))

                ResultSection(
                    result = result,
                    backgroundColor = resultBackgroundColor,
                    primaryColor = primaryColor,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun InputSection(
    weight: Int,
    onWeightChange: (Int) -> Unit,
    selectedAgeIndex: Int,
    onAgeIndexChange: (Int) -> Unit,
    onCalculateClick: () -> Unit,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Seu peso (kg):", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        WeightStepper(
            weight = weight,
            onDecrement = { onWeightChange(weight - 1) },
            onIncrement = { onWeightChange(weight + 1) },
            color = primaryColor
        )

        Text("Sua faixa de idade:", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        AgeRangeSelector(
            selectedIndex = selectedAgeIndex,
            onIndexSelected = onAgeIndexChange,
            color = primaryColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCalculateClick,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
        ) {
            Text("Calcular Meta", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ResultSection(
    result: Result<WaterIntakeResult>?,
    backgroundColor: Color,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    val targetLiters = result?.getOrNull()?.liters ?: 0.0f
    val animatedLiters by animateFloatAsState(
        targetValue = targetLiters,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "LitersAnimation"
    )

    Column(
        modifier = modifier.fillMaxHeight().background(backgroundColor).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Sua meta diária recomendada é:",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(Modifier.height(16.dp))

        val resultText = animatedLiters.toBrazilianDecimalFormat()

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                resultText,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
            Text(
                " Litros",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(
            "O que equivale a aproximadamente:",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        val glassesText = result?.getOrNull()?.takeIf { it.liters > 0 }?.let {
            "${it.glassesOf250ml} copos de 250ml"
        } ?: "(Insira seus dados para calcular.)"

        Text(
            glassesText,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun WeightStepper(
    weight: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val buttonModifier = Modifier.size(40.dp).clip(CircleShape)

        IconButton(onClick = onDecrement, modifier = buttonModifier.background(color)) {
            Icon(Icons.Default.Remove, contentDescription = "Diminuir peso", tint = Color.White)
        }

        Text(
            text = weight.toString(),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )

        IconButton(onClick = onIncrement, modifier = buttonModifier.background(color)) {
            Icon(Icons.Default.Add, contentDescription = "Aumentar peso", tint = Color.White)
        }
    }
}

@Composable
private fun AgeRangeSelector(
    selectedIndex: Int,
    onIndexSelected: (Int) -> Unit,
    color: Color
) {
    val ageRanges = listOf("Até 17 anos", "18 a 55 anos", "56 a 65 anos", "66+ anos")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (0..1).forEach { index ->
                AgeButton(
                    text = ageRanges[index],
                    isSelected = selectedIndex == index,
                    onClick = { onIndexSelected(index) },
                    selectedColor = color,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            (2..3).forEach { index ->
                AgeButton(
                    text = ageRanges[index],
                    isSelected = selectedIndex == index,
                    onClick = { onIndexSelected(index) },
                    selectedColor = color,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun AgeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) selectedColor else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.Black
    val border = if (isSelected) BorderStroke(1.dp, selectedColor) else BorderStroke(1.dp, Color.Gray)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor, contentColor = contentColor),
        border = border,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ContactCtaSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Onde nos achar",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(Res.drawable.rede_social),
                contentDescription = "Banner de contato: Fique por dentro, acompanhe as novidades e nos siga no @alimentandofases",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                contentScale = ContentScale.FillWidth
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CustomFollowButton(modifier = Modifier.offset(y = 125.dp))
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CustomFollowButton(modifier: Modifier = Modifier) {
    val primaryColor = Color(0xFF5A8E5A)
    val buttonHeight = 60.dp
    val buttonWidth = 250.dp

    val uriHandler = LocalUriHandler.current
    val instagramUrl = "https://www.instagram.com/alimentandofases?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=="

    Box(
        modifier = modifier
            .clickable(
                onClick = { uriHandler.openUri(instagramUrl) }
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier.padding(top = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .width(buttonWidth)
                    .height(buttonHeight)
                    .offset(x = 4.dp, y = 4.dp),
                shape = CircleShape,
                color = Color.Black
            ) {}

            Surface(
                modifier = Modifier
                    .width(buttonWidth)
                    .height(buttonHeight),
                shape = CircleShape,
                color = primaryColor,
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        "NOS SIGA",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Card(
            modifier = Modifier.zIndex(1f),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_instagram_official),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "@alimentandofases",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }
        }
    }
}

// =====================================================================================
// SAVERS
// =====================================================================================

@Suppress("UNCHECKED_CAST")
private fun <T : Any> resultSaver(): Saver<Result<T>?, Any> {
    return Saver(
        save = { it?.getOrNull() },
        restore = { saved ->
            saved?.let { Result.success(it as T) }
        }
    )
}
