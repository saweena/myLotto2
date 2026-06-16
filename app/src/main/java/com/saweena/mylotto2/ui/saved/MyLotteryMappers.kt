package com.saweena.mylotto2.ui.saved

import com.saweena.mylotto2.model.LotteryCheckStatus
import com.saweena.mylotto2.model.SavedLotteryNumber

fun List<SavedLotteryNumber>.toSavedLotteryUiModels(): List<SavedLotteryUiModel> {
    return map { it.toSavedLotteryUiModel() }
}

private fun SavedLotteryNumber.toSavedLotteryUiModel(): SavedLotteryUiModel {
    return SavedLotteryUiModel(
        id = id,
        lotteryNumber = number,
        drawDateText = drawDateText,
        status = when (status) {
            LotteryCheckStatus.Pending -> LotteryStatusUiModel.Pending
            LotteryCheckStatus.NotWon -> LotteryStatusUiModel.NotWon
            is LotteryCheckStatus.Won -> LotteryStatusUiModel.Won
        },
        prizeSummaryText = (status as? LotteryCheckStatus.Won)
            ?.prizes
            ?.firstOrNull()
            ?.prizeName,
    )
}
