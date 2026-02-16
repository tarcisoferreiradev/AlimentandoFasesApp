package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.screens.recipes.CategoryTabs
import org.example.project.screens.recipes.LifeStageSection
import org.example.project.screens.recipes.MainBanner
import org.example.project.screens.recipes.PopularRecipesSection
import org.example.project.screens.recipes.QuickRecipesSection
import org.example.project.screens.recipes.SearchAndFilter

@Composable
fun RecipesScreen(onTitleChange: (String?) -> Unit) {
    LaunchedEffect(Unit) {
        onTitleChange("Receitas")
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFe6dfca))) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { SearchAndFilter() }
            item { MainBanner() }
            item { CategoryTabs() }
            item { PopularRecipesSection() }
            item { LifeStageSection() }
            item { QuickRecipesSection() }
        }
    }
}
