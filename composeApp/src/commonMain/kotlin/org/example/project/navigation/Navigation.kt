package org.example.project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String? = null, val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    data class VerifyEmail(val email: String) : Screen("verify_email")
    object CreatePassword : Screen("create_password")
    object RegistrationName : Screen("reg_name")
    object RegistrationBirthday : Screen("reg_birthday")
    object RegistrationProfilePhoto : Screen("reg_photo")
    object Main : Screen("main")

    // Telas da Bottom Nav
    object Home : Screen("home", "Início", Icons.Outlined.Home)
    object Content : Screen("content", "Conteúdo", Icons.Outlined.List)
    object Community : Screen("community", "Comunidade", Icons.Outlined.Person)
    object Recipes : Screen("recipes", "Receitas", Icons.Outlined.RestaurantMenu)
    object Profile : Screen("profile", "Perfil", Icons.Outlined.AccountCircle)
}
