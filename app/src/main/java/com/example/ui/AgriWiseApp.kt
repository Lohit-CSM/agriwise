package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AgriRepository
import com.example.model.NotificationItem
import com.example.ui.components.SihTopBanner
import com.example.ui.screens.*
import com.example.ui.theme.*

enum class AppDestination(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    LANDING("Welcome", Icons.Default.Home),
    DASHBOARD("Dashboard", Icons.Default.Dashboard),
    CREATE_LOT("New Lot", Icons.Default.AddCircle),
    PRICE_DISCOVERY("Price Engine", Icons.Default.MonetizationOn),
    MARKETS("Markets", Icons.Default.Storefront),
    OFFERS("Offers", Icons.Default.Gavel),
    AGGREGATION("Aggregation", Icons.Default.GroupWork),
    GLOBAL("Global", Icons.Default.Public),
    EXPORT_READINESS("Export", Icons.Default.FactCheck),
    PASSPORT("Passport", Icons.Default.QrCode),
    ANALYTICS("Trends", Icons.Default.ShowChart),
    LOGIN("Login", Icons.Default.AccountCircle)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgriWiseApp() {
    var currentScreen by remember { mutableStateOf(AppDestination.LANDING) }
    val currentLot by AgriRepository.currentLot.collectAsState()
    val notifications by AgriRepository.notifications.collectAsState()
    val currentLanguage by AgriRepository.currentLanguage.collectAsState()

    var showNotificationsSheet by remember { mutableStateOf(false) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    var showSmsIvrDialog by remember { mutableStateOf(false) }
    var showAdminDialog by remember { mutableStateOf(false) }
    var showDemoToast by remember { mutableStateOf(false) }

    val unreadNotifsCount = notifications.count { it.isUnread }

    // Start Demo Flow helper
    fun runSihDemo() {
        AgriRepository.resetToDemoScenario()
        showDemoToast = true
        currentScreen = AppDestination.PRICE_DISCOVERY
    }

    Scaffold(
        topBar = {
            Column {
                // Persistent SIH 2026 Top Ribbon
                SihTopBanner(onStartDemoClick = { runSihDemo() })

                // Main App TopAppBar
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                color = AgriMintGreen,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Eco,
                                        contentDescription = "AgriWise Logo",
                                        tint = AgriDeepGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "AGRIWISE",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "From Farm Supply to Global Demand",
                                    fontSize = 9.sp,
                                    color = AgriLightGreen
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AgriForestGreen,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    actions = {
                        // Language Dropdown Selector
                        Box {
                            IconButton(onClick = { showLanguageMenu = true }) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = "Language",
                                    tint = Color.White
                                )
                            }
                            DropdownMenu(
                                expanded = showLanguageMenu,
                                onDismissRequest = { showLanguageMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("English (Default)") },
                                    onClick = {
                                        AgriRepository.setLanguage("English")
                                        showLanguageMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("తెలుగు (Telugu)") },
                                    onClick = {
                                        AgriRepository.setLanguage("Telugu")
                                        showLanguageMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("हिंदी (Hindi)") },
                                    onClick = {
                                        AgriRepository.setLanguage("Hindi")
                                        showLanguageMenu = false
                                    }
                                )
                            }
                        }

                        // SMS / IVR showcase modal
                        IconButton(onClick = { showSmsIvrDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Sms,
                                contentDescription = "SMS & IVR Inclusion",
                                tint = Color.White
                            )
                        }

                        // Notifications with unread badge
                        IconButton(onClick = { showNotificationsSheet = true }) {
                            BadgedBox(
                                badge = {
                                    if (unreadNotifsCount > 0) {
                                        Badge(containerColor = AgriChilliRed) {
                                            Text("$unreadNotifsCount")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White
                                )
                            }
                        }

                        // Admin & Judge Controls
                        IconButton(onClick = { showAdminDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "Simulation Console",
                                tint = AgriGoldAccent
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = AgriSurface,
                contentColor = AgriForestGreen,
                tonalElevation = 8.dp
            ) {
                val navItems = listOf(
                    AppDestination.DASHBOARD,
                    AppDestination.PRICE_DISCOVERY,
                    AppDestination.MARKETS,
                    AppDestination.OFFERS,
                    AppDestination.AGGREGATION,
                    AppDestination.GLOBAL
                )

                navItems.forEach { dest ->
                    val isSelected = currentScreen == dest
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = dest },
                        icon = {
                            Icon(
                                imageVector = dest.icon,
                                contentDescription = dest.label
                            )
                        },
                        label = {
                            Text(
                                text = dest.label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AgriDeepGreen,
                            selectedTextColor = AgriDeepGreen,
                            indicatorColor = AgriLightGreen,
                            unselectedIconColor = AgriTextMuted,
                            unselectedTextColor = AgriTextMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Screen Routing
            when (currentScreen) {
                AppDestination.LANDING -> {
                    LandingScreen(
                        onFindMarketClick = { currentScreen = AppDestination.CREATE_LOT },
                        onStartSihDemoClick = { runSihDemo() },
                        onViewFeaturesClick = { currentScreen = AppDestination.DASHBOARD }
                    )
                }

                AppDestination.LOGIN -> {
                    LoginScreen(
                        onLoginSuccess = { currentScreen = AppDestination.DASHBOARD }
                    )
                }

                AppDestination.DASHBOARD -> {
                    DashboardScreen(
                        onCreateLotClick = { currentScreen = AppDestination.CREATE_LOT },
                        onPriceDiscoveryClick = { currentScreen = AppDestination.PRICE_DISCOVERY },
                        onMarketsClick = { currentScreen = AppDestination.MARKETS },
                        onOffersClick = { currentScreen = AppDestination.OFFERS },
                        onAggregationClick = { currentScreen = AppDestination.AGGREGATION },
                        onGlobalClick = { currentScreen = AppDestination.GLOBAL },
                        onExportReadinessClick = { currentScreen = AppDestination.EXPORT_READINESS },
                        onPassportClick = { currentScreen = AppDestination.PASSPORT }
                    )
                }

                AppDestination.CREATE_LOT -> {
                    CreateLotScreen(
                        onLotCreated = { currentScreen = AppDestination.PRICE_DISCOVERY }
                    )
                }

                AppDestination.PRICE_DISCOVERY -> {
                    PriceDiscoveryScreen(
                        onExploreMarketsClick = { currentScreen = AppDestination.MARKETS },
                        onViewBuyerOffersClick = { currentScreen = AppDestination.OFFERS }
                    )
                }

                AppDestination.MARKETS -> {
                    MarketDiscoveryScreen(
                        onSelectMarket = { /* Handled in screen */ }
                    )
                }

                AppDestination.OFFERS -> {
                    BuyerOffersScreen()
                }

                AppDestination.AGGREGATION -> {
                    AggregationScreen()
                }

                AppDestination.GLOBAL -> {
                    GlobalMarketScreen(
                        onCheckReadinessClick = { currentScreen = AppDestination.EXPORT_READINESS }
                    )
                }

                AppDestination.EXPORT_READINESS -> {
                    ExportReadinessScreen()
                }

                AppDestination.PASSPORT -> {
                    ProducePassportScreen()
                }

                AppDestination.ANALYTICS -> {
                    PriceAnalyticsScreen()
                }
            }

            // Quick Floating Demo Badge Notification
            if (showDemoToast) {
                Surface(
                    color = AgriDeepGreen,
                    shape = RoundedCornerShape(20.dp),
                    shadowElevation = 6.dp,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = AgriMintGreen, modifier = Modifier.size(16.dp))
                        Text(
                            text = "SIH Demo Scenario Loaded: 8T Red Chilli (Guntur AP)",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { showDemoToast = false },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }

    // Notifications Dialog Sheet
    if (showNotificationsSheet) {
        AlertDialog(
            onDismissRequest = {
                AgriRepository.markNotificationsAsRead()
                showNotificationsSheet = false
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Market & Buyer Alerts", fontWeight = FontWeight.Bold)
                    TextButton(onClick = { AgriRepository.markNotificationsAsRead() }) {
                        Text("Mark Read", fontSize = 11.sp, color = AgriForestGreen)
                    }
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    notifications.forEach { item ->
                        Surface(
                            onClick = {
                                when (item.targetTab) {
                                    "offers" -> currentScreen = AppDestination.OFFERS
                                    "aggregation" -> currentScreen = AppDestination.AGGREGATION
                                    "global" -> currentScreen = AppDestination.GLOBAL
                                    "price" -> currentScreen = AppDestination.PRICE_DISCOVERY
                                }
                                showNotificationsSheet = false
                            },
                            color = if (item.isUnread) AgriUltraLightGreen else AgriSurfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item.title,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AgriTextPrimary
                                    )
                                    Text(text = item.timeAgo, fontSize = 9.sp, color = AgriTextMuted)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.message,
                                    fontSize = 11.sp,
                                    color = AgriTextSecondary,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        AgriRepository.markNotificationsAsRead()
                        showNotificationsSheet = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AgriForestGreen)
                ) {
                    Text("Close")
                }
            }
        )
    }

    // SMS & IVR Dialog
    if (showSmsIvrDialog) {
        SmsIvrDialog(onDismiss = { showSmsIvrDialog = false })
    }

    // Admin & Simulation Console Dialog
    if (showAdminDialog) {
        AdminPanelDialog(onDismiss = { showAdminDialog = false })
    }
}
