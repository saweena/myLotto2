package com.saweena.mylotto2.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.saweena.mylotto2.ui.theme.MyLotto2Theme
import com.saweena.mylotto2.ui.theme.SurfaceYellow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrizeCard(
    title: String,
    prizeAmountText: String?,
    numbers: List<String>,
    emphasized: Boolean,
    modifier: Modifier = Modifier,
) {
    val containerColor = if (emphasized) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }
    val contentContainerColor = if (emphasized) {
        SurfaceYellow
    } else {
        MaterialTheme.colorScheme.surface
    }
    val contentColor = if (emphasized) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }
    val numbersTextStyle = if (emphasized) {
        MaterialTheme.typography.displayLarge
    } else {
        MaterialTheme.typography.headlineMedium
    }
    val innerPadding = if (emphasized) 20.dp else 16.dp

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = containerColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(if (emphasized) 20.dp else 14.dp),
        tonalElevation = if (emphasized) 4.dp else 1.dp,
        shadowElevation = if (emphasized) 2.dp else 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = contentColor,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )

            if (!prizeAmountText.isNullOrBlank()) {
                Text(
                    text = prizeAmountText,
                    style = MaterialTheme.typography.bodyMedium.copy(color = contentColor),
                    textAlign = TextAlign.Center,
                )
            }

            Surface(
                color = contentContainerColor,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(if (emphasized) 16.dp else 12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            PaddingValues(
                                horizontal = if (emphasized) 20.dp else 16.dp,
                                vertical = if (emphasized) 18.dp else 14.dp,
                            )
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 12.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = if (emphasized) 1 else 3,
                ) {
                    for (number in numbers) {
                        Text(
                            text = number,
                            style = numbersTextStyle.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun PrizeCardEmphasizedPreview() {
    MyLotto2Theme {
        PreviewPrizeCardContainer {
            PrizeCard(
                title = "รางวัลที่ 1",
                prizeAmountText = "รางวัลละ 6,000,000 บาท",
                numbers = listOf("123456"),
                emphasized = true,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun PrizeCardCompactPreview() {
    MyLotto2Theme {
        PreviewPrizeCardContainer {
            PrizeCard(
                title = "เลขท้าย 2 ตัว",
                prizeAmountText = null,
                numbers = listOf("56"),
                emphasized = false,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun PrizeCardMultipleNumbersPreview() {
    MyLotto2Theme {
        PreviewPrizeCardContainer {
            PrizeCard(
                title = "เลขหน้า 3 ตัว",
                prizeAmountText = "รางวัลละ 4,000 บาท",
                numbers = listOf("123", "456", "789", "012"),
                emphasized = false,
            )
        }
    }
}

@Composable
private fun PreviewPrizeCardContainer(
    content: @Composable () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(16.dp),
    ) {
        content()
    }
}
