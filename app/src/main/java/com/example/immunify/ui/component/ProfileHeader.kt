package com.example.immunify.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.data.model.ChildData
import com.example.immunify.data.model.Gender
import com.example.immunify.ui.theme.Black100
import com.example.immunify.ui.theme.Grey70
import com.example.immunify.ui.theme.ImmunifyTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileHeader(
    child: ChildData,
    avatarColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(avatarColor)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                // Name
                Text(
                    text = child.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = Black100),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Birthday + Gender
                Row(verticalAlignment = Alignment.CenterVertically) {

                    // Birthday icon + formatted date
                    Icon(
                        painter = painterResource(id = R.drawable.ic_birthday),
                        contentDescription = null,
                        tint = Grey70,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    val formattedDate =
                        LocalDate.parse(child.birthDate)
                            .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey70)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // Gender icon
                    Icon(
                        painter = painterResource(
                            id = if (child.gender == Gender.MALE) R.drawable.ic_male else R.drawable.ic_female
                        ),
                        contentDescription = null,
                        tint = Grey70,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Gender text
                    Text(
                        text = if (child.gender == Gender.MALE) "Male" else "Female",
                        style = MaterialTheme.typography.bodySmall.copy(color = Grey70)
                    )
                }
            }
        }

        // Next icon
        Icon(
            painter = painterResource(id = R.drawable.ic_next_bold),
            contentDescription = "Next",
            tint = Grey70,
            modifier = Modifier.size(28.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProfileHeader() {

    val sampleChild = ChildData(
        id = "child_01",
        name = "Jane Doe",
        birthDate = "2008-06-20",
        gender = Gender.FEMALE
    )

    val color = androidx.compose.ui.graphics.Color(0xFFFFC6C6)

    ImmunifyTheme {
        ProfileHeader(
            child = sampleChild,
            avatarColor = color,
            onClick = {}
        )
    }
}
