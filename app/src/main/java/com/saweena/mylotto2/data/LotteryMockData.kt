package com.saweena.mylotto2.data

import com.saweena.mylotto2.domain.LotteryChecker
import com.saweena.mylotto2.model.LotteryCheckStatus
import com.saweena.mylotto2.model.LotteryDraw
import com.saweena.mylotto2.model.SavedLotteryNumber

object LotteryMockData {
    val latestDraw = LotteryDraw(
        id = "2026-06-16",
        drawDateText = "16 มิถุนายน 2569",
        firstPrize = "123456",
        lastTwoDigits = "56",
        firstThreeDigits = listOf("123", "789"),
        lastThreeDigits = listOf("456", "999"),
        nearbyFirstPrize = listOf("123455", "123457"),
        secondPrize = listOf("234567", "345678", "456789"),
        thirdPrize = listOf("111111", "222222", "333333"),
        fourthPrize = listOf("444444", "555555", "666666"),
        fifthPrize = listOf("777777", "888888", "999999"),
    )

    val draws = listOf(latestDraw)

    val savedNumbers = listOf(
        SavedLotteryNumber(
            id = "saved-123456",
            number = "123456",
            drawDateText = latestDraw.drawDateText,
            status = LotteryChecker.check(
                lotteryNumber = "123456",
                draw = latestDraw,
            ),
        ),
        SavedLotteryNumber(
            id = "saved-045789",
            number = "045789",
            drawDateText = latestDraw.drawDateText,
            status = LotteryCheckStatus.Pending,
        ),
        SavedLotteryNumber(
            id = "saved-888888",
            number = "888888",
            drawDateText = latestDraw.drawDateText,
            status = LotteryCheckStatus.NotWon,
        ),
    )
}
