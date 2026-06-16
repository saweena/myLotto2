package com.saweena.mylotto2.ui.saved

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.saweena.mylotto2.ui.component.LottoTopBar
import com.saweena.mylotto2.ui.theme.MyLotto2Theme
import com.saweena.mylotto2.ui.theme.SurfaceGreen
import com.saweena.mylotto2.ui.theme.SurfaceYellow

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
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                LottoTopBar(
                    title = "เลขของฉัน",
                    showBack = true,
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
                items(
                    items = savedLotteries,
                    key = { it.id },
                ) { savedLottery ->
                    SavedLotteryCard(
                        savedLottery = savedLottery,
                        onClick = { onLotteryClick(savedLottery) },
                        onDeleteClick = { onDeleteClick(savedLottery) },
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
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
fun SavedLotteryCard(
    savedLottery: SavedLotteryUiModel,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = savedLottery.lotteryNumber,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = savedLottery.drawDateText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                StatusChip(status = savedLottery.status)

                if (!savedLottery.prizeSummaryText.isNullOrBlank()) {
                    Text(
                        text = savedLottery.prizeSummaryText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "ลบเลขสลาก",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    status: LotteryStatusUiModel,
    modifier: Modifier = Modifier,
) {
    val containerColor = when (status) {
        LotteryStatusUiModel.Pending -> SurfaceYellow
        LotteryStatusUiModel.Won -> SurfaceGreen
        LotteryStatusUiModel.NotWon -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (status) {
        LotteryStatusUiModel.Pending -> MaterialTheme.colorScheme.secondary
        LotteryStatusUiModel.Won -> MaterialTheme.colorScheme.primary
        LotteryStatusUiModel.NotWon -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor,
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = status.label,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
        )
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
        lotteryNumber = "888888",
        drawDateText = "1 มิถุนายน 2569",
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
