package com.saweena.mylotto2.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            HomeHeaderSection(
                lotteryNumber = lotteryNumber,
                onLotteryNumberChange = onLotteryNumberChange,
                onSearchClick = onSearchClick,
                selectedDateText = selectedDateText,
                availableDateTexts = availableDateTexts,
                onDateSelected = onDateSelected,
                onMyLotteryClick = onMyLotteryClick,
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
    onMyLotteryClick: () -> Unit,
    searchErrorText: String?,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(
            bottomStart = 30.dp,
            bottomEnd = 30.dp,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                start = 15.dp,
                top = 10.dp,
                end = 15.dp,
                bottom = 12.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LotterySearchBar(
                    value = lotteryNumber,
                    onValueChange = onLotteryNumberChange,
                    onSearchClick = onSearchClick,
                    modifier = Modifier.weight(1f),
                    errorText = searchErrorText,
                )

                Surface(
                    modifier = Modifier.size(45.dp),
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Text(
                        text = "▦",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    modifier = Modifier
                        .size(width = 88.dp, height = 66.dp)
                        .clickable(onClick = onMyLotteryClick),
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "ตรวจ\nหวย\nรวยๆ",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp,
                                lineHeight = 17.sp,
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                DrawDateSelector(
                    selectedDateText = selectedDateText,
                    availableDateTexts = availableDateTexts,
                    onDateSelected = onDateSelected,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun LatestResultSection(
    selectedDateText: String,
    prizeGroups: List<HomePrizeGroup>,
) {
    val firstThreeDigits = prizeGroups.firstOrNull { it.title == "เลขหน้า 3 ตัว" }
    val lastThreeDigits = prizeGroups.firstOrNull { it.title == "เลขท้าย 3 ตัว" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "ผลสลากกินแบ่งรัฐบาล",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "งวดวันที่ $selectedDateText",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        for (prizeGroup in prizeGroups) {
            if (prizeGroup.title == "เลขท้าย 3 ตัว") continue
            if (prizeGroup.title == "เลขหน้า 3 ตัว" && lastThreeDigits != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrizeCard(
                        title = prizeGroup.title,
                        prizeAmountText = prizeGroup.prizeAmountText,
                        numbers = prizeGroup.numbers,
                        emphasized = prizeGroup.emphasized,
                        modifier = Modifier.weight(1f),
                    )

                    PrizeCard(
                        title = lastThreeDigits.title,
                        prizeAmountText = lastThreeDigits.prizeAmountText,
                        numbers = lastThreeDigits.numbers,
                        emphasized = lastThreeDigits.emphasized,
                        modifier = Modifier.weight(1f),
                    )
                }
                continue
            }

            val cardModifier = if (prizeGroup.title == "เลขท้าย 2 ตัว") {
                Modifier.fillMaxWidth(0.52f)
            } else if (prizeGroup.emphasized) {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
            } else {
                Modifier.fillMaxWidth()
            }

            PrizeCard(
                title = prizeGroup.title,
                prizeAmountText = prizeGroup.prizeAmountText,
                numbers = prizeGroup.numbers,
                emphasized = prizeGroup.emphasized,
                modifier = cardModifier,
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
