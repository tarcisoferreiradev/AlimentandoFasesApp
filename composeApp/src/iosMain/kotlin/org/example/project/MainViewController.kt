package org.example.project

import androidx.compose.ui.window.ComposeUIViewController

/**
 * [MainViewController] é o ponto de entrada principal para a UI no iOS.
 * 
 * NOTA TÉCNICA DE PERFORMANCE (Skia/Skiko):
 * No iOS, o Compose Multiplatform utiliza a engine Skia (via Skiko) para renderização. 
 * Para garantir 60/120 FPS constantes:
 * 1. Evitamos o uso excessivo de camadas com 'Color.Transparent' sobrepostas, 
 *    o que causa 'overdraw' e sobrecarrega a GPU.
 * 2. Componentes complexos utilizam 'graphicsLayer' para cache de rasterização.
 * 3. O 'isStatic = true' no framework (build.gradle) complementa esta fluidez 
 *    ao reduzir o overhead de chamadas dinâmicas.
 */
fun MainViewController() = ComposeUIViewController { App() }
