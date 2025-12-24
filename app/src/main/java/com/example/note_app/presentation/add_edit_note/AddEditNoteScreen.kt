@file:OptIn(ExperimentalLayoutApi::class)
package com.example.note_app.presentation.add_edit_note

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.note_app.data.local.entity.NoteType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel,
    onNavigateBack: () -> Unit
) {
    val title = viewModel.noteTitle.value
    val content = viewModel.noteContent.value
    val selectedType = viewModel.noteType.value
    val selectedColor = viewModel.noteColor.value
    val showTypeSelector = viewModel.showTypeSelector.value
    val showColorPicker = viewModel.showColorPicker.value

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        "Tạo ghi chú mới",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.showColorPicker() }) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(selectedColor))
                        )
                    }

                    // Type selector button
                    IconButton(onClick = { viewModel.showTypeSelector() }) {
                        Icon(
                            imageVector = getTypeIcon(selectedType),
                            contentDescription = "Chọn loại",
                            tint = getTypeColor(selectedType)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.saveNote()
                    onNavigateBack()
                },
                icon = {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Lưu"
                    )
                },
                text = { Text("Lưu ghi chú") },
                expanded = title.isNotEmpty() || content.isNotEmpty(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .navigationBarsPadding()
        ) {
            // Type indicator chip
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Surface(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = getTypeColor(selectedType).copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = getTypeIcon(selectedType),
                            contentDescription = null,
                            tint = getTypeColor(selectedType),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = getTypeDisplayName(selectedType),
                            style = MaterialTheme.typography.labelMedium,
                            color = getTypeColor(selectedType),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Title field
            OutlinedTextField(
                value = title,
                onValueChange = { viewModel.onTitleChange(it) },
                placeholder = {
                    Text(
                        "Tiêu đề...",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Content field
            OutlinedTextField(
                value = content,
                onValueChange = { viewModel.onContentChange(it) },
                placeholder = {
                    Text(
                        "Bắt đầu viết...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // Character count
            AnimatedVisibility(
                visible = content.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Text(
                    text = "${content.length} ký tự",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // Type Selector Bottom Sheet
        if (showTypeSelector) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.hideTypeSelector() },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                TypeSelectorContent(
                    selectedType = selectedType,
                    onTypeSelected = { type ->
                        viewModel.onTypeChange(type)
                        viewModel.hideTypeSelector()
                    }
                )
            }
        }

        // Color Picker Bottom Sheet
        if (showColorPicker) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.hideColorPicker() },
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                ColorPickerContent(
                    selectedColor = selectedColor,
                    onColorSelected = { color ->
                        viewModel.onColorChange(color)
                        viewModel.hideColorPicker()
                    }
                )
            }
        }
    }
}

@Composable
fun TypeSelectorContent(
    selectedType: NoteType,
    onTypeSelected: (NoteType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Chọn loại ghi chú",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        NoteType.values().forEach { type ->
            TypeItem(
                type = type,
                isSelected = type == selectedType,
                onClick = { onTypeSelected(type) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TypeItem(
    type: NoteType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) getTypeColor(type).copy(alpha = 0.15f)
        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getTypeIcon(type),
                contentDescription = null,
                tint = getTypeColor(type),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = getTypeDisplayName(type),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )

            if (isSelected) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = getTypeColor(type),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ColorPickerContent(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
) {
    val colors = listOf(
        0xFFFFB3BA.toInt(), // Pastel Red
        0xFFFFDFBA.toInt(), // Pastel Orange
        0xFFFFFFBA.toInt(), // Pastel Yellow
        0xFFBAFFC9.toInt(), // Pastel Green
        0xFFBAE1FF.toInt(), // Pastel Blue
        0xFFE0BBE4.toInt(), // Pastel Purple
        0xFFFFD6E8.toInt(), // Pastel Pink
        0xFFC9C9C9.toInt(), // Gray
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Chọn màu",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            colors.forEach { color ->
                ColorCircle(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ColorCircle(
    color: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(56.dp),
        shape = CircleShape,
        color = Color(color),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(
            width = 3.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

fun getTypeIcon(type: NoteType) = when (type) {
    NoteType.PERSONAL -> Icons.Default.Person
    NoteType.WORK -> Icons.Default.Build
    NoteType.STUDY -> Icons.Default.Create
    NoteType.SHOPPING -> Icons.Default.ShoppingCart
    NoteType.REMINDER -> Icons.Default.Notifications
}

fun getTypeColor(type: NoteType) = when (type) {
    NoteType.PERSONAL -> Color(0xFF6366F1)
    NoteType.WORK -> Color(0xFF8B5CF6)
    NoteType.STUDY -> Color(0xFF06B6D4)
    NoteType.SHOPPING -> Color(0xFFEC4899)
    NoteType.REMINDER -> Color(0xFFF59E0B)
}

fun getTypeDisplayName(type: NoteType) = when (type) {
    NoteType.PERSONAL -> "Cá nhân"
    NoteType.WORK -> "Công việc"
    NoteType.STUDY -> "Học tập"
    NoteType.SHOPPING -> "Mua sắm"
    NoteType.REMINDER -> "Nhắc nhở"
}