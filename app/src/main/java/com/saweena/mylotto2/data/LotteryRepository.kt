package com.saweena.mylotto2.data

import com.saweena.mylotto2.model.LotteryDraw
import com.saweena.mylotto2.model.SavedLotteryNumber

class LotteryRepository(
    private val draws: List<LotteryDraw> = LotteryMockData.draws,
    private val savedNumbers: List<SavedLotteryNumber> = LotteryMockData.savedNumbers,
) {
    fun getDraws(): List<LotteryDraw> = draws

    fun getLatestDraw(): LotteryDraw = draws.first()

    fun getDrawByDateText(drawDateText: String): LotteryDraw? {
        return draws.firstOrNull { it.drawDateText == drawDateText }
    }

    fun getSavedNumbers(): List<SavedLotteryNumber> = savedNumbers
}
