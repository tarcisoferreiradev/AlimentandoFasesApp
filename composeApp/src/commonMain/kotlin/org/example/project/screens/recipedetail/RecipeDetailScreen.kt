package org.example.project.screens.recipedetail

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.bolinho
import alimentandofasesapp.composeapp.generated.resources.panquequinha
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// [ARCHITECTURAL REFACTOR]
// O modelo de dados foi refatorado para ser a única fonte da verdade, incluindo os
// valores de progresso, desacoplando completamente a UI da lógica de dados.
private data class RecipeDetails(
    val id: String,
    val name: String,
    val imageRes: DrawableResource,
    val tags: List<String>,
    val rating: Double,
    val description: String,
    val calories: Double,
    val carbs: Double,
    val protein: Double,
    val fat: Double,
    val caloriesProgress: Float,
    val carbsProgress: Float,
    val proteinProgress: Float,
    val fatProgress: Float,
    val ingredients: List<Pair<String, String>>,
    val preparationSteps: List<String>
)

@OptIn(ExperimentalResourceApi::class)
private object RecipeDetailRepository {
    private val mockRecipes = mapOf(
        "1" to RecipeDetails(
            id = "1",
            name = "Bolinho Vegano de Banana e Aveia",
            imageRes = Res.drawable.bolinho,
            tags = listOf("Vegano", "Fácil"),
            rating = 4.9,
            description = "Um bolinho rápido e saboroso feito com ingredientes simples que você já tem em casa.",
            calories = 82.3,
            carbs = 13.3,
            protein = 1.9,
            fat = 2.3,
            caloriesProgress = 0.205f,
            carbsProgress = 0.646f,
            proteinProgress = 0.092f,
            fatProgress = 0.251f,
            ingredients = listOf(
                "Bananas maduras" to "2 unidades",
                "Aveia em flocos finos" to "1 xícara",
                "Óleo de coco" to "1 colher (sopa)",
                "Canela em pó" to "1 colher (chá)",
                "Fermento em pó" to "1 colher (chá)"
            ),
            preparationSteps = listOf(
                "Preaqueça o forno a 180°C. Unte as forminhas de muffin ou use formas de silicone.",
                "Em uma tigela grande, amasse bem as bananas maduras até obter uma consistência de purê.",
                "Adicione o óleo de coco e a canela ao purê de banana, misturando bem para integrar os sabores.",
                "Incorpore a aveia em flocos finos gradualmente, mexendo até que a massa fique homogênea.",
                "Por último, adicione o fermento em pó e misture delicadamente apenas o suficiente para incorporar.",
                "Distribua a massa uniformemente nas forminhas, preenchendo cerca de 3/4 de cada uma.",
                "Asse por 20 a 25 minutos, ou até que um palito inserido no centro saia limpo e o topo esteja dourado."
            )
        ),
        "2" to RecipeDetails(
            id = "2",
            name = "Panqueca de Banana e Aveia",
            imageRes = Res.drawable.panquequinha,
            tags = listOf("Rápido", "Sem Glúten"),
            rating = 4.7,
            description = "Uma panqueca fofinha e nutritiva, ideal para um café da manhã rápido ou um lanche da tarde.",
            calories = 60.3,
            carbs = 9.1,
            protein = 2.5,
            fat = 1.7,
            caloriesProgress = 0.151f,
            carbsProgress = 0.591f,
            proteinProgress = 0.163f,
            fatProgress = 0.246f,
            ingredients = listOf(
                "Banana madura" to "1 unidade",
                "Ovo" to "1 unidade",
                "Aveia sem glúten" to "2 colheres (sopa)",
                "Canela em pó" to "a gosto"
            ),
            preparationSteps = listOf(
                "Preparação da Base Úmida: Em um bowl, descasque a banana madura e amasse-a com um garfo até obter uma pasta lisa, sem grumos grandes.",
                "Emulsão: Adicione o ovo à banana amassada e bata vigorosamente com um batedor de arame (fouet) ou garfo até que a mistura esteja aerada e combinada.",
                "Adição de Secos e Aromatização: Incorpore as 2 colheres de aveia e a canela a gosto, misturando apenas o suficiente para hidratar a aveia.",
                "Cocção Térmica: Aqueça uma frigideira antiaderente em fogo baixo. Utilize uma colher para despejar a massa, formando pequenas panquecas.",
                "Finalização: Assim que surgirem pequenas bolhas na superfície, vire com uma espátula e doure o outro lado por cerca de 1 minuto."
            )
        )
    )

    fun getRecipeById(id: String): RecipeDetails? = mockRecipes[id]
}

private data class Nutrient(val value: String, val label: String, val color: Color, val progress: Float)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecipeDetailScreen(recipeId: String, onNavigateBack: () -> Unit) {
    val recipe = remember(recipeId) { RecipeDetailRepository.getRecipeById(recipeId) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    if (recipe == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Receita não encontrada.")
        }
        return
    }

    val imageHeight = 400.dp
    val backgroundColor = Color(0xFFe6dfca)

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = backgroundColor
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource( recipe.imageRes),
                contentDescription = recipe.name,
                modifier = Modifier.fillMaxWidth().height(imageHeight),
                contentScale = ContentScale.Crop
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = imageHeight - 24.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .padding(16.dp)
                    ) {
                        RecipeHeader(recipe)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(recipe.description, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Informações Nutricionais", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("(por porção)", style = MaterialTheme.typography.bodySmall, color = Color.Black.copy(alpha = 0.7f))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        NutritionalInfo(recipe)
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        val tabs = listOf("Ingredientes", "Modo de Preparo")
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            containerColor = Color.Black.copy(alpha = 0.05f),
                            contentColor = Color.Black,
                            modifier = Modifier.clip(RoundedCornerShape(50))
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index },
                                    text = { Text(title) },
                                    selectedContentColor = Color.Black,
                                    unselectedContentColor = Color.Black.copy(alpha = 0.7f)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        when (selectedTabIndex) {
                            0 -> IngredientsList(recipe.ingredients)
                            1 -> InstructionsList(recipe.preparationSteps)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding() + 16.dp))
                }
            }
            TopBarActions(onNavigateBack)
        }
    }
}

@Composable
private fun RecipeHeader(recipe: RecipeDetails) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                recipe.tags.forEach { tag ->
                    val color = if (tag == "Vegano") Color(0xFFE0EFFF) else Color(0xFFFFF0E0)
                    Text(text=tag, modifier=Modifier.background(color, RoundedCornerShape(12.dp)).padding(horizontal=12.dp, vertical=4.dp), fontSize=12.sp, color=Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(recipe.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, "Rating", tint=Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(4.dp))
                Text("${recipe.rating}", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        IconButton(onClick = { /* Salvar */ }) {
            Icon(Icons.Default.BookmarkBorder, "Salvar", tint = Color.Black)
        }
    }
}

@Composable
private fun NutritionalInfo(recipe: RecipeDetails) {
    // [STATE-DRIVEN UI]
    // O componente agora é puramente presentacional, consumindo os valores de progresso
    // diretamente do modelo de dados `recipe`, o que o torna reutilizável e desacoplado.
    val items = listOf(
        Nutrient(value = "${recipe.calories}", label = "Kcal", color = Color(0xFF53C22C), progress = recipe.caloriesProgress),
        Nutrient(value = "${recipe.carbs} g", label = "Carb", color = Color(0xFFFFA500), progress = recipe.carbsProgress),
        Nutrient(value = "${recipe.protein} g", label = "Prot", color = Color(0xFFFFC107), progress = recipe.proteinProgress),
        Nutrient(value = "${recipe.fat} g", label = "Gord", color = Color(0xFFF44336), progress = recipe.fatProgress)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items.forEach { nutrient ->
            NutrientIndicator(nutrient)
        }
    }
}

@Composable
private fun NutrientIndicator(nutrient: Nutrient) {
    var isAnimated by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (isAnimated) nutrient.progress else 0f,
        animationSpec = tween(durationMillis = 1200, delayMillis = 200, easing = FastOutSlowInEasing),
        label = "NutrientProgressAnimation"
    )

    LaunchedEffect(Unit) { isAnimated = true }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(90.dp)
            .shadow(elevation = 8.dp, shape = CircleShape)
            .background(Color.White, CircleShape)
            .padding(4.dp)
    ) {
        CircularProgressIndicator(progress={1f}, modifier=Modifier.fillMaxSize(), color=nutrient.color.copy(alpha=0.15f), strokeWidth=7.dp)
        CircularProgressIndicator(progress={animatedProgress}, modifier=Modifier.fillMaxSize(), color=nutrient.color, strokeWidth=7.dp, trackColor=Color.Transparent, strokeCap=StrokeCap.Round)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text=nutrient.value, fontWeight=FontWeight.Bold, fontSize=16.sp, color=Color.Black)
            Text(text=nutrient.label, fontSize=14.sp, color=Color.Black.copy(alpha=0.7f))
        }
    }
}

@Composable
private fun IngredientsList(ingredients: List<Pair<String, String>>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ingredients.forEach { (name, quantity) ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween, verticalAlignment=Alignment.CenterVertically) {
                Text(name, fontWeight = FontWeight.Medium, color = Color.Black)
                Text(quantity, color = Color.Black)
            }
        }
    }
}

@Composable
private fun InstructionsList(steps: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        steps.forEachIndexed { index, step ->
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "${index + 1}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.8f), CircleShape)
                        .padding(8.dp),
                    fontSize = 12.sp
                )
                Text(
                    text = step,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                    color = Color.Black,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun TopBarActions(onNavigateBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal=16.dp, vertical=8.dp)) {
        val buttonModifier = Modifier.clip(CircleShape).background(Color.Black.copy(alpha=0.3f))
        IconButton(onClick=onNavigateBack, modifier=buttonModifier.align(Alignment.CenterStart)) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint=Color.White) }
        IconButton(onClick={/* Compartilhar */}, modifier=buttonModifier.align(Alignment.CenterEnd)) { Icon(Icons.Default.Share, "Compartilhar", tint=Color.White) }
    }
}
