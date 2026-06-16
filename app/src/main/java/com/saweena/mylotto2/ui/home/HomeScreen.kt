package com.saweena.mylotto2.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saweena.mylotto2.ui.component.DrawDateSelector
import com.saweena.mylotto2.ui.component.LotterySearchBar
import com.saweena.mylotto2.ui.component.PrizeCard
import com.saweena.mylotto2.ui.theme.MyLotto2Theme

@Composable
fun HomeScreen(
    lotteryNumber: String,
    onLotteryNumberChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    selectedDateText: String,
    availableDateTexts: List<String>,
    onDateSelected: (String) -> Unit,
    onMyLotteryClick: () -> Unit,
    modifier: Modifier = Modifier,
    searchErrorText: String? = null,
    latestPrizeGroups: List<HomePrizeGroup> = homePreviewPrizeGroups,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            HomeHeaderSection(
                lotteryNumber = lotteryNumber,
                onLotteryNumberChange = onLotteryNumberChange,
                onSearchClick = onSearchClick,
                selectedDateText = selectedDateText,
                availableDateTexts = availableDateTexts,
                onDateSelected = onDateSelected,
                searchErrorText = searchErrorText,
            )
        }

        item {
            LatestResultSection(
                selectedDateText = selectedDateText,
                prizeGroups = latestPrizeGroups,
            )
        }
    }
}

@Composable
private fun HomeHeaderSection(
    lotteryNumber: String,
    onLotteryNumberChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    selectedDateText: String,
    availableDateTexts: List<String>,
    onDateSelected: (String) -> Unit,
    searchErrorText: String?,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "MyLotto",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            )

            Text(
                text = "ตรวจผลสลากกินแบ่งรัฐบาลแบบเรียบง่ายและเชื่อถือได้",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.92f),
            )

            LotterySearchBar(
                value = lotteryNumber,
                onValueChange = onLotteryNumberChange,
                onSearchClick = onSearchClick,
                errorText = searchErrorText,
            )

            DrawDateSelector(
                selectedDateText = selectedDateText,
                availableDateTexts = availableDateTexts,
                onDateSelected = onDateSelected,
            )
        }
    }
}

@Composable
private fun LatestResultSection(
    selectedDateText: String,
    prizeGroups: List<HomePrizeGroup>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "ผลสลากกินแบ่งรัฐบาล",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Text(
            text = selectedDateText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        for (prizeGroup in prizeGroups) {
            PrizeCard(
                title = prizeGroup.title,
                prizeAmountText = prizeGroup.prizeAmountText,
                numbers = prizeGroup.numbers,
                emphasized = prizeGroup.emphasized,
            )
        }
    }
}

data class HomePrizeGroup(
    val title: String,
    val prizeAmountText: String?,
    val numbers: List<String>,
    val emphasized: Boolean,
)

private val homePreviewPrizeGroups = listOf(
    HomePrizeGroup(
        title = "รางวัลที่ 1",
        prizeAmountText = "รางวัลละ 6,000,000 บาท",
        numbers = listOf("123456"),
        emphasized = true,
    ),
    HomePrizeGroup(
        title = "เลขท้าย 2 ตัว",
        prizeAmountText = null,
        numbers = listOf("56"),
        emphasized = false,
    ),
    HomePrizeGroup(
        title = "เลขหน้า 3 ตัว",
        prizeAmountText = "รางวัลละ 4,000 บาท",
        numbers = listOf("123", "789"),
        emphasized = false,
    ),
    HomePrizeGroup(
        title = "เลขท้าย 3 ตัว",
        prizeAmountText = "รางวัลละ 4,000 บาท",
        numbers = listOf("456", "999"),
        emphasized = false,
    ),
    HomePrizeGroup(
        title = "เลขข้างเคียงรางวัลที่ 1",
        prizeAmountText = "รางวัลละ 100,000 บาท",
        numbers = listOf("123455", "123457"),
        emphasized = false,
    ),
)

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun HomeScreenPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(
                lotteryNumber = "123456",
                onLotteryNumberChange = {},
                onSearchClick = {},
                selectedDateText = "16 มิถุนายน 2569",
                availableDateTexts = listOf(
                    "1 มิถุนายน 2569",
                    "16 มิถุนายน 2569",
                    "1 กรกฎาคม 2569",
                ),
                onDateSelected = {},
                onMyLotteryClick = {},
            )
        }
    }
}
