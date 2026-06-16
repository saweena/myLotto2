package com.saweena.mylotto2.ui.home

data class HomeUiState(
    val lotteryNumber: String = "",
    val selectedDateText: String,
    val availableDateTexts: List<String>,
    val searchErrorText: String? = null,
    val latestPrizeGroups: List<HomePrizeGroup>,
)
