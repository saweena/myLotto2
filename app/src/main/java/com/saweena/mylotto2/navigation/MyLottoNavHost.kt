package com.saweena.mylotto2.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saweena.mylotto2.ui.home.HomeScreen
import com.saweena.mylotto2.ui.result.MatchedPrizeUiModel
import com.saweena.mylotto2.ui.result.PrizePriority
import com.saweena.mylotto2.ui.result.ResultCheckingScreen
import com.saweena.mylotto2.ui.result.ResultCheckingStatus
import com.saweena.mylotto2.ui.result.ResultCheckingUiState
import com.saweena.mylotto2.ui.saved.LotteryStatusUiModel
import com.saweena.mylotto2.ui.saved.MyLotteryScreen
import com.saweena.mylotto2.ui.saved.SavedLotteryUiModel

@Composable
fun MyLottoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    var lotteryNumber by rememberSaveable { mutableStateOf("") }
    var selectedDateText by rememberSaveable { mutableStateOf(mockDrawDates.first()) }

    NavHost(
        navController = navController,
        startDestination = MyLottoDestination.Home.route,
        modifier = modifier,
    ) {
        composable(MyLottoDestination.Home.route) {
            HomeScreen(
                lotteryNumber = lotteryNumber,
                onLotteryNumberChange = { lotteryNumber = it },
                onSearchClick = {
                    navController.navigate(
                        MyLottoDestination.ResultChecking.createRoute(
                            lotteryNumber = lotteryNumber,
                            drawDateText = selectedDateText,
                        ),
                    )
                },
                selectedDateText = selectedDateText,
                availableDateTexts = mockDrawDates,
                onDateSelected = { selectedDateText = it },
            )
        }

        composable(
            route = MyLottoDestination.ResultChecking.route,
            arguments = listOf(
                navArgument(MyLottoDestination.ResultChecking.LOTTERY_NUMBER_ARG) {
                    type = NavType.StringType
                },
                navArgument(MyLottoDestination.ResultChecking.DRAW_DATE_TEXT_ARG) {
                    type = NavType.StringType
                },
            ),
        ) { backStackEntry ->
            val checkedNumber = backStackEntry.arguments
                ?.getString(MyLottoDestination.ResultChecking.LOTTERY_NUMBER_ARG)
                ?.let(Uri::decode)
                .orEmpty()
            val drawDateText = backStackEntry.arguments
                ?.getString(MyLottoDestination.ResultChecking.DRAW_DATE_TEXT_ARG)
                ?.let(Uri::decode)
                .orEmpty()

            ResultCheckingScreen(
                uiState = createMockResultCheckingUiState(
                    lotteryNumber = checkedNumber,
                    drawDateText = drawDateText,
                ),
                onBackClick = { navController.popBackStack() },
                onSaveClick = {},
                onCheckAnotherClick = {
                    navController.popBackStack(
                        route = MyLottoDestination.Home.route,
                        inclusive = false,
                    )
                },
            )
        }

        composable(MyLottoDestination.MyLottery.route) {
            MyLotteryScreen(
                savedLotteries = mockSavedLotteries,
                showDeleteDialog = false,
                onBackClick = { navController.popBackStack() },
                onLotteryClick = { savedLottery ->
                    navController.navigate(
                        MyLottoDestination.ResultChecking.createRoute(
                            lotteryNumber = savedLottery.lotteryNumber,
                            drawDateText = savedLottery.drawDateText,
                        ),
                    )
                },
                onDeleteClick = {},
                onConfirmDelete = {},
                onDismissDelete = {},
            )
        }
    }
}

private fun createMockResultCheckingUiState(
    lotteryNumber: String,
    drawDateText: String,
): ResultCheckingUiState {
    val matchedPrizes = when (lotteryNumber) {
        "123456" -> listOf(
            MatchedPrizeUiModel(
                prizeName = "รางวัลที่ 1",
                prizeAmountText = "รางวัลละ 6,000,000 บาท",
                matchedNumber = lotteryNumber,
                priority = PrizePriority.FirstPrize,
            ),
            MatchedPrizeUiModel(
                prizeName = "เลขท้าย 2 ตัว",
                prizeAmountText = "รางวัลละ 2,000 บาท",
                matchedNumber = lotteryNumber.takeLast(2),
                priority = PrizePriority.LastTwoDigits,
            ),
        )

        else -> emptyList()
    }

    return ResultCheckingUiState(
        lotteryNumber = lotteryNumber,
        drawDateText = drawDateText,
        status = if (matchedPrizes.isEmpty()) {
            ResultCheckingStatus.NotWon
        } else {
            ResultCheckingStatus.Won
        },
        matchedPrizes = matchedPrizes,
    )
}

private val mockDrawDates = listOf(
    "16 มิถุนายน 2569",
    "1 มิถุนายน 2569",
    "16 พฤษภาคม 2569",
)

private val mockSavedLotteries = listOf(
    SavedLotteryUiModel(
        id = "1",
        lotteryNumber = "123456",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.Won,
        prizeSummaryText = "รางวัลที่ 1",
    ),
    SavedLotteryUiModel(
        id = "2",
        lotteryNumber = "045789",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.Pending,
    ),
)
