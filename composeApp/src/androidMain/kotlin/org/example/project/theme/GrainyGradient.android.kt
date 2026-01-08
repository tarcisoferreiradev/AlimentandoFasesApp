package org.example.project.theme

import android.graphics.RuntimeShader
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.platform.LocalInspectionMode

private const val AGSL_SHADER_CODE = """    uniform half4 startColor;
    uniform half4 endColor;
    uniform float2 iResolution;
    uniform float grain_intensity;

    // Gerador pseudo-aleatório de https://thebookofshaders.com/10/
    float random(vec2 st) {
        return fract(sin(dot(st.xy, vec2(12.9898,78.233))) * 43758.5453123);
    }

    half4 main(vec2 fragCoord) {
        // Gradiente diagonal simples
        float2 uv = fragCoord.xy / iResolution.xy;
        float progress = (uv.x + uv.y) * 0.5;

        // Mistura as cores com base no progresso
        half4 gradientColor = mix(startColor, endColor, progress);

        // Gera o ruído
        float noise = (random(fragCoord / iResolution.xy) - 0.5) * grain_intensity;

        // Adiciona o ruído
        return gradientColor + noise;
    }
"""

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class GrainyGradientBrush(
    private val startColor: Color,
    private val endColor: Color,
    private val grainIntensity: Float
) : ShaderBrush() {

    private val shader = RuntimeShader(AGSL_SHADER_CODE)

    override fun createShader(size: Size): Shader {
        shader.setFloatUniform("iResolution", size.width, size.height)
        shader.setFloatUniform("grain_intensity", grainIntensity)
        shader.setFloatUniform("startColor", startColor.red, startColor.green, startColor.blue, startColor.alpha)
        shader.setFloatUniform("endColor", endColor.red, endColor.green, endColor.blue, endColor.alpha)

        return shader
    }
}

@Composable
actual fun grainyGradientBrush(
    startColor: Color,
    endColor: Color,
    grainIntensity: Float
): Brush {
    val isInPreview = LocalInspectionMode.current
    return if (!isInPreview && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        remember {
            GrainyGradientBrush(
                startColor = startColor,
                endColor = endColor,
                grainIntensity = grainIntensity
            )
        }
    } else {
        remember {
            Brush.linearGradient(
                colors = listOf(
                    startColor,
                    endColor
                )
            )
        }
    }
}
