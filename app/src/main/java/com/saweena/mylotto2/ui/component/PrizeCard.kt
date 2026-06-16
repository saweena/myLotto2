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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PrizeCard(
    title: String,
    prizeAmountText: String?,
    numbers: List<String>,
    emphasized: Boolean,
    modifier: Modifier = Modifier,
) {
    val contentColor = MaterialTheme.colorScheme.onPrimary
    val numbersTextStyle = if (emphasized) {
        MaterialTheme.typography.displayLarge
    } else {
        MaterialTheme.typography.headlineMedium
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        contentColor = contentColor,
        shape = RoundedCornerShape(15.dp),
        shadowElevation = 0.dp,
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = contentColor,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Center,
            )

            if (!prizeAmountText.isNullOrBlank()) {
                Text(
                    text = prizeAmountText,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = contentColor,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
            }

            Surface(
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            PaddingValues(
                                horizontal = if (emphasized) 20.dp else 10.dp,
                                vertical = if (emphasized) 8.dp else 6.dp,
                            )
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = if (emphasized) 12.dp else 18.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = if (emphasized) 1 else 2,
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
