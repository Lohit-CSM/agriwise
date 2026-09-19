package com.example.model

enum class UserRole {
    FARMER,
    FPO,
    BUYER
}

data class UserProfile(
    val id: String,
    val name: String,
    val role: UserRole,
    val organization: String,
    val location: String,
    val phone: String,
    val isVerified: Boolean = true
)

enum class CropType(val displayName: String, val unitPriceBase: Double, val defaultGrade: String) {
    RED_CHILLI("Red Chilli", 18500.0, "Grade A (Teja / 334)"),
    TOMATO("Tomato", 2200.0, "Grade A (Hybrid Semi-ripe)"),
    PADDY("Paddy", 2400.0, "Grade A (BPT 5204 Sona Masoori)"),
    TURMERIC("Turmeric", 14200.0, "Grade A (Salem / Nizamabad)"),
    GROUNDNUT("Groundnut", 6800.0, "Grade A (Bold 80/90 count)"),
    COTTON("Cotton", 7400.0, "Grade A (Medium Staple 29mm)")
}

data class ProduceLot(
    val id: String = "AW-CHL-2026-001",
    val crop: CropType = CropType.RED_CHILLI,
    val quantityTonnes: Double = 8.0,
    val grade: String = "Grade A",
    val state: String = "Andhra Pradesh",
    val district: String = "Guntur",
    val mandal: String = "Tenali",
    val availabilityDays: Int = 3,
    val storageAvailable: Boolean = true,
    val moisturePercent: Double = 10.5,
    val damagePercent: Double = 1.2,
    val colorGrade: String = "Deep Crimson Red (ASTA 120)",
    val certification: String = "Spices Board Registered / GAP Verified",
    val ownerName: String = "Guntur Rythu FPO Producer Co.",
    val harvestDate: String = "Ready in 3 Days (21 Sep 2026)"
)

enum class MarketCategory {
    NEARBY,
    STATE,
    NATIONAL,
    GLOBAL
}

data class MarketOpportunity(
    val id: String,
    val name: String,
    val category: MarketCategory,
    val location: String,
    val distanceKm: Int,
    val demandLevel: String, // "High", "Very High", "Moderate"
    val headlinePricePerQuintal: Double,
    val logisticsCostPerQuintal: Double,
    val handlingCostPerQuintal: Double,
    val marketFeesPerQuintal: Double,
    val otherApplicableCostsPerQuintal: Double,
    val qualityRequirement: String,
    val buyerInterestCount: Int,
    val priceFitScore: Int,      // 0..100
    val demandFitScore: Int,     // 0..100
    val qualityFitScore: Int,    // 0..100
    val logisticsFitScore: Int,  // 0..100
    val buyerReliabilityScore: Int, // 0..100
    val whyExplainableNotes: List<String>
) {
    val estimatedRealizableValue: Double
        get() = headlinePricePerQuintal - (logisticsCostPerQuintal + handlingCostPerQuintal + marketFeesPerQuintal + otherApplicableCostsPerQuintal)

    val opportunityScore: Int
        get() = ((priceFitScore * 0.30) + (demandFitScore * 0.25) + (qualityFitScore * 0.20) + (logisticsFitScore * 0.15) + (buyerReliabilityScore * 0.10)).toInt()
}

data class Buyer(
    val id: String,
    val name: String,
    val companyType: String, // "Food Processor", "Spice Exporter", "National Retail Chain", "FMCG Manufacturer"
    val location: String,
    val requiredCrop: String,
    val minQuantityTonnes: Double,
    val maxQuantityTonnes: Double,
    val matchScorePercent: Int,
    val indicativeOfferPerQuintal: Double,
    val reliabilityBadge: String, // "Verified Enterprise", "Spices Board Approved", "A+ Rated"
    val responseTimeHours: Int,
    val contactPerson: String
)

data class BuyerOffer(
    val id: String,
    val buyerId: String,
    val buyerName: String,
    val buyerType: String,
    val location: String,
    val pricePerQuintal: Double,
    val requestedQuantityTonnes: Double,
    val pickupDeliveryTerm: String, // "Farmgate Pickup Included", "Direct Mandi Delivery"
    val qualitySpec: String,
    val paymentTerms: String, // "Direct Bank Transfer (T+1 day)", "Escrow release on inspection"
    val verificationStatus: String,
    val expiryDays: Int,
    val status: OfferStatus = OfferStatus.PENDING,
    val counterPricePerQuintal: Double? = null,
    val counterNotes: String? = null
)

enum class OfferStatus {
    PENDING,
    COUNTERED,
    ACCEPTED,
    DECLINED
}

data class AggregationLot(
    val id: String,
    val farmerOrFpoName: String,
    val location: String,
    val distanceKm: Int,
    val quantityTonnes: Double,
    val grade: String
)

data class AggregationGroup(
    val targetBuyerName: String,
    val institutionalRequirementTonnes: Double,
    val userLotTonnes: Double,
    val nearbyLots: List<AggregationLot>,
    val estimatedLogisticsSavingPerQuintal: Double,
    val estimatedBargainingBenefitPercent: Double
) {
    val totalAggregatedTonnes: Double
        get() = userLotTonnes + nearbyLots.sumOf { it.quantityTonnes }

    val isRequirementMet: Boolean
        get() = totalAggregatedTonnes >= institutionalRequirementTonnes
}

data class GlobalOpportunity(
    val id: String,
    val destinationCountry: String,
    val flagEmoji: String,
    val targetCommodity: String,
    val demandLevel: String,
    val indicativeInternationalPricePerQuintalEq: Double,
    val qualityRequirements: String,
    val logisticsComplexity: String, // "Moderate", "High", "Very High"
    val exportReadinessStatus: String, // "Partial Match (65%)", "High Potential (85%)"
    val potentialFit: String, // "Strong", "Moderate"
    val keyPort: String
)

data class ExportCheckItem(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val isCritical: Boolean
)

data class PriceHistoryPoint(
    val dayLabel: String,
    val indicativeHeadline: Double,
    val estimatedRealizable: Double,
    val localMandiPrice: Double
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val isUnread: Boolean = true,
    val targetTab: String = "offers"
)
