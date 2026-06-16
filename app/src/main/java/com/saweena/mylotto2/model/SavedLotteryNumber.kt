package com.saweena.mylotto2.model

data class SavedLotteryNumber(
    val id: String,
    val number: String,
    val drawDateText: String,
    val status: LotteryCheckStatus,
)
