package com.saweena.mylotto2.data

import com.saweena.mylotto2.model.LotteryDraw
import com.saweena.mylotto2.model.SavedLotteryNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LotteryRepository {
    private val draws: List<LotteryDraw> = LotteryMockData.draws
    private val _savedNumbers = MutableStateFlow(LotteryMockData.savedNumbers)
    val savedNumbers: StateFlow<List<SavedLotteryNumber>> = _savedNumbers.asStateFlow()

    fun getDraws(): List<LotteryDraw> = draws

    fun getLatestDraw(): LotteryDraw = draws.first()

    fun getDrawByDateText(drawDateText: String): LotteryDraw? {
        return draws.firstOrNull { it.drawDateText == drawDateText }
    }

    fun getSavedNumbers(): List<SavedLotteryNumber> = savedNumbers.value

    fun saveNumber(savedLotteryNumber: SavedLotteryNumber) {
        val isDuplicate = _savedNumbers.value.any {
            it.number == savedLotteryNumber.number &&
                it.drawDateText == savedLotteryNumber.drawDateText
        }
        if (!isDuplicate) {
            _savedNumbers.value = _savedNumbers.value + savedLotteryNumber
        }
    }

    fun deleteSavedNumber(id: String) {
        _savedNumbers.value = _savedNumbers.value.filterNot { it.id == id }
    }

    fun isSaved(
        lotteryNumber: String,
        drawDateText: String,
    ): Boolean {
        return _savedNumbers.value.any {
            it.number == lotteryNumber && it.drawDateText == drawDateText
        }
    }
}
