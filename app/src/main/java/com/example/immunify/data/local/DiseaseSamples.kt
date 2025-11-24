package com.example.immunify.data.local

import com.example.immunify.R
import com.example.immunify.data.model.*

val polioDisease = DiseaseData(
    id = "polio",
    name = "Polio",
    imageRes = R.drawable.image_polio,
    keyFacts = listOf(
        "Polio mainly affects children under 5.",
        "1 in 200 infections causes irreversible paralysis, and 5–10% of those affected die.",
        "One infected child can put all children at risk of contracting polio.",
        "Failure to eradicate polio from remaining strongholds could cause a global resurgence.",
        "The global effort to eradicate polio has improved infectious disease control in many countries."
    ),
    overview = "Polio, or poliomyelitis, is a disabling and life-threatening disease caused by the poliovirus. " +
            "The virus spreads from person to person and can infect a person's spinal cord, causing paralysis (can't move parts of the body).",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Requires a medical diagnosis",
            summaryDescription = "Many people who are infected with the poliovirus don't become sick and have no symptoms. " +
                    "However, those who do become ill develop paralysis, which can sometimes be fatal.",
            categories = listOf(
                SymptomCategory("Whole body", "Fatigue, feeling faint, fever, or wasting away"),
                SymptomCategory("Muscular", "Muscle weakness, loss of muscle, or muscle quiver"),
                SymptomCategory("Also common", "Headache, nausea, or stunted growth")
            )
        )
    ),
    treatments = listOf(
        TreatmentSection(
            overview = "There is no cure for polio, but it can be prevented with safe and effective vaccination. " +
                    "Treatment includes bed rest, pain relievers and portable ventilators.",
            categories = listOf(
                TreatmentCategory("Lifestyle", "Bed rest"),
                TreatmentCategory("Therapy", "Physical therapy"),
                TreatmentCategory(
                    "Medications",
                    "Nonsteroidal anti-inflammatory drug and Analgesic"
                )
            )
        )
    )
)

val hpvDisease = DiseaseData(
    id = "hpv",
    name = "HPV",
    imageRes = R.drawable.image_hpv,
    keyFacts = listOf(
        "HPV is one of the most common viral infections worldwide.",
        "Certain HPV types can cause cervical cancer and other genital cancers.",
        "HPV spreads mainly through skin-to-skin sexual contact.",
        "Vaccination can prevent the most dangerous HPV types.",
        "Most HPV infections clear on their own within two years."
    ),
    overview = "HPV is a group of viruses that infect skin and mucous membranes. Some strains are harmless, while others can lead to cancer or genital warts. Persistent infection with high-risk HPV types is associated with cervical, anal, penile, vulvar, vaginal and oropharyngeal cancers.",
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
            overview = "There is no cure for the virus itself. Treatment targets warts or abnormal tissue changes. Vaccination and regular screening are key in prevention.",
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
        "Hand-washing and safe food practices reduce risk."
    ),
    overview = "Typhoid fever causes prolonged high fever, weakness, stomach pain, headache, and sometimes rash. It spreads where sanitation is poor and clean water is lacking. Vaccination and improved hygiene are important in prevention.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Symptoms develop gradually",
            summaryDescription = "Symptoms usually appear 1-2 weeks after exposure. Without treatment, complications such as intestinal perforation can occur.",
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
            overview = "Typhoid is treated with antibiotics and proper hydration. Vaccines can reduce risk in endemic regions.",
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
    overview = "COVID-19 is a respiratory illness caused by the coronavirus SARS-CoV-2. Severity varies widely, especially in older adults or people with underlying conditions. Vaccination, masking, ventilation and early detection are key in controlling spread.",
    symptoms = listOf(
        SymptomSection(
            summaryTitle = "Symptoms vary",
            summaryDescription = "Some people have no symptoms, while others develop severe breathing difficulties. Long-COVID may persist for months.",
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
