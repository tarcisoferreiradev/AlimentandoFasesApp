package org.example.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.components.PremiumLoadingButton
import org.example.project.theme.AppSpacing
import org.example.project.theme.grainyGradientBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationBirthdayScreen(onContinueClicked: () -> Unit, onBackClicked: () -> Unit) {
    var birthday by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val premiumShadow = Modifier.shadow(elevation = 10.dp, ambientColor = Color.Black.copy(alpha = 0.06f), spotColor = Color.Black.copy(alpha = 0.06f), shape = RoundedCornerShape(50))

    val backgroundBrush = grainyGradientBrush(
        startColor = Color(0xFFF0F4E8),
        endColor = Color(0xFFC7DAA3),
        grainIntensity = 0.02f
    )

    fun handleContinue() {
        isLoading = true
        scope.launch {
            delay(3000) // Simulate network call
            isLoading = false
            onContinueClicked()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Scaffold(
            topBar = {
                RegistrationAppBar(step = 2, totalSteps = 3, onBackClicked = onBackClicked)
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1f))

                Text(
                    text = "Quando você nasceu?",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF5d8c4a)
                )
                Spacer(Modifier.height(AppSpacing.medium))
                Text(
                    text = "Isso nos ajuda a personalizar sua experiência.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = AppSpacing.medium)
                )
                Spacer(Modifier.height(AppSpacing.extraLarge))
                OutlinedTextField(
                    value = birthday,
                    onValueChange = { birthday = it },
                    label = { Text("Data de Nascimento") },
                    leadingIcon = { Icon(Icons.Filled.CalendarToday, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(premiumShadow),
                    shape = RoundedCornerShape(AppSpacing.medium),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    singleLine = true
                )
                Spacer(Modifier.height(AppSpacing.large))
                PremiumLoadingButton(
                    isLoading = isLoading,
                    text = "CONTINUAR",
                    onClick = { handleContinue() },
                    enabled = birthday.isNotBlank(),
                    modifier = premiumShadow
                )
                Spacer(Modifier.weight(2f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationAppBar(
    step: Int,
    totalSteps: Int,
    onBackClicked: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Passo $step de $totalSteps",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color(0xFF5d8c4a))
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..totalSteps) {
                val color = if (i <= step) Color(0xFF5d8c4a) else MaterialTheme.colorScheme.surfaceVariant
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .background(color, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    }
}
