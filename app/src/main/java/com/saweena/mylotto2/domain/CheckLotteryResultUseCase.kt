package com.saweena.mylotto2.domain

import com.saweena.mylotto2.model.LotteryCheckStatus
import com.saweena.mylotto2.model.LotteryDraw
import com.saweena.mylotto2.model.MatchedPrize
import com.saweena.mylotto2.model.PrizePriority

object CheckLotteryResultUseCase {
    fun check(
        lotteryNumber: String,
        draw: LotteryDraw,
    ): LotteryCheckStatus {
        val normalizedNumber = lotteryNumber.trim()
        val matchedPrizes = buildList {
            addIfMatched(
                condition = normalizedNumber == draw.firstPrize,
                prizeName = "รางวัลที่ 1",
                prizeAmountText = "รางวัลละ 6,000,000 บาท",
                matchedNumber = draw.firstPrize,
                priority = PrizePriority.FirstPrize,
            )
            addIfMatched(
                condition = normalizedNumber in draw.nearbyFirstPrize,
                prizeName = "เลขข้างเคียงรางวัลที่ 1",
                prizeAmountText = "รางวัลละ 100,000 บาท",
                matchedNumber = normalizedNumber,
                priority = PrizePriority.NearbyFirstPrize,
            )
            addPrizeListMatches(
                lotteryNumber = normalizedNumber,
                prizeNumbers = draw.secondPrize,
                prizeName = "รางวัลที่ 2",
                prizeAmountText = "รางวัลละ 200,000 บาท",
                priority = PrizePriority.SecondPrize,
            )
            addPrizeListMatches(
                lotteryNumber = normalizedNumber,
                prizeNumbers = draw.thirdPrize,
                prizeName = "รางวัลที่ 3",
                prizeAmountText = "รางวัลละ 80,000 บาท",
                priority = PrizePriority.ThirdPrize,
            )
            addPrizeListMatches(
                lotteryNumber = normalizedNumber,
                prizeNumbers = draw.fourthPrize,
                prizeName = "รางวัลที่ 4",
                prizeAmountText = "รางวัลละ 40,000 บาท",
                priority = PrizePriority.FourthPrize,
            )
            addPrizeListMatches(
                lotteryNumber = normalizedNumber,
                prizeNumbers = draw.fifthPrize,
                prizeName = "รางวัลที่ 5",
                prizeAmountText = "รางวัลละ 20,000 บาท",
                priority = PrizePriority.FifthPrize,
            )
            addIfMatched(
                condition = normalizedNumber.take(3) in draw.firstThreeDigits,
                prizeName = "เลขหน้า 3 ตัว",
                prizeAmountText = "รางวัลละ 4,000 บาท",
                matchedNumber = normalizedNumber.take(3),
                priority = PrizePriority.FirstThreeDigits,
            )
            addIfMatched(
                condition = normalizedNumber.takeLast(3) in draw.lastThreeDigits,
                prizeName = "เลขท้าย 3 ตัว",
                prizeAmountText = "รางวัลละ 4,000 บาท",
                matchedNumber = normalizedNumber.takeLast(3),
                priority = PrizePriority.LastThreeDigits,
            )
            addIfMatched(
                condition = normalizedNumber.takeLast(2) == draw.lastTwoDigits,
                prizeName = "เลขท้าย 2 ตัว",
                prizeAmountText = "รางวัลละ 2,000 บาท",
                matchedNumber = draw.lastTwoDigits,
                priority = PrizePriority.LastTwoDigits,
            )
        }.sortedBy { it.priority }

        return if (matchedPrizes.isEmpty()) {
            LotteryCheckStatus.NotWon
        } else {
            LotteryCheckStatus.Won(matchedPrizes)
        }
    }

    private fun MutableList<MatchedPrize>.addIfMatched(
        condition: Boolean,
        prizeName: String,
        prizeAmountText: String,
        matchedNumber: String,
        priority: PrizePriority,
    ) {
        if (condition) {
            add(
                MatchedPrize(
                    prizeName = prizeName,
                    prizeAmountText = prizeAmountText,
                    matchedNumber = matchedNumber,
                    priority = priority,
                ),
            )
        }
    }

    private fun MutableList<MatchedPrize>.addPrizeListMatches(
        lotteryNumber: String,
        prizeNumbers: List<String>,
        prizeName: String,
        prizeAmountText: String,
        priority: PrizePriority,
    ) {
        if (lotteryNumber in prizeNumbers) {
            add(
                MatchedPrize(
                    prizeName = prizeName,
                    prizeAmountText = prizeAmountText,
                    matchedNumber = lotteryNumber,
                    priority = priority,
                ),
            )
        }
    }
}
