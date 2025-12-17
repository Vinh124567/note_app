package com.example.note_app.presentation.note_list.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.note_app.R
import com.example.note_app.ui.theme.color6B4EFF

data class DashboardItem(
    val title: String,
    val fileCount: Int,
    val sizeInMB: Float,
    val iconTint: Color,
    val onClick: () -> Unit
)

@Composable
fun DashboardItemCard(
    item: DashboardItem,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        // Ảnh background
        Image(
            painter = painterResource(id = R.drawable.item),
            contentDescription = "Dashboard Item Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // Icon bên trong
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 44.dp, start = 45.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = color6B4EFF.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(23.dp)
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_document),
                    contentDescription = "Folder Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(26.dp)
                )
            }
            Text(
                text = "Personal",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.ExtraBold,
            )
            Text(
                text = "Personal",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            )
        }
    }
}

@Composable
fun DashBoardGridView(
    onPersonalClick: () -> Unit,
    onWorkClick: () -> Unit,
    onPrivateClick: () -> Unit,
    onOthersClick: () -> Unit
) {
    val dashBoardItem = listOf(
        DashboardItem(
            title = "Personal",
            fileCount = 34,
            sizeInMB = 512.0f,
            iconTint = Color.Red,
            onClick = onPersonalClick
        ),
        DashboardItem(
            title = "Work",
            fileCount = 20,
            sizeInMB = 256.0f,
            iconTint = Color.Green,
            onClick = onWorkClick
        ),
        DashboardItem(
            title = "Private",
            fileCount = 15,
            sizeInMB = 128.0f,
            iconTint = Color.Blue,
            onClick = onPrivateClick
        ),
        DashboardItem(
            title = "Others",
            fileCount = 10,
            sizeInMB = 64.0f,
            iconTint = Color.Yellow,
            onClick = onOthersClick
        )
    )

    Column {
        dashBoardItem.chunked(2).forEach { rowItem ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItem.forEach { item ->
                    DashboardItemCard(
                        item = item,
                        modifier = Modifier
                            .weight(1f)
                            .size(width = 181.dp, height = 182.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun DashboardItemCardPreview() {
    val item = DashboardItem(
        title = "Documents",
        fileCount = 12,
        sizeInMB = 256.5f,
        iconTint = Color.Blue,
        onClick = {}
    )

    DashboardItemCard(
        item = item,
        modifier = Modifier.size(width = 181.dp, height = 182.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DashBoardGridViewPreview() {
    DashBoardGridView(
        onPersonalClick = {},
        onWorkClick = {},
        onPrivateClick = {},
        onOthersClick = {}
    )
}