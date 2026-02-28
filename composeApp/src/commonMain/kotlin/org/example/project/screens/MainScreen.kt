package org.example.project.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.components.BackButtonHandler
import org.example.project.navigation.Screen
import org.example.project.screens.contenthub.ContentHubScreen
import org.example.project.screens.recipedetail.RecipeDetailScreen
import org.example.project.ui.theme.AppDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Content, Screen.Community, Screen.Recipes, Screen.Profile)

    var currentRecipeId by remember { mutableStateOf<String?>(null) }

    val recipeId = currentRecipeId

    // [ARCHITECTURAL REFINEMENT] O BackButtonHandler (multiplataforma) agora gerencia a navegação de retorno
    // em diferentes níveis de estado. Se uma receita estiver sendo exibida, ele lida com o
    // retorno para a lista. Caso contrário, ele trata da navegação para a HomeScreen a
    // partir de outras seções principais.
    BackButtonHandler(enabled = recipeId != null || currentScreen !is Screen.Home) {
        if (recipeId != null) {
            currentRecipeId = null
        } else {
            currentScreen = Screen.Home
        }
    }

    if (recipeId != null) {
        RecipeDetailScreen(
            recipeId = recipeId,
            onNavigateBack = { currentRecipeId = null }
        )
    } else {
        val showBottomBar = currentScreen in screens
        var topBarTitle by remember { mutableStateOf<String?>(null) }

        Scaffold(
            topBar = {
                topBarTitle?.let {
                    val topBarColors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppDefaults.BegeNavegacao,
                        scrolledContainerColor = AppDefaults.BegeNavegacao
                    )
                    CenterAlignedTopAppBar(
                        // [UI REFINEMENT] O título foi removido para uma UI mais limpa. A barra
                        // (com seus ícones) continua a ser exibida, mas o conteúdo de texto
                        // do título foi esvaziado, desacoplando a estrutura da barra de seu conteúdo.
                        title = { },
                        navigationIcon = {
                            if (showBottomBar) {
                                IconButton(onClick = { /* TODO: Open Navigation Drawer */ }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        modifier = Modifier.size(32.dp),
                                        tint = Color.Black
                                    )
                                }
                            }
                        },
                        actions = {
                            when (currentScreen) {
                                is Screen.Content -> {
                                    IconButton(onClick = { /* TODO */ }) {
                                        Icon(Icons.Outlined.Search, "Pesquisar")
                                    }
                                }

                                is Screen.Community -> {
                                    IconButton(onClick = { /* TODO */ }) {
                                        Icon(Icons.Default.Add, "Criar Post")
                                    }
                                    IconButton(onClick = { /* TODO */ }) {
                                        Icon(Icons.AutoMirrored.Filled.Chat, "Mensagens")
                                    }
                                }

                                is Screen.Profile -> {
                                    IconButton(onClick = { /* TODO */ }) {
                                        Icon(Icons.Outlined.Settings, "Configurações")
                                    }
                                }

                                else -> {}
                            }
                        },
                        colors = topBarColors,
                        windowInsets = WindowInsets.statusBars
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar(containerColor = AppDefaults.BegeNavegacao) {
                        screens.forEach { screen ->
                            val isSelected = currentScreen == screen
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentScreen = screen },
                                label = { Text(screen.label!!) },
                                icon = {
                                    Icon(
                                        imageVector = screen.icon!!,
                                        contentDescription = screen.label
                                    )
                                },
                                // [ACCESSIBILITY REFINEMENT]
                                // A cor do texto e dos ícones foi definida como Color.Black sólido para
                                // garantir alto contraste e máxima legibilidade, alinhando-se às
                                // diretrizes de acessibilidade (WCAG).
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    unselectedIconColor = Color.Black,
                                    selectedTextColor = Color.Black,
                                    unselectedTextColor = Color.Black,
                                    indicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                val onTitleChange: (String?) -> Unit = { newTitle -> topBarTitle = newTitle }
                when (currentScreen) {
                    is Screen.Home -> HomeScreen(onTitleChange = onTitleChange)
                    is Screen.Content -> ContentHubScreen(onTitleChange = onTitleChange)
                    is Screen.Community -> CommunityScreen(onTitleChange = onTitleChange)
                    is Screen.Recipes -> RecipesScreen(
                        onTitleChange = onTitleChange,
                        onNavigateToRecipeDetail = { id -> currentRecipeId = id }
                    )
                    is Screen.Profile -> ProfileScreen(onTitleChange = onTitleChange)
                    // [ARCHITECTURAL REFINEMENT]
                    // Adicionado um `else` para tornar o `when` exaustivo. Isso garante que qualquer
                    // estado de tela não mapeado seja tratado de forma resiliente, revertendo para
                    // a HomeScreen como um fallback seguro e prevenindo crashes ou telas em branco.
                    else -> HomeScreen(onTitleChange = onTitleChange)
                }
            }
        }
    }
}
