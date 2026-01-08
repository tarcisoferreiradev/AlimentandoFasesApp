package org.example.project.screens

import alimentandofasesapp.composeapp.generated.resources.Res
import alimentandofasesapp.composeapp.generated.resources.logo_app1
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Notifications
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
import org.example.project.navigation.Screen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Content, Screen.Community, Screen.Profile)
    val showBottomBar = currentScreen in screens

    Scaffold(
        topBar = {
            if (showBottomBar) {
                val topBarColors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, // TopAppBar se mistura com o fundo
                    scrolledContainerColor = Color.Transparent,
                    titleContentColor = Color(0xFFE0E0E0),
                    actionIconContentColor = Color(0xFF38761d)
                )
                CenterAlignedTopAppBar(
                    title = {
                        when (currentScreen) {
                            is Screen.Home -> Image(
                                painter = painterResource(Res.drawable.logo_app1),
                                contentDescription = "Logo Alimentando Fases",
                                modifier = Modifier.height(32.dp)
                            )
                            else -> currentScreen.label?.let { Text(it, color = Color(0xFFE0E0E0)) }
                        }
                    },
                    actions = {
                        // ... (o código das Actions permanece o mesmo) ...
                        when (currentScreen) {
                            is Screen.Home -> {
                                IconButton(onClick = { /* TODO: Handle notifications click */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Notifications,
                                        contentDescription = "Notificações"
                                    )
                                }
                            }
                            is Screen.Content -> {
                                IconButton(onClick = { /* TODO: Handle search */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = "Pesquisar"
                                    )
                                }
                            }
                            is Screen.Community -> {
                                IconButton(onClick = { /* TODO: Handle create post */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = "Criar Post"
                                    )
                                }
                                IconButton(onClick = { /* TODO: Handle messages */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.ChatBubbleOutline,
                                        contentDescription = "Mensagens"
                                    )
                                }
                            }
                            is Screen.Profile -> {
                                IconButton(onClick = { /* TODO: Handle settings */ }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = "Configurações"
                                    )
                                }
                            }
                            else -> {}
                        }
                    },
                    colors = topBarColors,
                    // Adiciona padding para não desenhar atrás da Status Bar
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFF121412),
                    modifier = Modifier
                        .height(80.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars) // <<< MUDANÇA PRINCIPAL
                ) {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = screen.label!!) },
                            label = { Text(screen.label!!) },
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF38761d),
                                unselectedIconColor = Color.Gray,
                                selectedTextColor = Color(0xFF38761d),
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color(0xFF2E2E2E) // Indicador um pouco mais escuro
                            )
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFF121412),
        contentWindowInsets = WindowInsets(0,0,0,0), // <<< MUDANÇA PRINCIPAL
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                is Screen.Home -> HomeScreen()
                is Screen.Content -> ContentScreen()
                is Screen.Community -> CommunityScreen()
                is Screen.Profile -> ProfileScreen()
                else -> HomeScreen()
            }
        }
    }
}
