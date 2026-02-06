package org.example.project.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A central class to manage the global state of the application.
 *
 * This class holds the application's readiness state, which is crucial for
 * coordinating the transition from the native splash screen to the main UI.
 *
 * @property isReady A StateFlow that emits `true` when the application has completed
 * its initial loading and is ready to be displayed.
 */
class AppState {
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    /**
     * Signals that the application's initialization is complete.
     * This should be called after all essential data has been loaded or async
     * operations are done.
     */
    fun setReady() {
        _isReady.value = true
    }
}
