package com.saweena.mylotto2.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.saweena.mylotto2.ui.theme.MyLotto2Theme

@Composable
fun ResultCheckingScreen(
    uiState: ResultCheckingUiState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCheckAnotherClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            LegacyResultTopBar(
                onBackClick = onBackClick,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
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
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = drawDateText,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        Surface(
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = lotteryNumber,
                modifier = Modifier.padding(horizontal = 34.dp, vertical = 26.dp),
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
            )
        }

        StatusBadge(status = status)
    }
}

@Composable
private fun LegacyResultTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ย้อนกลับ",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun StatusBadge(
    status: ResultCheckingStatus,
    modifier: Modifier = Modifier,
) {
    val text = when (status) {
        ResultCheckingStatus.Won -> "ถูกรางวัล"
        ResultCheckingStatus.NotWon -> "ไม่ถูกรางวัล"
    }

    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = if (status == ResultCheckingStatus.Won) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        },
        textAlign = TextAlign.Center,
    )
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
    val primaryPrize = matchedPrizes.firstOrNull()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        if (primaryPrize != null) {
            Text(
                text = "${primaryPrize.prizeName}   ${primaryPrize.prizeAmountText.removePrefix("รางวัลละ ")}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        if (matchedPrizes.size > 1) {
            Text(
                text = matchedPrizes.drop(1).joinToString(separator = "\n") {
                    "${it.prizeName} ${it.prizeAmountText}"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "จำนวน",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Surface(
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(2.dp),
            ) {
                Text(
                    text = "1",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
            Text(
                text = "ใบ",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(42.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }

        Text(
            text = "รวมทั้งสิ้น ${primaryPrize?.prizeAmountText?.removePrefix("รางวัลละ ") ?: "-"}",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun NotWonContent() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "เสียใจด้วย งวดนี้ยังไม่ถูกรางวัล",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "ลองตรวจเลขอื่น หรือบันทึกเลขไว้ตรวจครั้งถัดไป",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
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
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        OutlinedButton(
            onClick = onCheckAnotherClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
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
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
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
