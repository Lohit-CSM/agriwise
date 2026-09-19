package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.GeminiChatService
import com.example.data.AgriRepository
import com.example.model.ChatMessage
import com.example.model.MessageSender
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun AiChatbotScreen(
    onNavigateBack: () -> Unit = {},
    onCallSupportClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val initialMessages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = MessageSender.AI_BOT,
                text = "Namaste! I am AgriWise AI, your intelligent farming and market advisor. You have an active lot of 8 Tonnes Red Chilli (Grade A) in Guntur. How can I assist you with price discovery, buyer offers, or quality grading today?",
                suggestedActions = listOf(
                    "What is my net realizable price?",
                    "Check buyer offers on my lot",
                    "Is today good for harvesting & drying?",
                    "How to prepare for export to UAE?"
                )
            )
        )
    }

    var inputText by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }

    fun sendMessage(textToSend: String) {
        val trimmed = textToSend.trim()
        if (trimmed.isEmpty()) return

        val userMessage = ChatMessage(sender = MessageSender.USER, text = trimmed)
        initialMessages.add(userMessage)
        inputText = ""

        coroutineScope.launch {
            isThinking = true
            listState.animateScrollToItem(initialMessages.size - 1)

            val replyText = GeminiChatService.getAiResponse(trimmed, initialMessages.toList())
            isThinking = false
            initialMessages.add(
                ChatMessage(
                    sender = MessageSender.AI_BOT,
                    text = replyText,
                    suggestedActions = listOf(
                        "Explain freight deductions",
                        "View verified buyer list",
                        "24/7 Call Support"
                    )
                )
            )
            listState.animateScrollToItem(initialMessages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = AgriForestGreen,
                contentColor = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = AgriMintGreen,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = "AgriWise AI",
                                    tint = AgriDeepGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "AgriWise AI Advisory",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = AgriEmeraldAccent,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "GEMINI 3.5",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = AgriDeepGreen,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Instant Crop Economics, Quality & Mandi Answers",
                                fontSize = 10.sp,
                                color = AgriLightGreen
                            )
                        }
                    }

                    // 24/7 Call Support Quick Action Button
                    Button(
                        onClick = onCallSupportClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AgriGoldAccent,
                            contentColor = Color(0xFF261A00)
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "24/7 Call", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = AgriSurface,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
            ) {
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    // Chat Input Box
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { Text("Ask about prices, buyers, moisture, weather...", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AgriForestGreen,
                                unfocusedBorderColor = AgriBorder,
                                focusedContainerColor = AgriSurfaceVariant,
                                unfocusedContainerColor = AgriSurfaceVariant
                            ),
                            maxLines = 3
                        )

                        FilledIconButton(
                            onClick = { sendMessage(inputText) },
                            enabled = inputText.isNotBlank() && !isThinking,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = AgriForestGreen,
                                contentColor = Color.White,
                                disabledContainerColor = AgriBorder,
                                disabledContentColor = AgriTextMuted
                            ),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxSize()
                .background(AgriBackground)
                .padding(innerPadding)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(initialMessages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg,
                    onActionClick = { actionText ->
                        if (actionText == "24/7 Call Support") {
                            onCallSupportClick()
                        } else {
                            sendMessage(actionText)
                        }
                    }
                )
            }

            if (isThinking) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    ) {
                        CircularProgressIndicator(
                            color = AgriForestGreen,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "AgriWise AI is synthesizing market data...",
                            fontSize = 11.sp,
                            color = AgriTextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(
    message: ChatMessage,
    onActionClick: (String) -> Unit
) {
    val isUser = message.sender == MessageSender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {
            if (!isUser) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(AgriForestGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = AgriMintGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
            }

            Surface(
                color = if (isUser) AgriForestGreen else AgriSurface,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, AgriBorder) else null,
                shadowElevation = if (!isUser) 1.dp else 0.dp
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = message.text,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = if (isUser) Color.White else AgriTextPrimary
                    )
                }
            }
        }

        // Suggested Action Chips if available from AI
        if (!isUser && message.suggestedActions.isNotEmpty()) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 34.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    message.suggestedActions.forEach { action ->
                        Surface(
                            onClick = { onActionClick(action) },
                            color = AgriUltraLightGreen,
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AgriMintGreen.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = action,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AgriDeepGreen,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
