package org.example.project.screens

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.logo_app1
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.navigation.Screen
import org.example.project.ui.theme.AppDefaults
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Content, Screen.Community, Screen.Recipes, Screen.Profile)
    val showBottomBar = currentScreen in screens

    Scaffold(
        topBar = {
            if (showBottomBar) {
                val topBarColors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppDefaults.BegeNavegacao,
                    scrolledContainerColor = AppDefaults.BegeNavegacao
                )
                CenterAlignedTopAppBar(
                    title = {
                        currentScreen.label?.let { Text(it) }
                    },
                    navigationIcon = {
                        if (currentScreen is Screen.Home) {
                            IconButton(onClick = { /* TODO: Open Navigation Drawer */ }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    modifier = Modifier.size(32.dp),
                                    // Diretriz Corretiva: Força a cor preta no ícone do menu
                                    // para garantir visibilidade contra o fundo bege.
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
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.Black.copy(alpha = 0.7f),
                                unselectedIconColor = Color.Black.copy(alpha = 0.7f),
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            when (currentScreen) {
                is Screen.Home -> HomeScreen()
                is Screen.Content -> ContentScreen()
                is Screen.Community -> CommunityScreen()
                is Screen.Recipes -> RecipesScreen()
                is Screen.Profile -> ProfileScreen()
                else -> HomeScreen()
            }
        }
    }
}
