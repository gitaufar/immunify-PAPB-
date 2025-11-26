package com.example.immunify.data.local

import com.example.immunify.data.model.VaccineData
import kotlin.collections.listOf

val VaccineSamples = listOf(
    VaccineData(
        id = "v_1",
        name = "HPV Vaccine",
        description = listOf(
            "Used to prevent infections caused by human papillomavirus (HPV).",
            "Recommended for preteens aged 9 to 14 but can be given up to age 26.",
            "Series involves 2–3 doses depending on age when starting vaccination.",
            "Helps reduce risk of cervical, throat, and anal cancers.",
            "Common mild side effects include fatigue, dizziness, and arm soreness."
        ),
        brand = listOf("Gardasil 9", "Cervarix"),
        scheduledDates = listOf("2025-11-30"),
        completedDates = emptyList(),
        remainingDoses = 1
    ),

    VaccineData(
        id = "v_2",
        name = "Influenza",
        description = listOf(
            "The influenza vaccine helps protect against seasonal flu viruses.",
            "Recommended annually, especially for children, elderly, and high-risk groups.",
            "Typically given as a single-dose intramuscular injection.",
            "Prevents severe flu symptoms and reduces hospitalization risk.",
            "Possible temporary side effects include fever, muscle pain, and mild fatigue."
        ),
        brand = listOf("Fluzone", "FluMist", "Fluad", "Flublok"),
        scheduledDates = listOf("2025-11-30", "2026-01-25"),
        completedDates = emptyList(),
        remainingDoses = 2
    ),

    VaccineData(
        id = "v_3",
        name = "Varicella (Chickenpox)",
        description = listOf(
            "Prevents infection caused by the varicella-zoster virus.",
            "Recommended for individuals without prior infection or immunity.",
            "Usually administered in two doses spaced 4–8 weeks apart.",
            "Helps reduce severe symptoms and lowers complications such as pneumonia.",
            "Side effects are generally mild: redness, fever, or mild rash near injection site."
        ),
        brand = listOf("Varivax", "MMRV (ProQuad)"),
        scheduledDates = listOf("2025-12-12"),
        completedDates = emptyList(),
        remainingDoses = 1
    ),

    VaccineData(
        id = "v_4",
        name = "HPV Vaccine",
        description = listOf(
            "HPV vaccine protects against the sexually transmitted human papillomavirus.",
            "Recommended for both genders, typically at ages 11–12 or as early as 9.",
            "Administered in a series of 2–3 doses depending on age at first vaccination.",
            "Highly effective in preventing cervical cancer, other HPV-related cancers, and genital warts.",
            "Side effects are typically mild and include pain, redness, swelling at the injection site, or mild fever."
        ),
        brand = listOf("Gardasil", "Cervarix"),
        scheduledDates = listOf("2025-10-08"),
        completedDates = listOf("2025-10-08"),
        remainingDoses = 0
    ),

    VaccineData(
        id = "v_5",
        name = "Hepatitis B Vaccine",
        description = listOf(
            "Provides protection against hepatitis B virus infection, which can lead to chronic liver disease or liver cancer.",
            "Recommended for infants at birth and for unvaccinated children or adults at risk.",
            "Administered as a series of 3–4 doses over 6 months depending on the vaccine used.",
            "Side effects are usually mild and may include low-grade fever or soreness."
        ),
        brand = listOf("Engerix-B", "Recombivax HB"),
        scheduledDates = listOf("2025-10-25"),
        completedDates = listOf("2025-10-25"),
        remainingDoses = 0
    ),

    VaccineData(
        id = "v_6",
        name = "Varicella (Chickenpox)",
        description = listOf(
            "Protects against chickenpox caused by the varicella-zoster virus.",
            "Recommended for children starting at 12–15 months of age and a booster dose at 4–6 years.",
            "Helps prevent both mild cases and severe complications such as pneumonia and infections.",
            "Common mild reactions include soreness at the injection site or mild rash."
        ),
        brand = listOf("Varivax", "ProQuad"),
        scheduledDates = listOf("2025-09-09", "2025-11-04"),
        completedDates = listOf("2025-09-09", "2025-11-04"),
        remainingDoses = 0
    )
)

val RSSA_Vaccines = listOf(
    VaccineData(
        id = "v_rssa_1",
        name = "HPV vaccine",
        description = listOf(
            "HPV vaccine protects against infection caused by the human papillomavirus.",
            "Recommended for adolescents aged 11–12 years but can start as early as 9.",
            "Given in 2–3 doses depending on age at first vaccination.",
            "Effective in preventing cervical cancer, anal cancer, and genital warts.",
            "Side effects are generally mild, including redness or pain at the injection site."
        ),
        brand = listOf("Gardasil", "Cervarix")
    ),
    VaccineData(
        id = "v_rssa_2",
        name = "Influenza vaccine",
        description = listOf(
            "Protects against seasonal influenza viruses.",
            "Recommended annually for children, elderly, and individuals with chronic conditions.",
            "Helps reduce risk of severe flu-related complications.",
            "Most common side effects include low-grade fever and soreness at the injection site."
        ),
        brand = listOf("Fluzone", "FluQuadri")
    ),
    VaccineData(
        id = "v_rssa_3",
        name = "Hepatitis B vaccine",
        description = listOf(
            "Protects against hepatitis B virus which can cause chronic liver disease and liver cancer.",
            "Typically given as a series of 3 doses over 6 months.",
            "Recommended for all infants and unvaccinated adults with risk factors.",
            "Side effects are mild and may include slight fatigue or arm soreness."
        ),
        brand = listOf("Engerix-B", "Recombivax HB")
    )
)

val SOEPRAOEN_Vaccines = listOf(
    VaccineData(
        id = "v_soep_1",
        name = "Varicella vaccine",
        description = listOf(
            "Protects against chickenpox caused by the varicella-zoster virus.",
            "Recommended in 2 doses starting from 12–15 months of age.",
            "Prevents both mild and severe chickenpox infections and reduces complications.",
            "Side effects may include mild rash or temporary soreness."
        ),
        brand = listOf("Varivax", "ProQuad")
    ),
    VaccineData(
        id = "v_soep_2",
        name = "MMR vaccine",
        description = listOf(
            "Protects against measles, mumps, and rubella.",
            "Given in 2 doses typically at 12–15 months and 4–6 years.",
            "Prevents severe complications such as pneumonia, meningitis, and congenital rubella.",
            "Side effects may include mild fever or rash."
        ),
        brand = listOf("MMR II", "Priorix")
    ),
    VaccineData(
        id = "v_soep_3",
        name = "Tetanus, Diphtheria, Pertussis (Tdap)",
        description = listOf(
            "Protects against tetanus, diphtheria, and pertussis (whooping cough).",
            "Recommended once during adolescence and during each pregnancy.",
            "Helps prevent severe respiratory infections and muscle paralysis.",
            "Common effects include redness, fatigue, and mild swelling at injection site."
        ),
        brand = listOf("Boostrix", "Adacel")
    ),
    VaccineData(
        id = "v_soep_4",
        name = "Pneumococcal vaccine",
        description = listOf(
            "Provides protection from pneumococcal bacteria which can cause pneumonia and meningitis.",
            "Recommended for infants, older adults, and people with chronic health conditions.",
            "Given as either PCV or PPSV type depending on age and risk.",
            "Side effects are mild such as low-grade fever or injection-site discomfort."
        ),
        brand = listOf("Prevnar 13", "Pneumovax 23")
    )
)

val RSUD_MALANG_Vaccines = listOf(
    VaccineData(
        id = "v_rsud_1",
        name = "BCG vaccine",
        description = listOf(
            "Protects infants from severe tuberculosis, especially TB meningitis and miliary TB.",
            "Usually administered shortly after birth.",
            "A small bump or scar may appear which is a normal response.",
            "Effective especially in countries with high TB prevalence."
        ),
        brand = listOf("BCG Vaccine SSI", "Onko BCG")
    ),
    VaccineData(
        id = "v_rsud_2",
        name = "Rotavirus vaccine",
        description = listOf(
            "Helps protect infants from rotavirus-related severe diarrhea and dehydration.",
            "Administered orally in 2–3 doses depending on vaccine brand.",
            "Most effective when started at 6 weeks of age.",
            "Side effects are minimal, usually mild fussiness or temporary diarrhea."
        ),
        brand = listOf("Rotarix", "RotaTeq")
    ),
    VaccineData(
        id = "v_rsud_3",
        name = "Hepatitis A vaccine",
        description = listOf(
            "Provides protection from hepatitis A virus which causes acute liver infection.",
            "Recommended for children aged 12–23 months and high-risk adults.",
            "Given in 2 doses spaced at least 6 months apart.",
            "Side effects are usually mild fatigue or tenderness at injection area."
        ),
        brand = listOf("Havrix", "Vaqta")
    )
)

val LAVALETTE_Vaccines = listOf(
    VaccineData(
        id = "v_lav_1",
        name = "Polio vaccine (IPV)",
        description = listOf(
            "Protects against poliovirus which causes paralysis.",
            "Given as part of routine childhood immunization.",
            "IPV is an inactivated vaccine administered via injection.",
            "Side effects are rare and generally very mild."
        ),
        brand = listOf("IPOL", "Poliorix")
    ),
    VaccineData(
        id = "v_lav_2",
        name = "Influenza vaccine",
        description = listOf(
            "Offers annual protection against circulating influenza strains.",
            "Recommended especially for older adults, pregnant women, and people with chronic diseases.",
            "Helps reduce risk of hospitalization and severe flu outcomes.",
            "Side effects may include temporary soreness or mild fever."
        ),
        brand = listOf("FluQuadri", "Influvac")
    ),
    VaccineData(
        id = "v_lav_3",
        name = "HPV vaccine",
        description = listOf(
            "Protects adolescents and adults from HPV-related diseases including cervical cancer.",
            "Given in 2–3 doses depending on age at initiation.",
            "Highly effective when given before sexual activity begins.",
            "Side effects are minor, such as injection site redness."
        ),
        brand = listOf("Gardasil 9")
    ),
    VaccineData(
        id = "v_lav_4",
        name = "Meningococcal vaccine",
        description = listOf(
            "Protects against meningococcal bacteria which cause meningitis and bloodstream infections.",
            "Recommended for adolescents and certain high-risk groups.",
            "Administered as a single dose with boosters when required.",
            "Side effects may include mild muscle pain or fever."
        ),
        brand = listOf("Menactra", "Menveo")
    )
)

val PERSADA_Vaccines = listOf(
    VaccineData(
        id = "v_pers_1",
        name = "COVID-19 vaccine",
        description = listOf(
            "Protects against SARS-CoV-2 infection and reduces risk of severe COVID-19.",
            "Recommended for individuals aged 6 months and older.",
            "Booster doses may be recommended depending on current health guidelines.",
            "Side effects are typically mild such as fatigue or arm soreness."
        ),
        brand = listOf("Pfizer-BioNTech", "Moderna")
    ),
    VaccineData(
        id = "v_pers_2",
        name = "DPT vaccine",
        description = listOf(
            "Protects children against diphtheria, pertussis (whooping cough), and tetanus.",
            "Given in multiple doses from 2 months of age.",
            "Helps prevent severe respiratory and nervous system complications.",
            "Mild fever and injection-site swelling can occur."
        ),
        brand = listOf("Pentabio", "Infanrix")
    ),
    VaccineData(
        id = "v_pers_3",
        name = "Typhoid vaccine",
        description = listOf(
            "Protects from Salmonella typhi infection which causes typhoid fever.",
            "Recommended for individuals living in or traveling to areas with high typhoid incidence.",
            "Available in injectable and oral forms.",
            "Side effects generally include mild fever or soreness."
        ),
        brand = listOf("Typhim Vi")
    )
)

val PANTI_NIRMALA_Vaccines = listOf(
    VaccineData(
        id = "v_pn_1",
        name = "Japanese Encephalitis vaccine",
        description = listOf(
            "Protects against Japanese encephalitis virus spread through mosquito bites.",
            "Recommended for travelers and residents in endemic regions.",
            "Given as 1–2 doses depending on vaccine type.",
            "Side effects may involve mild fever or fatigue."
        ),
        brand = listOf("JENVAC", "IXIARO")
    ),
    VaccineData(
        id = "v_pn_2",
        name = "MMR vaccine",
        description = listOf(
            "Provides immunity against measles, mumps, and rubella.",
            "Typically administered in 2 doses during childhood.",
            "Helps prevent outbreaks and protects pregnant women from congenital rubella syndrome.",
            "Side effects may include short-lived rash or fever."
        ),
        brand = listOf("MMR II", "Priorix")
    ),
    VaccineData(
        id = "v_pn_3",
        name = "Hepatitis A vaccine",
        description = listOf(
            "Protects from hepatitis A virus which spreads through contaminated food or water.",
            "Given in 2 doses spaced months apart.",
            "Highly effective in preventing symptomatic infection.",
            "Side effects are usually mild such as low energy or arm discomfort."
        ),
        brand = listOf("Havrix", "Avaxim")
    )
)

val PANTI_WALUYA_Vaccines = listOf(
    VaccineData(
        id = "v_pw_1",
        name = "Varicella vaccine",
        description = listOf(
            "Provides immunity against chickenpox and prevents severe complications.",
            "Administered as 2 doses for children and susceptible adults.",
            "Helps reduce risk of shingles later in life.",
            "Common reactions include mild rash or temporary swelling."
        ),
        brand = listOf("Varivax", "ProQuad")
    ),
    VaccineData(
        id = "v_pw_2",
        name = "Tdap vaccine",
        description = listOf(
            "Helps protect against tetanus, diphtheria, and pertussis.",
            "Recommended as a booster for adolescents and adults.",
            "Essential for pregnant women during each pregnancy.",
            "Side effects are generally mild such as arm soreness or headache."
        ),
        brand = listOf("Boostrix", "Adacel")
    ),
    VaccineData(
        id = "v_pw_3",
        name = "Pneumococcal vaccine",
        description = listOf(
            "Provides protection against pneumococcal infections including pneumonia and meningitis.",
            "Recommended for infants, older adults, and individuals with chronic diseases.",
            "Commonly given as PCV and PPSV formulations.",
            "Side effects may involve mild fever or fatigue."
        ),
        brand = listOf("Prevnar 13", "Pneumovax 23")
    )
)

val RSI_AISYIYAH_Vaccines = listOf(
    VaccineData(
        id = "v_aisy_1",
        name = "HPV vaccine",
        description = listOf(
            "Protects from high-risk HPV strains linked with cervical and other cancers.",
            "Most effective when administered before sexual debut.",
            "Delivered in 2–3 doses depending on age.",
            "Side effects are minor, mainly redness or mild fever."
        ),
        brand = listOf("Gardasil 9")
    ),
    VaccineData(
        id = "v_aisy_2",
        name = "BCG vaccine",
        description = listOf(
            "Provides protection from severe forms of tuberculosis.",
            "Administered soon after birth as part of routine immunization.",
            "A small lesion or scar formation is considered normal.",
            "Side effects are minimal and localized."
        ),
        brand = listOf("BCG SSI")
    ),
    VaccineData(
        id = "v_aisy_3",
        name = "Influenza vaccine",
        description = listOf(
            "Gives yearly protection from influenza viruses.",
            "Recommended for pregnant women, children, and elderly individuals.",
            "Helps reduce incidence of severe flu and hospitalizations.",
            "Side effects include mild fever or fatigue."
        ),
        brand = listOf("FluQuadri", "Influvac")
    )
)

val HERMINA_Vaccines = listOf(
    VaccineData(
        id = "v_herm_1",
        name = "COVID-19 vaccine",
        description = listOf(
            "Reduces risk of severe illness and hospitalization from COVID-19.",
            "Suitable for individuals 6 months and older.",
            "Booster schedules depend on newest guidelines.",
            "Side effects include muscle aches or temporary fever."
        ),
        brand = listOf("Pfizer-BioNTech", "Sinovac")
    ),
    VaccineData(
        id = "v_herm_2",
        name = "Rotavirus vaccine",
        description = listOf(
            "Oral vaccine protecting infants from severe rotavirus gastroenteritis.",
            "Given in 2–3 doses starting at 6 weeks.",
            "Greatly reduces risk of dehydration and hospitalization.",
            "Side effects include mild irritability or diarrhea."
        ),
        brand = listOf("Rotarix", "RotaTeq")
    ),
    VaccineData(
        id = "v_herm_3",
        name = "Hepatitis B vaccine",
        description = listOf(
            "Protects individuals from hepatitis B virus which can become chronic.",
            "Most effective when given at birth and completed in a series.",
            "Recommended for all age groups without prior immunity.",
            "Side effects are minimal and self-limiting."
        ),
        brand = listOf("Engerix-B", "Recombivax HB")
    )
)

val RS_UB_Vaccines = listOf(
    VaccineData(
        id = "v_ub_1",
        name = "MMR vaccine",
        description = listOf(
            "Protects individuals from measles, mumps, and rubella infection.",
            "Typically given as two doses during childhood.",
            "Prevents severe complications such as encephalitis and meningitis.",
            "Side effects are temporary such as mild fever or rash."
        ),
        brand = listOf("Priorix", "MMR II")
    ),
    VaccineData(
        id = "v_ub_2",
        name = "Typhoid vaccine",
        description = listOf(
            "Offers protection from typhoid fever caused by Salmonella typhi.",
            "Recommended for residents of and travelers to endemic areas.",
            "Available as both oral and injectable formulations.",
            "Side effects include mild fatigue or muscle pain."
        ),
        brand = listOf("Typhim Vi")
    ),
    VaccineData(
        id = "v_ub_3",
        name = "Pneumococcal vaccine",
        description = listOf(
            "Protects against pneumococcal bacteria responsible for pneumonia and meningitis.",
            "Given to infants, older adults, and people with chronic diseases.",
            "Commonly administered as PCV or PPSV depending on age.",
            "Side effects generally mild such as local swelling."
        ),
        brand = listOf("Prevnar 13", "Pneumovax 23")
    ),
    VaccineData(
        id = "v_ub_4",
        name = "Meningococcal vaccine",
        description = listOf(
            "Prevents infections caused by meningococcal bacteria including meningitis.",
            "Recommended for adolescents and high-risk populations.",
            "Usually administered as a single dose with booster if required.",
            "Temporary fever or arm soreness may occur."
        ),
        brand = listOf("Menveo", "Menactra")
    )
)
