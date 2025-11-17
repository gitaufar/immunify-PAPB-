package com.example.immunify.ui.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.ChildData
import com.example.immunify.ui.theme.*

@SuppressLint("MutableCollectionMutableState")
@Composable
fun VaccinantSection(
    children: List<ChildData>,
    modifier: Modifier = Modifier,
    onUpdate: (List<ChildData>) -> Unit
) {
    // Simpan urutan asli
    val childOrder = remember {
        children.mapIndexed { index, child -> child.id to (index + 1) }.toMap()
    }

    // Gunakan SnapshotStateList supaya perubahan memicu recomposition
    val selected = remember { mutableStateListOf<ChildData>().apply { addAll(children) } }
    val unselected = remember { mutableStateListOf<ChildData>() }

    // Update external state when selection changes
    LaunchedEffect(selected.toList()) {
        onUpdate(selected.toList())
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Card Anak Terpilih
        if (selected.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White10, RoundedCornerShape(8.dp))
                    .border(1.dp, Grey30, RoundedCornerShape(8.dp))
            ) {
                // Tampilkan dalam urutan berdasarkan childOrder
                selected
                    .sortedBy { childOrder[it.id] ?: Int.MAX_VALUE }
                    .forEachIndexed { index, child ->
                        VaccinantItem(
                            child = child,
                            order = childOrder[child.id] ?: (index + 1),
                            isSelected = true,
                            isLast = index == selected.sortedBy { childOrder[it.id] }.lastIndex,
                            onToggle = {
                                if (selected.remove(child)) {
                                    unselected.add(0, child)
                                    onUpdate(selected)
                                }
                            }
                        )
                    }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Card Anak Tidak Terpilih
        if (unselected.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White10, RoundedCornerShape(8.dp))
                    .border(1.dp, Grey30, RoundedCornerShape(8.dp))
            ) {
                unselected
                    .sortedBy { childOrder[it.id] ?: Int.MAX_VALUE }
                    .forEachIndexed { index, child ->
                        VaccinantItem(
                            child = child,
                            order = childOrder[child.id] ?: (index + 1),
                            isSelected = false,
                            isLast = index == unselected.sortedBy { childOrder[it.id] }.lastIndex,
                            onToggle = {
                                if (unselected.remove(child)) {
                                    selected.add(child)
                                    onUpdate(selected)
                                }
                            }
                        )
                    }
            }
        }
    }
}

@Composable
fun VaccinantItem(
    child: ChildData,
    order: Int,
    isSelected: Boolean,
    isLast: Boolean,
    onToggle: () -> Unit
) {
    // rotasi: 0 = X (selected), 45 = + (unselected)
    val rotation by animateFloatAsState(
        targetValue = if (isSelected) 0f else 45f,
        animationSpec = tween(200)
    )

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vaccinant),
                    contentDescription = null,
                    tint = Grey90,
                    modifier = Modifier.size(22.dp)
                )

                Column {
                    Text(
                        text = child.name,
                        style = MaterialTheme.typography.labelMedium.copy(color = Black100)
                    )
                    Text(
                        text = "Child $order",
                        style = MaterialTheme.typography.labelSmall.copy(color = Grey70)
                    )
                }
            }

            IconButton(
                onClick = onToggle,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Grey70,
                    modifier = Modifier.rotate(rotation)
                )
            }
        }

        if (!isLast) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Grey30
            )
        }
    }
}