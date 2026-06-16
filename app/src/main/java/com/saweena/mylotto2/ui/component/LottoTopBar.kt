package com.saweena.mylotto2.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saweena.mylotto2.ui.theme.MyLotto2Theme

@Composable
fun LottoTopBar(
    title: String,
    showBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (showBack) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ย้อนกลับ",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        } else {
            Box(modifier = Modifier.width(48.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun LottoTopBarWithBackPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LottoTopBar(
                title = "ตรวจผลสลาก",
                showBack = true,
                onBackClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F8F5)
@Composable
private fun LottoTopBarWithoutBackPreview() {
    MyLotto2Theme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LottoTopBar(
                title = "เลขของฉัน",
                showBack = false,
                onBackClick = {},
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
