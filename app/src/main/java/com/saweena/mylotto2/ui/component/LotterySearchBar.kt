package com.saweena.mylotto2.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
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
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = sanitizedValue,
                        onValueChange = { input ->
                            onValueChange(input.filter(Char::isDigit).take(6))
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "กรอกเลข 6 หลัก",
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.58f),
                                textAlign = TextAlign.Center,
                            )
                        },
                        textStyle = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Search,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                            disabledIndicatorColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                    )

                    IconButton(
                        onClick = onSearchClick,
                        enabled = isSearchEnabled,
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .semantics {
                                contentDescription = "ค้นหาเลขสลาก"
                            },
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Surface(
                                modifier = Modifier.matchParentSize(),
                                color = MaterialTheme.colorScheme.primary,
                            ) {}
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(10.dp),
                            )
                        }
                    }
                }
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
