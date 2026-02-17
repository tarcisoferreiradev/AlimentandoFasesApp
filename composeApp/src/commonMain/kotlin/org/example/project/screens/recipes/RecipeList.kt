package org.example.project.screens.recipes

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.bolinho
import alimentandofasesapp.composeapp.generated.resources.panquequinha
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// ##################################################################
// #####       SEÇÃO DE RECEITAS POPULARES (ANTIGO RECIPELIST)  #####
// ##################################################################

/**
 * [PopularRecipesSection] exibe uma lista horizontal de receitas populares.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun PopularRecipesSection(onRecipeClick: (String) -> Unit) {
    val recipes = listOf(
        Recipe(
            id = "1",
            name = "Bolinho Vegano de Banana e Aveia",
            rating = 4.9f,
            timeInMinutes = 25,
            imageRes = Res.drawable.bolinho,
            tags = listOf("Sem Glúten", "Vegano")
        ),
        Recipe(
            id = "2",
            name = "Panqueca de banana e aveia",
            rating = 4.7f,
            timeInMinutes = 10,
            imageRes = Res.drawable.panquequinha,
            tags = listOf("Sem Glúten")
        )
    )

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // [REFINAMENTO DE LAYOUT] Adicionado espaço extra acima do título.
        Spacer(Modifier.height(24.dp))
        Text("Popular", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black))
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(IntrinsicSize.Max)
        ) {
            recipes.forEach { recipe ->
                RecipeCard(recipe, modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}

// ##################################################################
// #####               SEÇÃO PARA CADA FASE                     #####
// ##################################################################

/**
 * Define a estrutura de dados para uma categoria de fase de vida.
 * @property name Nome da categoria (ex: "Introdução Alimentar").
 * @property imageRes Recurso de imagem para o card.
 * @property filterKey Chave para navegação ou filtragem.
 */
data class LifeStageCategory(
    val name: String,
    val imageRes: DrawableResource,
    val filterKey: String
)

/**
 * [LifeStageSection] renderiza uma seção dedicada a guiar o usuário
 * por categorias baseadas em fases da vida, reforçando o branding do app.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun LifeStageSection() {
    // [NOTA TÉCNICA] Usando um recurso existente como placeholder para evitar falha de build.
    // Substituir por 'Res.drawable.placeholder_1', etc., quando os recursos forem adicionados.
    val categories = listOf(
        LifeStageCategory("Introdução Alimentar", Res.drawable.bolinho, "baby_food"),
        LifeStageCategory("Crescimento Infantil", Res.drawable.panquequinha, "kids_growth"),
        LifeStageCategory("Leve e Saudável", Res.drawable.bolinho, "light_healthy")
    )

    Column {
        Spacer(Modifier.height(24.dp))
        Text(
            "Para cada Fase",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                LifeStageCard(category)
            }
        }
    }
}

/**
 * [LifeStageCard] é um card de apresentação para uma categoria de fase de vida.
 * Utiliza um gradiente (scrim) sobre a imagem para garantir a legibilidade do texto.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
private fun LifeStageCard(category: LifeStageCategory) {
    Card(
        modifier = Modifier.size(width = 160.dp, height = 200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // [DIRETRIZ DE UI] Adição de um scrim (gradiente) para garantir
            // que o texto branco seja legível sobre qualquer imagem.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 300f
                        )
                    )
            )
            Text(
                text = category.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}


// ##################################################################
// #####               SEÇÃO DE RECEITAS RÁPIDAS                #####
// ##################################################################

/**
 * [QuickRecipesSection] exibe uma lista de receitas rápidas.
 * Arquiteturalmente, reutiliza toda a lógica de `PopularRecipesSection`
 * e `RecipeCard`, demonstrando alta reutilização de componentes.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun QuickRecipesSection(onRecipeClick: (String) -> Unit) {
    // Para fins de layout, usamos os mesmos dados. Em produção,
    // esta lista seria filtrada (ex: timeInMinutes <= 20).
    val recipes = listOf(
        Recipe(
            id = "2",
            name = "Panqueca de banana e aveia",
            rating = 4.7f,
            timeInMinutes = 10,
            imageRes = Res.drawable.panquequinha,
            tags = listOf("Sem Glúten")
        ),
        Recipe(
            id = "1",
            name = "Bolinho Vegano de Banana e Aveia",
            rating = 4.9f,
            timeInMinutes = 25,
            imageRes = Res.drawable.bolinho,
            tags = listOf("Sem Glúten", "Vegano")
        )
    )

    Column {
        Spacer(Modifier.height(24.dp))
        Text(
            "Receitas Rápidas",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            recipes.forEach { recipe ->
                RecipeCard(recipe, modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { onRecipeClick(recipe.id) })
            }
        }
    }
}


// ##################################################################
// #####       COMPONENTES DE CARD E DADOS (JÁ EXISTENTES)      #####
// ##################################################################

/**
 * [RecipeCard] é um componente de apresentação para um único item de receita.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecipeCard(recipe: Recipe, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(recipe.imageRes),
                    contentDescription = recipe.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                RecipeTags(tags = recipe.tags, modifier = Modifier.align(Alignment.TopStart))

                IconButton(
                    onClick = { /* TODO: Implementar lógica de salvar receita */ },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(Icons.Default.BookmarkBorder, "Salvar", tint = Color.White)
                }
            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    recipe.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, "Rating", tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text("${recipe.rating}", fontSize = 14.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Timer, "Preparo", tint = Color.LightGray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text("${recipe.timeInMinutes} Min", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/**
 * [RecipeTags] renderiza uma lista de etiquetas de texto sobrepostas a uma imagem.
 */
@Composable
private fun RecipeTags(tags: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tags.forEach { tag ->
            Surface(
                color = Color.Black.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = tag,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
        }
    }
}

/**
 * [Recipe] define a estrutura de dados para uma receita.
 */
data class Recipe(
    val id: String,
    val name: String,
    val rating: Float,
    val timeInMinutes: Int,
    val imageRes: DrawableResource,
    val tags: List<String>
)
