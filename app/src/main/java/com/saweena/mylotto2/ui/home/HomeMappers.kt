package com.saweena.mylotto2.ui.home

import com.saweena.mylotto2.model.LotteryDraw

fun LotteryDraw.toHomePrizeGroups(): List<HomePrizeGroup> {
    return listOf(
        HomePrizeGroup(
            title = "รางวัลที่ 1",
            prizeAmountText = "รางวัลละ 6,000,000 บาท",
            numbers = listOf(firstPrize),
            emphasized = true,
        ),
        HomePrizeGroup(
            title = "เลขท้าย 2 ตัว",
            prizeAmountText = "รางวัลละ 2,000 บาท",
            numbers = listOf(lastTwoDigits),
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "เลขหน้า 3 ตัว",
            prizeAmountText = "รางวัลละ 4,000 บาท",
            numbers = firstThreeDigits,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "เลขท้าย 3 ตัว",
            prizeAmountText = "รางวัลละ 4,000 บาท",
            numbers = lastThreeDigits,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "เลขข้างเคียงรางวัลที่ 1",
            prizeAmountText = "รางวัลละ 100,000 บาท",
            numbers = nearbyFirstPrize,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "รางวัลที่ 2",
            prizeAmountText = "รางวัลละ 200,000 บาท",
            numbers = secondPrize,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "รางวัลที่ 3",
            prizeAmountText = "รางวัลละ 80,000 บาท",
            numbers = thirdPrize,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "รางวัลที่ 4",
            prizeAmountText = "รางวัลละ 40,000 บาท",
            numbers = fourthPrize,
            emphasized = false,
        ),
        HomePrizeGroup(
            title = "รางวัลที่ 5",
            prizeAmountText = "รางวัลละ 20,000 บาท",
            numbers = fifthPrize,
            emphasized = false,
        ),
    )
}

fun String.toSearchErrorText(): String {
    return when {
        isBlank() -> "กรุณากรอกเลขสลาก"
        length < 6 -> "กรุณากรอกเลขให้ครบ 6 หลัก"
        else -> "กรุณากรอกเลขให้ครบ 6 หลัก"
    }
}
