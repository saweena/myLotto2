package com.saweena.mylotto2.model

data class MatchedPrize(
    val prizeName: String,
    val prizeAmountText: String,
    val matchedNumber: String,
    val priority: PrizePriority,
)

enum class PrizePriority {
    FirstPrize,
    NearbyFirstPrize,
    SecondPrize,
    ThirdPrize,
    FourthPrize,
    FifthPrize,
    FirstThreeDigits,
    LastThreeDigits,
    LastTwoDigits,
}
