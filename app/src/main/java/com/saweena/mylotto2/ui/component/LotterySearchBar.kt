package com.saweena.mylotto2.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saweena.mylotto2.ui.theme.MyLotto2Theme
import com.saweena.mylotto2.ui.theme.SurfaceGreen

@Composable
fun LotterySearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String? = null,
) {
    val sanitizedValue = value.filter(Char::isDigit).take(6)
    val isSearchEnabled = sanitizedValue.length == 6

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = sanitizedValue,
                onValueChange = { input ->
                    onValueChange(input.filter(Char::isDigit).take(6))
                },
                modifier = Modifier.weight(1f),
                singleLine = true,
                isError = !errorText.isNullOrBlank(),
                placeholder = {
                    Text(
                        text = "กรอกเลข 6 หลัก",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                    )
                },
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Search,
                ),
                shape = MaterialTheme.shapes.large,
            )

            Button(
                onClick = onSearchClick,
                enabled = isSearchEnabled,
                modifier = Modifier
                    .width(96.dp)
                    .heightIn(min = 56.dp)
                    .semantics {
                        contentDescription = "ค้นหาเลขสลาก"
                    },
            ) {
                Text(
                    text = "ค้นหา",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }

        if (!errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun LotterySearchBarEmptyPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LotterySearchBarPreviewContainer {
                LotterySearchBar(
                    value = "",
                    onValueChange = {},
                    onSearchClick = {},
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun LotterySearchBarValidPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LotterySearchBarPreviewContainer {
                LotterySearchBar(
                    value = "123456",
                    onValueChange = {},
                    onSearchClick = {},
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun LotterySearchBarErrorPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LotterySearchBarPreviewContainer {
                LotterySearchBar(
                    value = "123",
                    onValueChange = {},
                    onSearchClick = {},
                    errorText = "กรุณากรอกเลขให้ครบ 6 หลัก",
                )
            }
        }
    }
}

@Composable
private fun LotterySearchBarPreviewContainer(
    content: @Composable () -> Unit,
) {
    Surface(
        color = SurfaceGreen,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.padding(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
