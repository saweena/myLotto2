package com.saweena.mylotto2.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saweena.mylotto2.ui.component.LottoTopBar
import com.saweena.mylotto2.ui.component.PrizeCard
import com.saweena.mylotto2.ui.theme.MyLotto2Theme
import com.saweena.mylotto2.ui.theme.SurfaceGreen
import com.saweena.mylotto2.ui.theme.SurfaceYellow

@Composable
fun ResultCheckingScreen(
    uiState: ResultCheckingUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCheckAnotherClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            LottoTopBar(
                title = "ตรวจผลสลาก",
                showBack = true,
                onBackClick = onBackClick,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            )
        }

        item {
            ResultSummaryCard(
                lotteryNumber = uiState.lotteryNumber,
                drawDateText = uiState.drawDateText,
                status = uiState.status,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        item {
            ResultContent(
                uiState = uiState,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        item {
            BottomActions(
                onSaveClick = onSaveClick,
                onCheckAnotherClick = onCheckAnotherClick,
                saveEnabled = uiState.canSave,
                saveButtonText = uiState.saveButtonText,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}

@Composable
private fun ResultSummaryCard(
    lotteryNumber: String,
    drawDateText: String,
    status: ResultCheckingStatus,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = lotteryNumber,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )

            Text(
                text = drawDateText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            StatusBadge(status = status)
        }
    }
}

@Composable
private fun StatusBadge(
    status: ResultCheckingStatus,
    modifier: Modifier = Modifier,
) {
    val containerColor = when (status) {
        ResultCheckingStatus.Won -> SurfaceGreen
        ResultCheckingStatus.NotWon -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (status) {
        ResultCheckingStatus.Won -> MaterialTheme.colorScheme.primary
        ResultCheckingStatus.NotWon -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val text = when (status) {
        ResultCheckingStatus.Won -> "ถูกรางวัล"
        ResultCheckingStatus.NotWon -> "ไม่ถูกรางวัล"
    }

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun ResultContent(
    uiState: ResultCheckingUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        when (uiState.status) {
            ResultCheckingStatus.Won -> WonContent(
                matchedPrizes = uiState.matchedPrizes.sortedBy { it.priority },
            )

            ResultCheckingStatus.NotWon -> NotWonContent()
        }
    }
}

@Composable
private fun WonContent(
    matchedPrizes: List<MatchedPrizeUiModel>,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceYellow,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "ยินดีด้วย คุณถูกรางวัล",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )

            Text(
                text = "พบ ${matchedPrizes.size} รายการที่ตรงกับเลขสลากของคุณ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    for (prize in matchedPrizes) {
        PrizeCard(
            title = prize.prizeName,
            prizeAmountText = prize.prizeAmountText,
            numbers = listOf(prize.matchedNumber),
            emphasized = prize.priority == PrizePriority.FirstPrize,
        )
    }
}

@Composable
private fun NotWonContent() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "เสียใจด้วย งวดนี้ยังไม่ถูกรางวัล",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = "ลองตรวจเลขอื่น หรือบันทึกเลขไว้ตรวจครั้งถัดไป",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun BottomActions(
    onSaveClick: () -> Unit,
    onCheckAnotherClick: () -> Unit,
    saveEnabled: Boolean,
    saveButtonText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OutlinedButton(
            onClick = onCheckAnotherClick,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "ตรวจเลขอื่น",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        Button(
            onClick = onSaveClick,
            enabled = saveEnabled,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = saveButtonText,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}

data class ResultCheckingUiState(
    val lotteryNumber: String,
    val drawDateText: String,
    val status: ResultCheckingStatus,
    val matchedPrizes: List<MatchedPrizeUiModel> = emptyList(),
    val canSave: Boolean = true,
    val saveButtonText: String = "บันทึกเลขนี้",
)

data class MatchedPrizeUiModel(
    val prizeName: String,
    val prizeAmountText: String,
    val matchedNumber: String,
    val priority: PrizePriority,
)

enum class ResultCheckingStatus {
    Won,
    NotWon,
}

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

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun ResultCheckingScreenWonPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ResultCheckingScreen(
                uiState = ResultCheckingUiState(
                    lotteryNumber = "123456",
                    drawDateText = "16 มิถุนายน 2569",
                    status = ResultCheckingStatus.Won,
                    matchedPrizes = listOf(
                        MatchedPrizeUiModel(
                            prizeName = "รางวัลที่ 1",
                            prizeAmountText = "รางวัลละ 6,000,000 บาท",
                            matchedNumber = "123456",
                            priority = PrizePriority.FirstPrize,
                        ),
                    ),
                ),
                onBackClick = {},
                onSaveClick = {},
                onCheckAnotherClick = {},
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun ResultCheckingScreenNotWonPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ResultCheckingScreen(
                uiState = ResultCheckingUiState(
                    lotteryNumber = "045789",
                    drawDateText = "16 มิถุนายน 2569",
                    status = ResultCheckingStatus.NotWon,
                ),
                onBackClick = {},
                onSaveClick = {},
                onCheckAnotherClick = {},
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun ResultCheckingScreenMultiplePrizePreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ResultCheckingScreen(
                uiState = ResultCheckingUiState(
                    lotteryNumber = "123456",
                    drawDateText = "16 มิถุนายน 2569",
                    status = ResultCheckingStatus.Won,
                    matchedPrizes = listOf(
                        MatchedPrizeUiModel(
                            prizeName = "เลขท้าย 2 ตัว",
                            prizeAmountText = "รางวัลละ 2,000 บาท",
                            matchedNumber = "56",
                            priority = PrizePriority.LastTwoDigits,
                        ),
                        MatchedPrizeUiModel(
                            prizeName = "รางวัลที่ 1",
                            prizeAmountText = "รางวัลละ 6,000,000 บาท",
                            matchedNumber = "123456",
                            priority = PrizePriority.FirstPrize,
                        ),
                        MatchedPrizeUiModel(
                            prizeName = "เลขท้าย 3 ตัว",
                            prizeAmountText = "รางวัลละ 4,000 บาท",
                            matchedNumber = "456",
                            priority = PrizePriority.LastThreeDigits,
                        ),
                    ),
                ),
                onBackClick = {},
                onSaveClick = {},
                onCheckAnotherClick = {},
            )
        }
    }
}
