package com.saweena.mylotto2.ui.saved

data class MyLotteryUiState(
    val savedLotteries: List<SavedLotteryUiModel>,
    val showDeleteDialog: Boolean = false,
)
