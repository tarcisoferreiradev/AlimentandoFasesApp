package org.example.project.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel to coordinate the native splash screen transition.
 *
 * ARCHITECTURAL DECISION:
 * This ViewModel's sole responsibility is to bridge the gap between the app's cold start
 * and the point where the Compose UI is ready to take over. It emits a single `isReady`
 * signal as quickly as possible.
 *
 * The artificial `delay` was removed as it violated the principle of a fast, responsive UI.
 * The native splash screen should only be displayed for the absolute minimum time required
 * for the app process to initialize.
 */
class SplashViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        // Signal that the app's basic initialization is complete, allowing the native
        // splash to be dismissed and the Compose UI to take over.
        viewModelScope.launch {
            _isReady.value = true
        }
    }
}
