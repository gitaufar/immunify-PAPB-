package com.example.immunify.data.local

import com.example.immunify.R
import com.example.immunify.data.model.*

val polioDisease = DiseaseData(
    id = "polio",
    name = "Polio",
    imageRes = R.drawable.image_hpv,
    keyFacts = listOf(
        "Polio mainly affects children under 5.",
        "1 in 200 infections causes irreversible paralysis, and 5–10% of those affected die.",
        "One infected child can put all children at risk.",
        "Failure to eradicate polio may cause global resurgence.",
        "Global eradication efforts have improved disease control worldwide."
    ),
    overview = "Polio (poliomyelitis) is caused by poliovirus. It spreads from person to person and can infect the spinal cord, causing paralysis.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Requires a medical diagnosis",
            summaryDescription = "Most infected individuals have no symptoms. Some develop paralysis that can be fatal.",
            categories = listOf(
                SymptomCategory("Whole body", "Fatigue, fever, weakness"),
                SymptomCategory("Muscular", "Muscle loss or paralysis"),
                SymptomCategory("Also common", "Headache, nausea")
            )
        )
    ),
    treatments = listOf(
        TreatmentSection(
            overview = "There is no cure; treatment focuses on supportive care and rehabilitation.",
            categories = listOf(
                TreatmentCategory("Lifestyle", "Bed rest"),
                TreatmentCategory("Therapy", "Physical therapy"),
                TreatmentCategory("Medications", "Pain relievers / anti-inflammatory drugs")
            )
        )
    )
)

val hpvDisease = DiseaseData(
    id = "hpv",
    name = "HPV (Human Papillomavirus)",
    imageRes = R.drawable.image_polio,
    keyFacts = listOf(
        "HPV is one of the most common viral infections worldwide.",
        "Certain HPV types can cause cervical cancer and other genital cancers.",
        "HPV spreads mainly through skin-to-skin sexual contact.",
        "Vaccination can prevent the most dangerous HPV types.",
        "Most HPV infections clear on their own within two years."
    ),
    overview = "HPV is a group of viruses that infect skin and mucous membranes. Some strains are harmless, while others can lead to cancer or genital warts.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Often no symptoms",
            summaryDescription = "Many infected individuals never develop symptoms, making screening important.",
            categories = listOf(
                SymptomCategory("Skin / Genital area", "Genital warts, small bumps or lesions"),
                SymptomCategory(
                    "Long-term risk",
                    "Persistent infection may lead to cervical or other cancers"
                )
            )
        )
    ),
    treatments = listOf(
        TreatmentSection(
            overview = "There is no cure for the virus itself. Treatment targets warts or abnormal tissue changes.",
            categories = listOf(
                TreatmentCategory("Preventive", "HPV vaccination"),
                TreatmentCategory("Procedures", "Cryotherapy, laser removal, or minor surgery"),
                TreatmentCategory("Monitoring", "Routine Pap smear / HPV screening")
            )
        )
    )
)

val typhoidDisease = DiseaseData(
    id = "typhoid",
    name = "Typhoid Fever",
    imageRes = R.drawable.image_typhoid,
    keyFacts = listOf(
        "Typhoid is caused by Salmonella Typhi bacteria.",
        "It spreads through contaminated food or water.",
        "Without treatment, it can be life-threatening.",
        "Vaccination provides protection, especially for high-risk areas.",
        "Handwashing and safe food practices reduce risk."
    ),
    overview = "Typhoid fever causes prolonged high fever, weakness, stomach pain, headache, and sometimes rash. It spreads in areas with poor sanitation.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Symptoms develop gradually",
            summaryDescription = "Symptoms usually appear 1–2 weeks after exposure.",
            categories = listOf(
                SymptomCategory("Whole body", "High fever, fatigue, chills"),
                SymptomCategory(
                    "Digestive",
                    "Stomach pain, diarrhea or constipation, loss of appetite"
                ),
                SymptomCategory("Also common", "Headache, rash")
            )
        )
    ),
    treatments = listOf(
        TreatmentSection(
            overview = "Typhoid is treated with antibiotics and proper hydration.",
            categories = listOf(
                TreatmentCategory("Medications", "Antibiotics (as prescribed)"),
                TreatmentCategory("Supportive", "Rehydration therapy"),
                TreatmentCategory("Prevention", "Typhoid vaccination and safe food/water hygiene")
            )
        )
    )
)

val covidDisease = DiseaseData(
    id = "covid",
    name = "COVID-19",
    imageRes = R.drawable.image_covid,
    keyFacts = listOf(
        "COVID-19 is caused by the SARS-CoV-2 virus.",
        "Symptoms range from mild flu-like symptoms to severe pneumonia.",
        "The virus spreads through respiratory droplets and close contact.",
        "Vaccines reduce severe illness, hospitalization, and death.",
        "Good ventilation and masks reduce transmission indoors."
    ),
    overview = "COVID-19 is a respiratory illness caused by the coronavirus SARS-CoV-2. Severity varies widely, especially in older adults or people with underlying conditions.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Symptoms vary",
            summaryDescription = "Some people have no symptoms, while others develop severe breathing difficulties.",
            categories = listOf(
                SymptomCategory("Whole body", "Fever, fatigue, chills, body aches"),
                SymptomCategory("Respiratory", "Cough, sore throat, shortness of breath"),
                SymptomCategory("Also common", "Loss of taste or smell, headache, congestion")
            )
        )
    ),
    treatments = listOf(
        TreatmentSection(
            overview = "Treatment depends on severity. Most recover at home; severe cases may need oxygen or hospitalization.",
            categories = listOf(
                TreatmentCategory("Home care", "Rest, hydration, over-the-counter medicines"),
                TreatmentCategory("Medical", "Antivirals or corticosteroids in specific cases"),
                TreatmentCategory("Prevention", "Vaccination and mask use in high-risk settings")
            )
        )
    )
)

val DiseaseSamples = listOf(
    polioDisease,
    hpvDisease,
    typhoidDisease,
    covidDisease
)
