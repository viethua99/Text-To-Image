package com.vproject.texttoimage.feature.settings

import com.vproject.texttoimage.core.model.data.UserEditableSettings

internal sealed interface SettingsUiState {
    object Loading: SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}