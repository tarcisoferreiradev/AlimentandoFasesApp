package org.example.project.domain.model

import kotlin.jvm.JvmInline

data class WaterIntakeParams(val weight: Int, val age: Int)

// Usar um value class para tipagem forte e evitar "Primitive Obsession".
@JvmInline
value class WaterIntakeResult(val amountInMl: Int)
