package com.saweena.mylotto2.model

data class LotteryDraw(
    val id: String,
    val drawDateText: String,
    val firstPrize: String,
    val lastTwoDigits: String,
    val firstThreeDigits: List<String>,
    val lastThreeDigits: List<String>,
    val nearbyFirstPrize: List<String>,
    val secondPrize: List<String>,
    val thirdPrize: List<String>,
    val fourthPrize: List<String>,
    val fifthPrize: List<String>,
)
