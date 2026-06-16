package com.saweena.mylotto2.navigation

import android.net.Uri

sealed interface MyLottoDestination {
    val route: String

    data object Home : MyLottoDestination {
        override val route = "home"
    }

    data object ResultChecking : MyLottoDestination {
        const val LOTTERY_NUMBER_ARG = "lotteryNumber"
        const val DRAW_DATE_TEXT_ARG = "drawDateText"

        override val route = "result_checking/{$LOTTERY_NUMBER_ARG}/{$DRAW_DATE_TEXT_ARG}"

        fun createRoute(
            lotteryNumber: String,
            drawDateText: String,
        ): String {
            return "result_checking/${Uri.encode(lotteryNumber)}/${Uri.encode(drawDateText)}"
        }
    }

    data object MyLottery : MyLottoDestination {
        override val route = "my_lottery"
    }
}
