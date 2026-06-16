package com.saweena.mylotto2.ui.result

import androidx.lifecycle.ViewModel
import com.saweena.mylotto2.data.LotteryRepository
import com.saweena.mylotto2.domain.LotteryChecker
import com.saweena.mylotto2.model.SavedLotteryNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultCheckingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ResultCheckingUiState?>(null)
    val uiState: StateFlow<ResultCheckingUiState?> = _uiState.asStateFlow()

    private var currentLotteryNumber: String = ""
    private var currentDrawDateText: String = ""

    fun loadResult(
        lotteryNumber: String,
        drawDateText: String,
    ) {
        currentLotteryNumber = lotteryNumber
        currentDrawDateText = drawDateText
        _uiState.value = createResultUiState(
            lotteryNumber = lotteryNumber,
            drawDateText = drawDateText,
        )
    }

    fun saveCheckedNumber() {
        val draw = LotteryRepository.getDrawByDateText(currentDrawDateText)
            ?: LotteryRepository.getLatestDraw()
        if (LotteryRepository.isSaved(currentLotteryNumber, draw.drawDateText)) {
            _uiState.value = createResultUiState(
                lotteryNumber = currentLotteryNumber,
                drawDateText = draw.drawDateText,
            )
            return
        }

        LotteryRepository.saveNumber(
            SavedLotteryNumber(
                id = currentLotteryNumber.toSavedLotteryId(draw.drawDateText),
                number = currentLotteryNumber,
                drawDateText = draw.drawDateText,
                status = LotteryChecker.check(
                    lotteryNumber = currentLotteryNumber,
                    draw = draw,
                ),
            ),
        )
        _uiState.value = createResultUiState(
            lotteryNumber = currentLotteryNumber,
            drawDateText = draw.drawDateText,
        )
    }

    private fun createResultUiState(
        lotteryNumber: String,
        drawDateText: String,
    ): ResultCheckingUiState {
        val draw = LotteryRepository.getDrawByDateText(drawDateText)
            ?: LotteryRepository.getLatestDraw()
        val checkStatus = LotteryChecker.check(
            lotteryNumber = lotteryNumber,
            draw = draw,
        )
        val isAlreadySaved = LotteryRepository.isSaved(
            lotteryNumber = lotteryNumber,
            drawDateText = draw.drawDateText,
        )

        return checkStatus.toResultCheckingUiState(
            lotteryNumber = lotteryNumber,
            drawDateText = draw.drawDateText,
            isAlreadySaved = isAlreadySaved,
        )
    }
}
