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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
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
import org.example.project.ui.theme.SystemAppearance
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Como as barras de navegação agora têm um fundo claro, garantimos que
    // os ícones da barra de status do sistema sejam escuros para contraste.
    SystemAppearance(isLight = true)

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Content, Screen.Community, Screen.Profile)
    val showBottomBar = currentScreen in screens

    Scaffold(
        topBar = {
            if (showBottomBar) {
                val topBarColors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFf9efd4),      // Nova cor de fundo
                    scrolledContainerColor = Color(0xFFf9efd4),
                    titleContentColor = Color(0xFF333333),      // Conteúdo escuro para contraste
                    actionIconContentColor = Color(0xFF333333)
                )
                CenterAlignedTopAppBar(
                    title = {
                        when (currentScreen) {
                            is Screen.Home -> Image(
                                painter = painterResource(Res.drawable.logo_app1),
                                contentDescription = "Logo Alimentando Fases",
                                modifier = Modifier.height(32.dp)
                            )
                            else -> currentScreen.label?.let { Text(it, color = Color(0xFF333333)) }
                        }
                    },
                    actions = {
                        when (currentScreen) {
                            is Screen.Home -> {
                                IconButton(onClick = { /* TODO */ }) {
                                    Icon(Icons.Outlined.Notifications, "Notificações")
                                }
                            }
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
                    modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFFf9efd4), // Nova cor de fundo
                    modifier = Modifier.height(80.dp).windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = screen.label!!) },
                            label = { Text(screen.label!!) },
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF5d8c4a),      // Verde para ícone selecionado
                                unselectedIconColor = Color(0xFF757575),  // Cinza escuro para ícones não selecionados
                                selectedTextColor = Color(0xFF5d8c4a),      // Verde para texto selecionado
                                unselectedTextColor = Color(0xFF757575),  // Cinza escuro para texto não selecionado
                                indicatorColor = Color(0xFFE0E0E0)     // Cor de fundo do indicador
                            )
                        )
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
