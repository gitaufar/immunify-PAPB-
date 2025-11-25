package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProfileSheet(
    children: List<ChildData>,
    selectedChild: ChildData?,
    onDismiss: () -> Unit = {},
    onSelect: (ChildData) -> Unit = {},
    onAddNewProfile: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = White10,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = Modifier.navigationBarsPadding()
    ) {
        SelectProfileSheetContent(
            children = children,
            selectedChild = selectedChild,
            onSelect = onSelect,
            onAddNewProfile = onAddNewProfile
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SelectProfileSheetContent(
    children: List<ChildData>,
    selectedChild: ChildData?,
    onSelect: (ChildData) -> Unit,
    onAddNewProfile: () -> Unit
) {
    var selectedId by remember { mutableStateOf(selectedChild?.id) }

    Column(
        Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            var maleIndex = 0
            var femaleIndex = 0

            itemsIndexed(children) { index, child ->

                val avatarColor =
                    if (child.gender == Gender.MALE)
                        getChildColor(Gender.MALE, maleIndex++)
                    else
                        getChildColor(Gender.FEMALE, femaleIndex++)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedId = child.id
                            onSelect(child)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(avatarColor)
                        )

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = child.name,
                                style = MaterialTheme.typography.labelLarge.copy(color = Black100)
                            )
                            Text(
                                text = child.gender.name.lowercase().replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodySmall.copy(color = Grey70),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    RadioButton(
                        selected = selectedId == child.id,
                        onClick = {
                            selectedId = child.id
                            onSelect(child)
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = PrimaryMain
                        )
                    )
                }

                if (index < children.lastIndex) {
                    Spacer(Modifier.height(16.dp))
                    Divider(color = Grey30, thickness = 1.dp)
                }
            }

            item {
                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onAddNewProfile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PrimaryMain, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White10,
                        contentColor = PrimaryMain
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Add",
                        tint = PrimaryMain,
                        modifier = Modifier.size(22.dp).rotate(45f)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Add New Profile")
                }
            }
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewSelectProfileSheet() {
//    ImmunifyTheme {
//        SelectProfileSheet(
//            children = ChildSamples,
//            onDismiss = {},
//            onSelect = {},
//            onAddNewProfile = {}
//        )
//    }
//}
