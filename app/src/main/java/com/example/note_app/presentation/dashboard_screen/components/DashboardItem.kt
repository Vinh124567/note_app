package com.example.note_app.presentation.dashboard_screen.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.note_app.R

data class DashboardItem(
    val title: String,
    val fileCount: Int,
    val sizeInMB: Float,
    val iconTint: Color,
    val backgroundRes: Int,
    val iconRes: Int,
    val onClick: () -> Unit
)


@Composable
fun DashboardItemCard(
    item: DashboardItem, modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable { item.onClick() }) {
        Image(
            painter = painterResource(id = R.drawable.item),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 44.dp, start = 45.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = item.iconTint.copy(alpha = 0.1f), shape = RoundedCornerShape(23.dp)
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "${item.sizeInMB} MB",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
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
            backgroundRes = R.drawable.ic_document,
            iconRes = R.drawable.ic_document,
            onClick = onPersonalClick
        ), DashboardItem(
            title = "Work",
            fileCount = 20,
            sizeInMB = 256.0f,
            iconTint = Color.Green,
            backgroundRes = R.drawable.ic_academic,
            iconRes = R.drawable.ic_academic,
            onClick = onWorkClick
        ), DashboardItem(
            title = "Private",
            fileCount = 15,
            sizeInMB = 128.0f,
            iconTint = Color.Blue,
            onClick = onPrivateClick,
            backgroundRes = R.drawable.ic_work,
            iconRes = R.drawable.ic_work,
        ), DashboardItem(
            title = "Others",
            fileCount = 10,
            sizeInMB = 64.0f,
            iconTint = Color.Yellow,
            onClick = onOthersClick, backgroundRes = R.drawable.ic_paper,
            iconRes = R.drawable.ic_paper,
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


@Preview(showBackground = true)
@Composable
fun DashBoardGridViewPreview() {
    DashBoardGridView(onPersonalClick = {},
        onWorkClick = {},
        onPrivateClick = {},
        onOthersClick = {})
}