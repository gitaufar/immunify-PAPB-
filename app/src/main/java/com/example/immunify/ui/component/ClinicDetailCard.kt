package com.example.immunify.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.immunify.R
import com.example.immunify.data.model.ClinicDetailCardType
import com.example.immunify.ui.theme.*

@Composable
fun ClinicDetailCard(
    type: ClinicDetailCardType,
    title: String,
    subtitle: String? = null,
    showChange: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    val uriHandler = LocalUriHandler.current

    val iconStart = when (type) {
        ClinicDetailCardType.ADDRESS -> R.drawable.ic_location
        ClinicDetailCardType.TIME -> R.drawable.ic_clock
        ClinicDetailCardType.WEBSITE -> R.drawable.ic_website
        ClinicDetailCardType.PARENT -> R.drawable.ic_fullname
        ClinicDetailCardType.CLINIC -> R.drawable.ic_clinics
        ClinicDetailCardType.DATE -> R.drawable.ic_date
        ClinicDetailCardType.VACCINE -> R.drawable.ic_vaccine
        ClinicDetailCardType.VACCINANT -> R.drawable.ic_vaccinant
        ClinicDetailCardType.POLICY -> R.drawable.ic_info
    }

    // Icon kanan
    val endContent: @Composable (() -> Unit)? = when (type) {
        ClinicDetailCardType.CLINIC,
        ClinicDetailCardType.DATE,
        ClinicDetailCardType.VACCINE -> {
            if (showChange) {
                {
                    Text(
                        text = "Change",
                        style = MaterialTheme.typography.labelSmall.copy(color = PrimaryMain)
                    )
                }
            } else null
        }

        ClinicDetailCardType.VACCINANT,
        ClinicDetailCardType.TIME -> {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = null,
                    tint = Grey60,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        ClinicDetailCardType.ADDRESS -> {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_directions),
                    contentDescription = null,
                    tint = PrimaryMain,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        ClinicDetailCardType.PARENT,
        ClinicDetailCardType.WEBSITE,
        ClinicDetailCardType.POLICY -> null
    }

    // Klik untuk membuka website
    val finalClick: (() -> Unit)? = when (type) {
        ClinicDetailCardType.WEBSITE -> {
            {
                val urlSource = subtitle ?: title
                if (urlSource.isNotBlank()) {
                    val url = if (urlSource.startsWith("http")) urlSource else "https://$urlSource"
                    uriHandler.openUri(url)
                }
            }
        }

        else -> onClick
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Grey30, RoundedCornerShape(8.dp))
            .clickable(enabled = finalClick != null) { finalClick?.invoke() },
        color = White10,
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Left section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = iconStart),
                    contentDescription = null,
                    tint = if (type == ClinicDetailCardType.POLICY) PrimaryMain else Grey90,
                    modifier = Modifier.size(24.dp)
                )

                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Black100
                        )
                    )

                    if (!subtitle.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = subtitle,
                            style =
                                if (type == ClinicDetailCardType.POLICY)
                                    MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 12.sp,
                                        color = Grey70
                                    )
                                else
                                    MaterialTheme.typography.bodySmall.copy(color = Grey70),

                            maxLines =
                                when (type) {
                                    ClinicDetailCardType.POLICY,
                                    ClinicDetailCardType.CLINIC -> Int.MAX_VALUE

                                    else -> 1
                                },

                            overflow =
                                if (type == ClinicDetailCardType.POLICY || type == ClinicDetailCardType.CLINIC)
                                    TextOverflow.Visible
                                else
                                    TextOverflow.Ellipsis,

                            textAlign =
                                if (type == ClinicDetailCardType.POLICY)
                                    TextAlign.Justify
                                else
                                    TextAlign.Start
                        )
                    }
                }
            }

            // Right content
            if (endContent != null) {
                Box(modifier = Modifier.padding(start = 12.dp)) {
                    endContent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAllClinicDetailTypes() {
    ImmunifyTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            ClinicDetailCard(
                type = ClinicDetailCardType.PARENT,
                title = "Dona Doe",
                subtitle = "0218374920328"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.CLINIC,
                title = "RS EMC Pulomas",
                subtitle = "Jl. Pulo Mas Bar. VI No.20"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.DATE,
                title = "Sat, 22nd April 2023"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.VACCINE,
                title = "Varicella (Chicken Pox)"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.VACCINANT,
                title = "Choose Vaccinant"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.ADDRESS,
                title = "Jl. Pulo Mas Bar. VI No.20",
                subtitle = "Kec. Pulo Gadung, DKI Jakarta"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.TIME,
                title = "Open",
                subtitle = "24 Hours"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.WEBSITE,
                title = "www.emc.id"
            )
            ClinicDetailCard(
                type = ClinicDetailCardType.POLICY,
                title = "Cancellation",
                subtitle = "Our clinic requires a 24-hour notice for appointment cancellations. If you need to cancel your appointment, please call us at least 24 hours in advance. Failure to provide adequate notice may result in a cancellation fee."
            )
        }
    }
}
