package com.example.immunify.ui.clinics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.immunify.R
import com.example.immunify.ui.component.ClinicNearbyCard
import com.example.immunify.ui.component.SearchBar
import com.example.immunify.ui.theme.ImmunifyTheme
import com.example.immunify.ui.theme.Typography

@Composable
fun ClinicsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp)
) {
    val clinics = listOf(
        ClinicItem(
            "RS EMC Pulomas",
            "Jl. Pulo Mas Bar. VI No.20, Kec. Pulo Gadung",
            "2 km",
            4.9,
            R.drawable.image_hospital
        ),
        ClinicItem(
            "Columbia Asia Hospital",
            "Jl. Kayu Putih Raya No.1, RW.16, Kayu Putih",
            "3 km",
            4.9,
            R.drawable.image_hospital
        ),
        ClinicItem(
            "RSUP Persahabatan",
            "Jl. Persahabatan Raya No.1, Rawamangun",
            "2 km",
            4.9,
            R.drawable.image_hospital
        ),
        ClinicItem(
            "Mediros Hospital",
            "Jl. Perintis Kemerdekaan Kav. 149, Pulo Gadung",
            "3 km",
            3.6,
            R.drawable.image_hospital
        ),
        ClinicItem(
            "Pertamina Jaya Hospital",
            "Jl. Jend. Ahmad Yani No.2, RT.2/RW.7, Cempaka Putih",
            "2 km",
            4.0,
            R.drawable.image_hospital
        ),
        ClinicItem(
            "Mayapada Hospital",
            "Jl. Mayapada No. 88, Jakarta Timur",
            "4 km",
            4.8,
            R.drawable.image_hospital
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding // scaffold akan isi otomatis
    ) {
        item {
            SearchBar()
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.image_map),
                contentDescription = "Map",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Clinics Nearby",
                style = Typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(clinics) { clinic ->
            ClinicNearbyCard(
                imageRes = clinic.imageRes,
                hospitalName = clinic.name,
                address = clinic.address,
                distance = clinic.distance,
                rating = clinic.rating
            )
        }
    }
}

data class ClinicItem(
    val name: String,
    val address: String,
    val distance: String,
    val rating: Double,
    val imageRes: Int
)

@Preview(showBackground = true)
@Composable
fun PreviewClinicsScreen() {
    ImmunifyTheme {
        ClinicsScreen()
    }
}
