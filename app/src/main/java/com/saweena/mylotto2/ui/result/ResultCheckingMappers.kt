package com.saweena.mylotto2.ui.result

import com.saweena.mylotto2.model.LotteryCheckStatus
import com.saweena.mylotto2.model.MatchedPrize
import com.saweena.mylotto2.ui.result.PrizePriority as ResultPrizePriority

fun LotteryCheckStatus.toResultCheckingUiState(
    lotteryNumber: String,
    drawDateText: String,
    isAlreadySaved: Boolean,
): ResultCheckingUiState {
    val matchedPrizes = when (this) {
        is LotteryCheckStatus.Won -> prizes.map { it.toMatchedPrizeUiModel() }
        LotteryCheckStatus.NotWon,
        LotteryCheckStatus.Pending,
        -> emptyList()
    }
    return ResultCheckingUiState(
        lotteryNumber = lotteryNumber,
        drawDateText = drawDateText,
        status = if (this is LotteryCheckStatus.Won) {
            ResultCheckingStatus.Won
        } else {
            ResultCheckingStatus.NotWon
        },
        matchedPrizes = matchedPrizes,
        canSave = !isAlreadySaved,
        saveButtonText = if (isAlreadySaved) "บันทึกไว้แล้ว" else "บันทึกเลขนี้",
    )
}

fun String.toSavedLotteryId(drawDateText: String): String {
    return "saved-$this-${drawDateText.hashCode()}"
}

private fun MatchedPrize.toMatchedPrizeUiModel(): MatchedPrizeUiModel {
    return MatchedPrizeUiModel(
        prizeName = prizeName,
        prizeAmountText = prizeAmountText,
        matchedNumber = matchedNumber,
        priority = ResultPrizePriority.valueOf(priority.name),
    )
}
