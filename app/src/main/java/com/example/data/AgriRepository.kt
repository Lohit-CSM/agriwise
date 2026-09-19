package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object AgriRepository {

    // Default Scenario: 8 tonnes Red Chilli, Grade A, Guntur Andhra Pradesh
    private val defaultLot = ProduceLot(
        id = "AW-CHL-2026-001",
        crop = CropType.RED_CHILLI,
        quantityTonnes = 8.0,
        grade = "Grade A",
        state = "Andhra Pradesh",
        district = "Guntur",
        mandal = "Tenali",
        availabilityDays = 3,
        storageAvailable = true,
        moisturePercent = 10.2,
        damagePercent = 1.1,
        colorGrade = "Deep Crimson Red (ASTA 120+)",
        certification = "Spices Board of India / e-NAM Registered",
        ownerName = "Guntur Rythu FPO Producer Co.",
        harvestDate = "Ready in 3 Days (21 Sep 2026)"
    )

    private val _currentLot = MutableStateFlow(defaultLot)
    val currentLot: StateFlow<ProduceLot> = _currentLot.asStateFlow()

    private val _currentUser = MutableStateFlow(
        UserProfile(
            id = "USR-FPO-789",
            name = "Ravi Kumar Naidu",
            role = UserRole.FPO,
            organization = "Guntur District Farmers Producer Organisation",
            location = "Guntur, Andhra Pradesh",
            phone = "+91 98480 12345",
            isVerified = true
        )
    )
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    private val _currentLanguage = MutableStateFlow("English")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()

    private val _analysisStepText = MutableStateFlow("")
    val analysisStepText: StateFlow<String> = _analysisStepText.asStateFlow()

    // 1. Markets
    private val defaultMarkets = listOf(
        MarketOpportunity(
            id = "MKT-01",
            name = "Guntur Agricultural Market Yard (APMC Mandi)",
            category = MarketCategory.NEARBY,
            location = "Guntur Yard, AP",
            distanceKm = 25,
            demandLevel = "Steady",
            headlinePricePerQuintal = 18000.0,
            logisticsCostPerQuintal = 120.0,
            handlingCostPerQuintal = 90.0,
            marketFeesPerQuintal = 60.0,
            otherApplicableCostsPerQuintal = 30.0,
            qualityRequirement = "Fair Average Quality (FAQ) Grade A & B",
            buyerInterestCount = 14,
            priceFitScore = 80,
            demandFitScore = 75,
            qualityFitScore = 95,
            logisticsFitScore = 98,
            buyerReliabilityScore = 85,
            whyExplainableNotes = listOf(
                "Immediate local offloading within 25 km",
                "Lowest transit logistics cost (₹120/q)",
                "Standard APMC regulated weighing & transparent yard slips",
                "Higher local cess and standard yard unloading charges apply"
            )
        ),
        MarketOpportunity(
            id = "MKT-02",
            name = "Sri Lakshmi Agro Processors & Oleoresins",
            category = MarketCategory.STATE,
            location = "Hyderabad Industrial Hub, Telangana",
            distanceKm = 120,
            demandLevel = "High",
            headlinePricePerQuintal = 19000.0,
            logisticsCostPerQuintal = 350.0,
            handlingCostPerQuintal = 100.0,
            marketFeesPerQuintal = 50.0,
            otherApplicableCostsPerQuintal = 100.0,
            qualityRequirement = "Grade A with Moisture <= 11%, High Capsaicin",
            buyerInterestCount = 5,
            priceFitScore = 90,
            demandFitScore = 92,
            qualityFitScore = 98,
            logisticsFitScore = 84,
            buyerReliabilityScore = 94,
            whyExplainableNotes = listOf(
                "Premium direct processing purchase for oleoresin extraction",
                "Zero commission agent cuts compared to traditional open bidding",
                "Higher headline price absorbs the 120 km transit logistics",
                "Guaranteed electronic direct bank transfer on weighbridge receipt"
            )
        ),
        MarketOpportunity(
            id = "MKT-03",
            name = "ITC Agri Business Division (National FMCG)",
            category = MarketCategory.NATIONAL,
            location = "Nagpur National Distribution Hub",
            distanceKm = 650,
            demandLevel = "Very High",
            headlinePricePerQuintal = 20100.0,
            logisticsCostPerQuintal = 820.0,
            handlingCostPerQuintal = 180.0,
            marketFeesPerQuintal = 80.0,
            otherApplicableCostsPerQuintal = 320.0,
            qualityRequirement = "Grade A Verified, ASTA Color 110+, Aflatoxin Tested",
            buyerInterestCount = 8,
            priceFitScore = 96,
            demandFitScore = 95,
            qualityFitScore = 90,
            logisticsFitScore = 65,
            buyerReliabilityScore = 98,
            whyExplainableNotes = listOf(
                "High headline price (₹20,100/q) for export and national packaged foods",
                "Long haul container transit increases freight deduction by ₹820/q",
                "Rigorous quality lab inspection upon arrival at central depot",
                "Estimated net realizable value remains higher than local mandi"
            )
        ),
        MarketOpportunity(
            id = "MKT-04",
            name = "Al-Barakah Global Trading (Jebel Ali / Dubai Hub)",
            category = MarketCategory.GLOBAL,
            location = "Chennai Port / Dubai UAE Transit",
            distanceKm = 380,
            demandLevel = "Urgent / Premium",
            headlinePricePerQuintal = 22000.0, // INR equivalent reference
            logisticsCostPerQuintal = 1450.0,
            handlingCostPerQuintal = 450.0,
            marketFeesPerQuintal = 200.0,
            otherApplicableCostsPerQuintal = 800.0,
            qualityRequirement = "Grade A Export Spec, Certified Phytosanitary, Pre-fumigated",
            buyerInterestCount = 3,
            priceFitScore = 98,
            demandFitScore = 90,
            qualityFitScore = 85,
            logisticsFitScore = 52,
            buyerReliabilityScore = 92,
            whyExplainableNotes = listOf(
                "Strong Middle-East culinary demand for Teja dry red chilli",
                "Indicative international parity yields ₹22,000/q equivalent",
                "High export logistics, CFS handling, and port phytosanitary clearance",
                "Requires export documentation readiness (currently 65% complete)"
            )
        )
    )

    private val _markets = MutableStateFlow(defaultMarkets)
    val markets: StateFlow<List<MarketOpportunity>> = _markets.asStateFlow()

    // 2. Buyers
    private val defaultBuyers = listOf(
        Buyer(
            id = "BYR-01",
            name = "Sri Lakshmi Foods Pvt Ltd",
            companyType = "Food Processor & Spice Miller",
            location = "Hyderabad, Telangana",
            requiredCrop = "Red Chilli (Grade A)",
            minQuantityTonnes = 5.0,
            maxQuantityTonnes = 25.0,
            matchScorePercent = 94,
            indicativeOfferPerQuintal = 19100.0,
            reliabilityBadge = "Verified Enterprise",
            responseTimeHours = 2,
            contactPerson = "S. Ramanathan (Procurement VP)"
        ),
        Buyer(
            id = "BYR-02",
            name = "Patanjali Foods Supply Chain",
            companyType = "National FMCG Brand",
            location = "Haridwar / Hubli Depot",
            requiredCrop = "Red Chilli (Grade A & B)",
            minQuantityTonnes = 10.0,
            maxQuantityTonnes = 50.0,
            matchScorePercent = 88,
            indicativeOfferPerQuintal = 18950.0,
            reliabilityBadge = "A+ Rated Corporate",
            responseTimeHours = 4,
            contactPerson = "Vikas Sharma"
        ),
        Buyer(
            id = "BYR-03",
            name = "Deccan Spice Exports LLP",
            companyType = "APEDA & Spices Board Exporter",
            location = "Visakhapatnam Port Zone",
            requiredCrop = "Red Chilli (Grade A Stemless)",
            minQuantityTonnes = 15.0,
            maxQuantityTonnes = 80.0,
            matchScorePercent = 91,
            indicativeOfferPerQuintal = 19200.0,
            reliabilityBadge = "Spices Board Certified",
            responseTimeHours = 1,
            contactPerson = "Anand Vardhan"
        ),
        Buyer(
            id = "BYR-04",
            name = "Kaveri Agro Industries",
            companyType = "Regional Cold Storage & Milling",
            location = "Vijayawada, Andhra Pradesh",
            requiredCrop = "Red Chilli / Turmeric",
            minQuantityTonnes = 4.0,
            maxQuantityTonnes = 15.0,
            matchScorePercent = 89,
            indicativeOfferPerQuintal = 18700.0,
            reliabilityBadge = "Direct Farm Partner",
            responseTimeHours = 3,
            contactPerson = "M. Srinivas Rao"
        ),
        Buyer(
            id = "BYR-05",
            name = "FreshToHome Agri Retail",
            companyType = "Direct-to-Consumer / Modern Retail",
            location = "Bengaluru, Karnataka",
            requiredCrop = "Red Chilli / Tomato",
            minQuantityTonnes = 2.0,
            maxQuantityTonnes = 12.0,
            matchScorePercent = 86,
            indicativeOfferPerQuintal = 19050.0,
            reliabilityBadge = "Verified Marketplace",
            responseTimeHours = 5,
            contactPerson = "Pooja Hegde"
        )
    )

    private val _buyers = MutableStateFlow(defaultBuyers)
    val buyers: StateFlow<List<Buyer>> = _buyers.asStateFlow()

    // 3. Reverse Buyer Offers
    private val defaultOffers = listOf(
        BuyerOffer(
            id = "OFF-001",
            buyerId = "BYR-03",
            buyerName = "Deccan Spice Exports LLP",
            buyerType = "Spices Board Exporter",
            location = "Visakhapatnam Port SEZ",
            pricePerQuintal = 19200.0,
            requestedQuantityTonnes = 8.0,
            pickupDeliveryTerm = "Farmgate Pickup Arranged by Buyer",
            qualitySpec = "Grade A, Moisture <= 10.5%, ASTA 120+",
            paymentTerms = "Direct Bank RTGS within 24 hours of weighbridge load",
            verificationStatus = "Verified & Escrow Backed",
            expiryDays = 2,
            status = OfferStatus.PENDING
        ),
        BuyerOffer(
            id = "OFF-002",
            buyerId = "BYR-01",
            buyerName = "Sri Lakshmi Foods Pvt Ltd",
            buyerType = "Food Processing Industry",
            location = "Hyderabad Unit",
            pricePerQuintal = 19050.0,
            requestedQuantityTonnes = 8.0,
            pickupDeliveryTerm = "Direct Delivery to Hyderabad Depot",
            qualitySpec = "Grade A, Minimal discoloration, Dry stalk",
            paymentTerms = "Instant Escrow release on moisture sample check",
            verificationStatus = "Verified Enterprise",
            expiryDays = 3,
            status = OfferStatus.PENDING
        ),
        BuyerOffer(
            id = "OFF-003",
            buyerId = "BYR-02",
            buyerName = "Patanjali Foods Supply Chain",
            buyerType = "National FMCG Corporation",
            location = "Hubli Distribution Yard",
            pricePerQuintal = 18950.0,
            requestedQuantityTonnes = 8.0,
            pickupDeliveryTerm = "Shared Freight Logistics",
            qualitySpec = "Grade A/B Accepted, Standard FAQ",
            paymentTerms = "Direct Bank Account Deposit in T+2 Days",
            verificationStatus = "A+ Rated Corporate",
            expiryDays = 4,
            status = OfferStatus.PENDING
        ),
        BuyerOffer(
            id = "OFF-004",
            buyerId = "BYR-04",
            buyerName = "Kaveri Agro Industries",
            buyerType = "Regional Miller",
            location = "Vijayawada Yard",
            pricePerQuintal = 18700.0,
            requestedQuantityTonnes = 6.0,
            pickupDeliveryTerm = "Immediate Farmgate Loading",
            qualitySpec = "Grade A Teja standard",
            paymentTerms = "Same Day Immediate Cash/UPI Transfer",
            verificationStatus = "Direct Farm Partner",
            expiryDays = 1,
            status = OfferStatus.PENDING
        )
    )

    private val _offers = MutableStateFlow(defaultOffers)
    val offers: StateFlow<List<BuyerOffer>> = _offers.asStateFlow()

    // 4. Aggregation Group
    private val defaultAggregation = AggregationGroup(
        targetBuyerName = "ITC Mega Food Park & Exporters Consortium",
        institutionalRequirementTonnes = 20.0,
        userLotTonnes = 8.0,
        nearbyLots = listOf(
            AggregationLot(
                id = "AGG-01",
                farmerOrFpoName = "Rythu Mithra Group (K. Venkatesh)",
                location = "Tenali, Guntur (12 km)",
                distanceKm = 12,
                quantityTonnes = 4.0,
                grade = "Grade A (Moisture 10.8%)"
            ),
            AggregationLot(
                id = "AGG-02",
                farmerOrFpoName = "Kisan Vikas Cooperative",
                location = "Bapatla Mandal (24 km)",
                distanceKm = 24,
                quantityTonnes = 6.0,
                grade = "Grade A (Moisture 10.4%)"
            ),
            AggregationLot(
                id = "AGG-03",
                farmerOrFpoName = "Sitarama Organic Farmer Group",
                location = "Mangalagiri (18 km)",
                distanceKm = 18,
                quantityTonnes = 3.0,
                grade = "Grade A (Moisture 9.9%)"
            )
        ),
        estimatedLogisticsSavingPerQuintal = 220.0,
        estimatedBargainingBenefitPercent = 3.5
    )

    private val _aggregation = MutableStateFlow(defaultAggregation)
    val aggregation: StateFlow<AggregationGroup> = _aggregation.asStateFlow()

    // 5. Global Market Radar
    private val defaultGlobalOpportunities = listOf(
        GlobalOpportunity(
            id = "GLB-UAE",
            destinationCountry = "United Arab Emirates (Dubai)",
            flagEmoji = "🇦🇪",
            targetCommodity = "Indian Red Chilli (Teja Dried)",
            demandLevel = "Very High",
            indicativeInternationalPricePerQuintalEq = 22000.0,
            qualityRequirements = "Grade A Stemless, ASTA 120+, Zero Synthetic Additives",
            logisticsComplexity = "Moderate",
            exportReadinessStatus = "Partial Match (65%)",
            potentialFit = "Strong",
            keyPort = "Chennai Port to Jebel Ali (4-6 transit days)"
        ),
        GlobalOpportunity(
            id = "GLB-SAU",
            destinationCountry = "Saudi Arabia (Riyadh / Jeddah)",
            flagEmoji = "🇸🇦",
            targetCommodity = "Whole Red Chilli & Chilli Flakes",
            demandLevel = "High",
            indicativeInternationalPricePerQuintalEq = 21600.0,
            qualityRequirements = "SFDA (Saudi Food & Drug Authority) Compliant, Halal Verified",
            logisticsComplexity = "High",
            exportReadinessStatus = "Partial Match (58%)",
            potentialFit = "Strong",
            keyPort = "Jawaharlal Nehru Port (JNPT) / Jeddah"
        ),
        GlobalOpportunity(
            id = "GLB-BGD",
            destinationCountry = "Bangladesh (Dhaka / Benapole)",
            flagEmoji = "🇧🇩",
            targetCommodity = "Bulk Commercial Dry Chilli",
            demandLevel = "High",
            indicativeInternationalPricePerQuintalEq = 19800.0,
            qualityRequirements = "Standard Export Grade, Moisture < 12%",
            logisticsComplexity = "Low (Rail / Overland)",
            exportReadinessStatus = "High Potential (85%)",
            potentialFit = "Very Strong",
            keyPort = "Overland Rail Freight via Benapole Land Port"
        ),
        GlobalOpportunity(
            id = "GLB-MYS",
            destinationCountry = "Malaysia (Kuala Lumpur)",
            flagEmoji = "🇲🇾",
            targetCommodity = "Hot Chilli Powder Raw Material",
            demandLevel = "Moderate",
            indicativeInternationalPricePerQuintalEq = 20900.0,
            qualityRequirements = "Pesticide Residue Limits (PRL) Compliant",
            logisticsComplexity = "Moderate",
            exportReadinessStatus = "Partial Match (62%)",
            potentialFit = "Moderate",
            keyPort = "Visakhapatnam / Port Klang"
        ),
        GlobalOpportunity(
            id = "GLB-VNM",
            destinationCountry = "Vietnam (Ho Chi Minh City)",
            flagEmoji = "🇻🇳",
            targetCommodity = "Industrial Oleoresin Extraction Grade",
            demandLevel = "High",
            indicativeInternationalPricePerQuintalEq = 20400.0,
            qualityRequirements = "High Pungency (SHU > 45,000)",
            logisticsComplexity = "Moderate",
            exportReadinessStatus = "High Potential (80%)",
            potentialFit = "Strong",
            keyPort = "Chennai / Cat Lai Port"
        ),
        GlobalOpportunity(
            id = "GLB-EUR",
            destinationCountry = "European Union (Rotterdam)",
            flagEmoji = "🇪🇺",
            targetCommodity = "Organic Certified Chilli Pods",
            demandLevel = "Premium",
            indicativeInternationalPricePerQuintalEq = 24500.0,
            qualityRequirements = "EU MRL compliant, Aflatoxin < 5 ppb, BRC Packaging",
            logisticsComplexity = "Very High",
            exportReadinessStatus = "Requires Accreditation (45%)",
            potentialFit = "Future Phase",
            keyPort = "Mundra / Rotterdam"
        )
    )

    private val _globalOpportunities = MutableStateFlow(defaultGlobalOpportunities)
    val globalOpportunities: StateFlow<List<GlobalOpportunity>> = _globalOpportunities.asStateFlow()

    // 6. Export Readiness Checklist
    private val defaultExportChecklist = listOf(
        ExportCheckItem(
            id = "EXP-01",
            title = "Farmer / FPO Institutional Profile",
            description = "FPO Registration, GSTIN, PAN and official bank linkage verified on AgriWise.",
            isCompleted = true,
            isCritical = true
        ),
        ExportCheckItem(
            id = "EXP-02",
            title = "Produce Quantity & Lot Traceability",
            description = "8 tonnes verified dry lot with digital harvest batch id AW-CHL-2026-001.",
            isCompleted = true,
            isCritical = true
        ),
        ExportCheckItem(
            id = "EXP-03",
            title = "Basic Quality & Moisture Grading",
            description = "Laboratory report: Moisture at 10.2%, Grade A color profile compliant.",
            isCompleted = true,
            isCritical = true
        ),
        ExportCheckItem(
            id = "EXP-04",
            title = "APEDA / Spices Board Registered RCMC",
            description = "Registration-cum-Membership Certificate for spice export clearance.",
            isCompleted = false,
            isCritical = true
        ),
        ExportCheckItem(
            id = "EXP-05",
            title = "Standard Export Export Packaging (25kg Gunny/PP)",
            description = "Food-grade double stitched moisture-resistant corrugated liners.",
            isCompleted = false,
            isCritical = false
        ),
        ExportCheckItem(
            id = "EXP-06",
            title = "Authorized Customs Clearing Partner (CHA)",
            description = "Logistics linkage with approved freight forwarder at Chennai Port.",
            isCompleted = false,
            isCritical = true
        )
    )

    private val _exportChecklist = MutableStateFlow(defaultExportChecklist)
    val exportChecklist: StateFlow<List<ExportCheckItem>> = _exportChecklist.asStateFlow()

    // 7. Price Trends (7-day / 30-day)
    val priceHistory7Days = listOf(
        PriceHistoryPoint("12 Sep", 17800.0, 17200.0, 16900.0),
        PriceHistoryPoint("13 Sep", 18000.0, 17400.0, 17050.0),
        PriceHistoryPoint("14 Sep", 18200.0, 17600.0, 17200.0),
        PriceHistoryPoint("15 Sep", 18100.0, 17500.0, 17150.0),
        PriceHistoryPoint("16 Sep", 18350.0, 17750.0, 17300.0),
        PriceHistoryPoint("17 Sep", 18700.0, 18100.0, 17600.0),
        PriceHistoryPoint("18 Sep (Today)", 19000.0, 18400.0, 17800.0)
    )

    // 8. Notifications
    private val defaultNotifications = listOf(
        NotificationItem(
            id = "NOTIF-01",
            title = "Competitive Buyer Offer Received",
            message = "Deccan Spice Exports LLP offered ₹19,200/q for your 8t Red Chilli lot.",
            timeAgo = "10 mins ago",
            isUnread = true,
            targetTab = "offers"
        ),
        NotificationItem(
            id = "NOTIF-02",
            title = "Compatible Supply Detected for Aggregation",
            message = "Rythu Mithra Group added 4t nearby. Combined 21t meets ITC requirement!",
            timeAgo = "1 hour ago",
            isUnread = true,
            targetTab = "aggregation"
        ),
        NotificationItem(
            id = "NOTIF-03",
            title = "Global Demand Alert: UAE",
            message = "Dubai wholesale index moved up to ₹22,000/q equivalent. Check export readiness.",
            timeAgo = "3 hours ago",
            isUnread = false,
            targetTab = "global"
        ),
        NotificationItem(
            id = "NOTIF-04",
            title = "Mandi Reference Rate Updated",
            message = "Guntur Yard reference price rose by ₹250/q following quality arrivals.",
            timeAgo = "1 day ago",
            isUnread = false,
            targetTab = "price"
        )
    )

    private val _notifications = MutableStateFlow(defaultNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    // Functions to modify state
    fun switchUserRole(role: UserRole) {
        _currentUser.update {
            when (role) {
                UserRole.FARMER -> it.copy(
                    role = UserRole.FARMER,
                    name = "Venkata Rao",
                    organization = "Independent Producer (5 Acres)",
                    location = "Tenali, Guntur, AP"
                )
                UserRole.FPO -> it.copy(
                    role = UserRole.FPO,
                    name = "Ravi Kumar Naidu",
                    organization = "Guntur District Farmers Producer Co. (380 Members)",
                    location = "Guntur, Andhra Pradesh"
                )
                UserRole.BUYER -> it.copy(
                    role = UserRole.BUYER,
                    name = "S. Ramanathan",
                    organization = "Sri Lakshmi Foods Pvt Ltd (Procurement VP)",
                    location = "Hyderabad Hub"
                )
            }
        }
    }

    fun setLanguage(lang: String) {
        _currentLanguage.value = lang
    }

    fun submitNewLot(
        crop: CropType,
        quantity: Double,
        grade: String,
        state: String,
        district: String,
        mandal: String,
        availabilityDays: Int,
        storageAvailable: Boolean,
        onComplete: () -> Unit
    ) {
        val newLot = ProduceLot(
            id = "AW-${crop.name.take(3)}-2026-${(100..999).random()}",
            crop = crop,
            quantityTonnes = quantity,
            grade = grade,
            state = state,
            district = district,
            mandal = mandal.ifEmpty { "District Central" },
            availabilityDays = availabilityDays,
            storageAvailable = storageAvailable,
            moisturePercent = when (crop) {
                CropType.RED_CHILLI -> 10.2
                CropType.TOMATO -> 88.0
                CropType.PADDY -> 13.5
                CropType.TURMERIC -> 11.0
                CropType.GROUNDNUT -> 7.8
                CropType.COTTON -> 8.5
            },
            damagePercent = 1.2,
            colorGrade = "Verified Standard $grade",
            certification = "APEDA / Spices Board Registered",
            ownerName = _currentUser.value.organization,
            harvestDate = "Available in $availabilityDays days"
        )
        _currentLot.value = newLot
        onComplete()
    }

    fun resetToDemoScenario() {
        _currentLot.value = defaultLot
        _offers.value = defaultOffers
        _markets.value = defaultMarkets
        _exportChecklist.value = defaultExportChecklist
    }

    fun counterOffer(offerId: String, counterPrice: Double, notes: String) {
        _offers.update { list ->
            list.map { offer ->
                if (offer.id == offerId) {
                    offer.copy(
                        status = OfferStatus.COUNTERED,
                        counterPricePerQuintal = counterPrice,
                        counterNotes = notes
                    )
                } else offer
            }
        }
    }

    fun acceptOffer(offerId: String) {
        _offers.update { list ->
            list.map { offer ->
                if (offer.id == offerId) {
                    offer.copy(status = OfferStatus.ACCEPTED)
                } else offer
            }
        }
    }

    fun toggleExportCheckItem(id: String) {
        _exportChecklist.update { list ->
            list.map { item ->
                if (item.id == id) item.copy(isCompleted = !item.isCompleted) else item
            }
        }
    }

    fun markNotificationsAsRead() {
        _notifications.update { list ->
            list.map { it.copy(isUnread = false) }
        }
    }

    fun adminUpdatePrice(marketId: String, newHeadlinePrice: Double) {
        _markets.update { list ->
            list.map { market ->
                if (market.id == marketId) {
                    market.copy(headlinePricePerQuintal = newHeadlinePrice)
                } else market
            }
        }
    }
}
