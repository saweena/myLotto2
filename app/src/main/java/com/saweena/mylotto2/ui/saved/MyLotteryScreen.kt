package com.saweena.mylotto2.ui.saved

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MyLotteryScreen(
    savedLotteries: List<SavedLotteryUiModel>,
    showDeleteDialog: Boolean,
    onBackClick: () -> Unit,
    onLotteryClick: (SavedLotteryUiModel) -> Unit,
    onDeleteClick: (SavedLotteryUiModel) -> Unit,
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                LegacySavedTopBar(
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                )
            }

            if (savedLotteries.isEmpty()) {
                item {
                    EmptySavedLotteryState(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 48.dp),
                    )
                }
            } else {
                savedLotteries
                    .groupBy { it.drawDateText }
                    .forEach { (drawDateText, lotteries) ->
                        item(key = drawDateText) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Text(
                                    text = drawDateText,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 12.dp,
                                        alignment = Alignment.CenterHorizontally,
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    maxItemsInEachRow = 3,
                                ) {
                                    lotteries.forEach { savedLottery ->
                                        SavedLotteryCard(
                                            savedLottery = savedLottery,
                                            onClick = { onLotteryClick(savedLottery) },
                                            onDeleteClick = { onDeleteClick(savedLottery) },
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
        }

        if (showDeleteDialog) {
            DeleteConfirmationDialog(
                onConfirmDelete = onConfirmDelete,
                onDismissDelete = onDismissDelete,
            )
        }
    }
}

@Composable
private fun LegacySavedTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "ย้อนกลับ",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Text(
            text = "ผลตรวจสลาก",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SavedLotteryCard(
    savedLottery: SavedLotteryUiModel,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    @Suppress("UNUSED_EXPRESSION")
    onDeleteClick

    val chipColor = when (savedLottery.status) {
        LotteryStatusUiModel.Won -> MaterialTheme.colorScheme.primary
        LotteryStatusUiModel.Pending,
        LotteryStatusUiModel.NotWon,
        -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f)
    }
    val amountText = when {
        !savedLottery.prizeSummaryText.isNullOrBlank() -> "x1"
        else -> null
    }

    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = chipColor,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(5.dp),
        shadowElevation = 5.dp,
    ) {
        Row(
            modifier = Modifier.padding(start = 6.dp, top = 5.dp, end = 8.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = savedLottery.lotteryNumber,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary,
            )

            if (amountText != null) {
                Text(
                    text = amountText,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Composable
private fun EmptySavedLotteryState(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = "ยังไม่มีเลขที่บันทึก",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "บันทึกเลขสลากของคุณเพื่อตรวจผลภายหลัง",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onConfirmDelete: () -> Unit,
    onDismissDelete: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissDelete,
        title = {
            Text(
                text = "ลบเลขนี้?",
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            Text(
                text = "คุณต้องการลบเลขสลากนี้ออกจากรายการของคุณหรือไม่",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            Button(onClick = onConfirmDelete) {
                Text(
                    text = "ลบ",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismissDelete) {
                Text(
                    text = "ยกเลิก",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
    )
}

data class SavedLotteryUiModel(
    val id: String,
    val lotteryNumber: String,
    val drawDateText: String,
    val status: LotteryStatusUiModel,
    val prizeSummaryText: String? = null,
)

enum class LotteryStatusUiModel(val label: String) {
    Pending("รอตรวจผล"),
    Won("ถูกรางวัล"),
    NotWon("ไม่ถูกรางวัล"),
}

private val savedLotteryPreviewItems = listOf(
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
    SavedLotteryUiModel(
        id = "3",
        lotteryNumber = "258697",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.Won,
        prizeSummaryText = "เลขท้าย 2 ตัว",
    ),
    SavedLotteryUiModel(
        id = "4",
        lotteryNumber = "906781",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "5",
        lotteryNumber = "507947",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "6",
        lotteryNumber = "153646",
        drawDateText = "16 มิถุนายน 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "7",
        lotteryNumber = "653521",
        drawDateText = "1 มิถุนายน 2569",
        status = LotteryStatusUiModel.Won,
        prizeSummaryText = "เลขหน้า 3 ตัว",
    ),
    SavedLotteryUiModel(
        id = "8",
        lotteryNumber = "566129",
        drawDateText = "1 มิถุนายน 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "9",
        lotteryNumber = "462354",
        drawDateText = "1 มิถุนายน 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "10",
        lotteryNumber = "573351",
        drawDateText = "16 พฤษภาคม 2569",
        status = LotteryStatusUiModel.Pending,
    ),
    SavedLotteryUiModel(
        id = "11",
        lotteryNumber = "469213",
        drawDateText = "16 พฤษภาคม 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "12",
        lotteryNumber = "366980",
        drawDateText = "16 พฤษภาคม 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "13",
        lotteryNumber = "888888",
        drawDateText = "16 พฤษภาคม 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
    SavedLotteryUiModel(
        id = "14",
        lotteryNumber = "150684",
        drawDateText = "16 พฤษภาคม 2569",
        status = LotteryStatusUiModel.NotWon,
    ),
)

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun MyLotteryScreenSavedNumbersPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MyLotteryScreen(
                savedLotteries = savedLotteryPreviewItems,
                showDeleteDialog = false,
                onBackClick = {},
                onLotteryClick = {},
                onDeleteClick = {},
                onConfirmDelete = {},
                onDismissDelete = {},
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun MyLotteryScreenEmptyPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MyLotteryScreen(
                savedLotteries = emptyList(),
                showDeleteDialog = false,
                onBackClick = {},
                onLotteryClick = {},
                onDeleteClick = {},
                onConfirmDelete = {},
                onDismissDelete = {},
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun MyLotteryScreenDeleteDialogPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MyLotteryScreen(
                savedLotteries = savedLotteryPreviewItems,
                showDeleteDialog = true,
                onBackClick = {},
                onLotteryClick = {},
                onDeleteClick = {},
                onConfirmDelete = {},
                onDismissDelete = {},
            )
        }
    }
}
