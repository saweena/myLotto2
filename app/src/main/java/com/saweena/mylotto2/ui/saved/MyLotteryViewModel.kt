package com.saweena.mylotto2.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saweena.mylotto2.data.LotteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyLotteryViewModel : ViewModel() {
    private var pendingDeleteSavedLotteryId: String? = null

    private val _uiState = MutableStateFlow(
        MyLotteryUiState(
            savedLotteries = LotteryRepository.getSavedNumbers().toSavedLotteryUiModels(),
        ),
    )
    val uiState: StateFlow<MyLotteryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            LotteryRepository.savedNumbers.collect { savedNumbers ->
                _uiState.update {
                    it.copy(savedLotteries = savedNumbers.toSavedLotteryUiModels())
                }
            }
        }
    }

    fun onDeleteClick(savedLottery: SavedLotteryUiModel) {
        pendingDeleteSavedLotteryId = savedLottery.id
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun onConfirmDelete() {
        pendingDeleteSavedLotteryId?.let(LotteryRepository::deleteSavedNumber)
        pendingDeleteSavedLotteryId = null
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun onDismissDelete() {
        pendingDeleteSavedLotteryId = null
        _uiState.update { it.copy(showDeleteDialog = false) }
    }
}
