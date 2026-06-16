package com.saweena.mylotto2.ui.home

import androidx.lifecycle.ViewModel
import com.saweena.mylotto2.data.LotteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val latestDraw = LotteryRepository.getLatestDraw()

    private val _uiState = MutableStateFlow(
        HomeUiState(
            selectedDateText = latestDraw.drawDateText,
            availableDateTexts = LotteryRepository.getDraws().map { it.drawDateText },
            latestPrizeGroups = latestDraw.toHomePrizeGroups(),
        ),
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onLotteryNumberChange(value: String) {
        _uiState.update {
            it.copy(
                lotteryNumber = value,
                searchErrorText = null,
            )
        }
    }

    fun onDateSelected(drawDateText: String) {
        val draw = LotteryRepository.getDrawByDateText(drawDateText) ?: latestDraw
        _uiState.update {
            it.copy(
                selectedDateText = draw.drawDateText,
                searchErrorText = null,
                latestPrizeGroups = draw.toHomePrizeGroups(),
            )
        }
    }

    fun validateSearch(): Boolean {
        val lotteryNumber = _uiState.value.lotteryNumber
        return if (lotteryNumber.length == 6) {
            _uiState.update { it.copy(searchErrorText = null) }
            true
        } else {
            _uiState.update { it.copy(searchErrorText = lotteryNumber.toSearchErrorText()) }
            false
        }
    }
}
