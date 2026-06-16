package com.saweena.mylotto2.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saweena.mylotto2.ui.home.HomeScreen
import com.saweena.mylotto2.ui.home.HomeViewModel
import com.saweena.mylotto2.ui.result.ResultCheckingScreen
import com.saweena.mylotto2.ui.result.ResultCheckingUiState
import com.saweena.mylotto2.ui.result.ResultCheckingViewModel
import com.saweena.mylotto2.ui.saved.MyLotteryScreen
import com.saweena.mylotto2.ui.saved.MyLotteryViewModel

@Composable
fun MyLottoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MyLottoDestination.Home.route,
        modifier = modifier,
    ) {
        composable(MyLottoDestination.Home.route) {
            val homeViewModel: HomeViewModel = viewModel()
            val uiState by homeViewModel.uiState.collectAsState()

            HomeScreen(
                lotteryNumber = uiState.lotteryNumber,
                onLotteryNumberChange = homeViewModel::onLotteryNumberChange,
                onSearchClick = {
                    if (homeViewModel.validateSearch()) {
                        navController.navigate(
                            MyLottoDestination.ResultChecking.createRoute(
                                lotteryNumber = uiState.lotteryNumber,
                                drawDateText = uiState.selectedDateText,
                            ),
                        )
                    }
                },
                selectedDateText = uiState.selectedDateText,
                availableDateTexts = uiState.availableDateTexts,
                onDateSelected = homeViewModel::onDateSelected,
                onMyLotteryClick = {
                    navController.navigate(MyLottoDestination.MyLottery.route)
                },
                searchErrorText = uiState.searchErrorText,
                latestPrizeGroups = uiState.latestPrizeGroups,
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
            val resultViewModel: ResultCheckingViewModel = viewModel()
            val uiState by resultViewModel.uiState.collectAsState()
            val checkedNumber = backStackEntry.arguments
                ?.getString(MyLottoDestination.ResultChecking.LOTTERY_NUMBER_ARG)
                ?.let(Uri::decode)
                .orEmpty()
            val drawDateText = backStackEntry.arguments
                ?.getString(MyLottoDestination.ResultChecking.DRAW_DATE_TEXT_ARG)
                ?.let(Uri::decode)
                .orEmpty()

            LaunchedEffect(checkedNumber, drawDateText) {
                resultViewModel.loadResult(
                    lotteryNumber = checkedNumber,
                    drawDateText = drawDateText,
                )
            }

            uiState?.let { resultUiState: ResultCheckingUiState ->
                ResultCheckingScreen(
                    uiState = resultUiState,
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = resultViewModel::saveCheckedNumber,
                    onCheckAnotherClick = {
                        navController.popBackStack(
                            route = MyLottoDestination.Home.route,
                            inclusive = false,
                        )
                    },
                )
            }
        }

        composable(MyLottoDestination.MyLottery.route) {
            val myLotteryViewModel: MyLotteryViewModel = viewModel()
            val uiState by myLotteryViewModel.uiState.collectAsState()

            MyLotteryScreen(
                savedLotteries = uiState.savedLotteries,
                showDeleteDialog = uiState.showDeleteDialog,
                onBackClick = { navController.popBackStack() },
                onLotteryClick = { savedLottery ->
                    navController.navigate(
                        MyLottoDestination.ResultChecking.createRoute(
                            lotteryNumber = savedLottery.lotteryNumber,
                            drawDateText = savedLottery.drawDateText,
                        ),
                    )
                },
                onDeleteClick = myLotteryViewModel::onDeleteClick,
                onConfirmDelete = myLotteryViewModel::onConfirmDelete,
                onDismissDelete = myLotteryViewModel::onDismissDelete,
            )
        }
    }
}
