package com.saweena.mylotto2.model

sealed interface LotteryCheckStatus {
    data object Pending : LotteryCheckStatus
    data object NotWon : LotteryCheckStatus

    data class Won(
        val prizes: List<MatchedPrize>,
    ) : LotteryCheckStatus
}
