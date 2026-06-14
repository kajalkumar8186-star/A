package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Language(val displayName: String, val extension: String) {
    REACT_TAILWIND("React + Tailwind CSS", "jsx"),
    JETPACK_COMPOSE("Jetpack Compose", "kt"),
    FLUTTER_DART("Flutter + Dart", "dart")
}

data class WireframeTemplate(
    val id: String,
    val name: String,
    val fileName: String,
    val description: String,
    val mockLayout: @Composable (Color) -> Unit,
    val codeSnippet: Map<Language, String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()

    // ----------------------------------------------------
    // Templates Data Structure
    // ----------------------------------------------------
    val templates = remember {
        listOf(
            WireframeTemplate(
                id = "login",
                name = "Login Form",
                fileName = "wireframe_login.png",
                description = "Classic username, email, password entry card.",
                mockLayout = { buttonColor ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                color = Color(0xFF1E293B),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // Fake Email Input
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Title", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                        
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        // Fake Password Input
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text("Inputs", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Action Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .background(buttonColor, RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Button", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                },
                codeSnippet = mapOf(
                    Language.REACT_TAILWIND to """
                        import React from 'react';
                        import { React } from './poppomments';
                        import Tailwind from './reancat/reac';
                        
                        export function main() {
                          return (
                            <div>
                              <div class="tailwind-color-and">
                                <div class="talw">
                                <div class="sat/">
                                <Button class="pi">
                              </div>
                            </div>
                          );
                        }
                    """.trimIndent(),
                    Language.JETPACK_COMPOSE to """
                        @Composable
                        fun LoginCard(modifier: Modifier = Modifier) {
                            Card(
                                modifier = modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Login", fontWeight = FontWeight.Bold)
                                    OutlinedTextField(value = "", label = { Text("Title") })
                                    OutlinedTextField(value = "", label = { Text("Inputs") })
                                    Button(onClick = {}) { Text("Button") }
                                }
                            }
                        }
                    """.trimIndent(),
                    Language.FLUTTER_DART to """
                        Widget build(BuildContext context) {
                          return Card(
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                            child: Padding(
                              padding: const EdgeInsets.all(16.0),
                              child: Column(
                                children: [
                                  Text('Login', style: TextStyle(fontWeight: FontWeight.bold)),
                                  TextField(decoration: InputDecoration(labelText: 'Title')),
                                  TextField(decoration: InputDecoration(labelText: 'Inputs')),
                                  ElevatedButton(onPressed: () {}, child: Text('Button')),
                                ],
                              ),
                            ),
                          );
                        }
                    """.trimIndent()
                )
            ),
            WireframeTemplate(
                id = "dashboard",
                name = "Dashboard Chart",
                fileName = "wireframe_dashboard.png",
                description = "Modern analytics panel with summary indicators.",
                mockLayout = { buttonColor ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Sales Hub", color = Color(0xFF1E293B), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFFDCFCE7), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text("+24%", color = Color(0xFF15803D), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        
                        // Fake Circular Graph
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(56.dp)
                                .drawBehind {
                                    drawArc(
                                        color = Color(0xFFE2E8F0),
                                        startAngle = 0f,
                                        sweepAngle = 360f,
                                        useCenter = false,
                                        style = Stroke(width = 12f)
                                    )
                                    drawArc(
                                        color = buttonColor,
                                        startAngle = -90f,
                                        sweepAngle = 240f,
                                        useCenter = false,
                                        style = Stroke(width = 12f)
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("67%", color = Color(0xFF1E293B), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Divider(color = Color(0xFFE2E8F0))
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Revenue stats", color = Color(0xFF64748B), fontSize = 10.sp)
                            Text("${'$'}14,230.00", color = Color(0xFF1E293B), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                codeSnippet = mapOf(
                    Language.REACT_TAILWIND to """
                        import React from 'react';
                        
                        export default function DashboardCard() {
                          return (
                            <div className="bg-slate-900 border border-slate-800 p-6 rounded-2xl text-white">
                              <div className="flex justify-between items-center mb-4">
                                <h3 className="font-bold text-lg">Sales Hub</h3>
                                <span className="bg-emerald-500/10 text-emerald-400 px-2 py-0.5 rounded text-xs font-bold">+24%</span>
                              </div>
                              <div className="relative w-28 h-28 mx-auto flex items-center justify-center">
                                <svg className="w-full h-full transform -rotate-90">
                                  <circle cx="56" cy="56" r="40" stroke="#1e293b" strokeWidth="8" fill="transparent" />
                                  <circle cx="56" cy="56" r="40" stroke="#3b82f6" strokeWidth="8" fill="transparent" strokeDasharray="251.2" strokeDashoffset="82" />
                                </svg>
                                <span className="absolute text-xl font-extrabold">67%</span>
                              </div>
                              <div className="border-t border-slate-800 mt-5 pt-4 text-center">
                                <span className="text-slate-400 text-xs">Revenue stats: </span>
                                <span className="text-xl font-bold">${'$'}14,230.00</span>
                              </div>
                            </div>
                          );
                        }
                    """.trimIndent(),
                    Language.JETPACK_COMPOSE to """
                        @Composable
                        fun AnalyticsCard(modifier: Modifier = Modifier) {
                            Card(
                                modifier = modifier.padding(16.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("Sales Hub", fontWeight = FontWeight.Black)
                                        Box(Modifier.background(Color(0xFFDCFCE7))) {
                                            Text("+24%", color = Color(0xFF15803D))
                                        }
                                    }
                                    Box(contentAlignment = Alignment.Center) {
                                        Canvas(modifier = Modifier.size(80.dp)) {
                                            drawArc(Color.LightGray, 0f, 360f, false, style = Stroke(4.dp.toPx()))
                                            drawArc(Color.Blue, -90f, 240f, false, style = Stroke(4.dp.toPx()))
                                        }
                                        Text("67%", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    """.trimIndent(),
                    Language.FLUTTER_DART to """
                        Widget build(BuildContext context) {
                          return Container(
                            decoration: BoxDecoration(color: Colors.blueGrey[900]),
                            padding: EdgeInsets.all(16),
                            child: Column(
                              children: [
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text('Sales Hub', style: TextStyle(fontWeight: FontWeight.bold)),
                                    Chip(label: Text('+24%'), backgroundColor: Colors.greenAccent[100]),
                                  ],
                                ),
                                SizedBox(height: 16),
                                Stack(
                                  alignment: Alignment.center,
                                  children: [
                                    CircularProgressIndicator(value: 0.67, strokeWidth: 8),
                                    Text('67%', style: TextStyle(fontWeight: FontWeight.bold)),
                                  ],
                                )
                              ],
                            ),
                          );
                        }
                    """.trimIndent()
                )
            ),
            WireframeTemplate(
                id = "product",
                name = "Product Card",
                fileName = "wireframe_product.png",
                description = "Clean listing showcasing item graphics, title and CTA.",
                mockLayout = { buttonColor ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        // Product image container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .background(Color(0xFFF1F5F9), RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🖼️ Image placeholder", color = Color(0xFF94A3B8), fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Sleek Tech Headphones", color = Color(0xFF1E293B), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                        Text("High fidelity sound capture", color = Color(0xFF64748B), fontSize = 9.sp, maxLines = 1)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${'$'}189.00", color = Color(0xFF1E293B), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Box(
                                modifier = Modifier
                                    .background(buttonColor, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Get", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                },
                codeSnippet = mapOf(
                    Language.REACT_TAILWIND to """
                        import React from 'react';
                        
                        export default function ProductCard() {
                          return (
                            <div className="bg-slate-900 border border-slate-800 p-4 rounded-xl text-white w-64">
                              <div className="h-28 bg-slate-800 rounded-lg flex items-center justify-center text-slate-400 text-xs text-center mb-3">
                                🖼️ Image placeholder
                              </div>
                              <h4 className="font-extrabold text-sm truncate">Sleek Tech Headphones</h4>
                              <p className="text-slate-400 text-[10px] my-1 truncate">High fidelity sound capture</p>
                              <div className="flex justify-between items-center mt-3">
                                <span className="font-black text-blue-400 text-base">${'$'}189.00</span>
                                <button className="bg-blue-600 hover:bg-blue-500 rounded px-2.5 py-1 text-xs font-bold transition">Get</button>
                              </div>
                            </div>
                          );
                        }
                    """.trimIndent(),
                    Language.JETPACK_COMPOSE to """
                        @Composable
                        fun ProductCard(modifier: Modifier = Modifier) {
                            Card(
                                modifier = modifier.width(180.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray))
                                    Text("Sleek Tech Headphones", fontWeight = FontWeight.Bold)
                                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("${'$'}189.00")
                                        Button(onClick = {}) { Text("Get") }
                                    }
                                }
                            }
                        }
                    """.trimIndent(),
                    Language.FLUTTER_DART to """
                        Widget build(BuildContext context) {
                          return Card(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                AspectRatio(aspectRatio: 16/9, child: Container(color: Colors.grey)),
                                Padding(
                                  padding: const EdgeInsets.all(8.0),
                                  child: Column(
                                    children: [
                                      Text('Sleek Tech Headphones', style: TextStyle(fontWeight: FontWeight.bold)),
                                      Row(
                                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                        children: [
                                          Text('${'$'}189.00', style: TextStyle(color: Colors.blue)),
                                          ElevatedButton(onPressed: () {}, child: Text('Get')),
                                        ],
                                      )
                                    ],
                                  ),
                                )
                              ],
                            ),
                          );
                        }
                    """.trimIndent()
                )
            )
        )
    }

    // ----------------------------------------------------
    // App States
    // ----------------------------------------------------
    var selectedTemplateIndex by remember { mutableStateOf(0) }
    var selectedLanguage by remember { mutableStateOf(Language.REACT_TAILWIND) }
    var isSettingsOpen by remember { mutableStateOf(false) }
    var isSearchQuery by remember { mutableStateOf("") }
    var showUploadModal by remember { mutableStateOf(false) }
    var selectedNavBarItem by remember { mutableStateOf(0) } // Mock Bottom Nav Bar (0: Home, 1: Projects, 2: Notifications)
    var searchBarExpanded by remember { mutableStateOf(false) }

    val currentTemplate = templates[selectedTemplateIndex]

    // Code Contrast Mod State
    var highContrastMode by remember { mutableStateOf(false) }

    // Visual Styling Accent Color mapping from Theme
    val activeBrandColor = if (highContrastMode) Color(0xFF00E5FF) else TechBlue

    // Backdrop blur representation anim state
    val blurRadius by animateFloatAsState(
        targetValue = if (isSettingsOpen || showUploadModal) 10f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    // Layout Root Box Container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SlateBg)
    ) {
        // Main Screen Frame
        Scaffold(
            topBar = {
                // Header Area with Safe margins (edge-to-edge handling)
                Column(
                    modifier = Modifier
                        .background(SlateBg)
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Custom Logo & Name Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    Toast
                                        .makeText(context, "Welcome to ImageToCode AI!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                        ) {
                            // custom graphic `< />` logo
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(activeBrandColor.copy(alpha = 0.15f))
                                    .border(1.5.dp, activeBrandColor, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "</>",
                                    color = activeBrandColor,
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(10.dp))
                            
                            Text(
                                text = "ImageToCode AI",
                                style = TextStyle(
                                    color = OnSlateText,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.3.sp
                                )
                            )
                        }

                        // Search and Settings/Profile Icons row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Search Button / Extended Textfield
                            IconButton(
                                onClick = { searchBarExpanded = !searchBarExpanded },
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(SlateCard.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = OnSlateText
                                )
                            }

                            // Profile/Account click triggers Settings panel popup
                            IconButton(
                                onClick = { isSettingsOpen = true },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(activeBrandColor.copy(alpha = 0.2f))
                                    .border(1.5.dp, activeBrandColor, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Settings Menu",
                                    tint = OnSlateText
                                )
                            }
                        }
                    }

                    // Collapsible Search Input Row
                    AnimatedVisibility(
                        visible = searchBarExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                .background(SlateCard, RoundedCornerShape(8.dp))
                                .border(1.dp, BorderGray, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Search, contentDescription = null, tint = OnSlateSubText, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                BasicTextField(
                                    value = isSearchQuery,
                                    onValueChange = { indexText ->
                                        isSearchQuery = indexText
                                    },
                                    textStyle = TextStyle(color = OnSlateText, fontSize = 14.sp),
                                    modifier = Modifier.weight(1f),
                                    decorationBox = { innerTextField ->
                                        if (isSearchQuery.isEmpty()) {
                                            Text("Search wireframe components...", color = OnSlateSubText, fontSize = 14.sp)
                                        }
                                        innerTextField()
                                    }
                                )
                                if (isSearchQuery.isNotEmpty()) {
                                    IconButton(
                                        onClick = { isSearchQuery = "" },
                                        modifier = Modifier.size(18.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = OnSlateSubText)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                // Mock Platform M3 standard bottom navigation bar
                NavigationBar(
                    containerColor = SlateCard,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.border(layerBorderStroke(), color = BorderGray)
                ) {
                    NavigationBarItem(
                        selected = selectedNavBarItem == 0,
                        onClick = { selectedNavBarItem = 0 },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = activeBrandColor,
                            selectedTextColor = activeBrandColor,
                            unselectedIconColor = OnSlateSubText,
                            unselectedTextColor = OnSlateSubText,
                            indicatorColor = activeBrandColor.copy(alpha = 0.15f)
                        )
                    )
                    NavigationBarItem(
                        selected = selectedNavBarItem == 1,
                        onClick = {
                            selectedNavBarItem = 1
                            Toast.makeText(context, "Projects archive: offline mode cached assets shown.", Toast.LENGTH_SHORT).show()
                        },
                        icon = { Icon(Icons.Default.Menu, contentDescription = "Projects") }, // Using safe default menu icon
                        label = { Text("Projects", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = activeBrandColor,
                            selectedTextColor = activeBrandColor,
                            unselectedIconColor = OnSlateSubText,
                            unselectedTextColor = OnSlateSubText,
                            indicatorColor = activeBrandColor.copy(alpha = 0.15f)
                        )
                    )
                    NavigationBarItem(
                        selected = selectedNavBarItem == 2,
                        onClick = {
                            selectedNavBarItem = 2
                            Toast.makeText(context, "No new generation events available.", Toast.LENGTH_SHORT).show()
                        },
                        icon = { Icon(Icons.Default.Notifications, contentDescription = "Notifications") },
                        label = { Text("Notifications", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = activeBrandColor,
                            selectedTextColor = activeBrandColor,
                            unselectedIconColor = OnSlateSubText,
                            unselectedTextColor = OnSlateSubText,
                            indicatorColor = activeBrandColor.copy(alpha = 0.15f)
                        )
                    )
                }
            },
            containerColor = SlateBg
        ) { innerPadding ->
            // Outer scroll container avoiding overlay clips
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .then(if (blurRadius > 0) Modifier.blur(blurRadius.dp) else Modifier),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // 1. App Title / Marketing Banner Header
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            text = "ImageToCode AI",
                            style = TextStyle(
                                color = OnSlateText,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                letterSpacing = (-0.5).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Transform Images into Code Effortlessly",
                            style = TextStyle(
                                color = OnSlateSubText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                // 2. Main Large Upload Action Trigger Button
                item {
                    Button(
                        onClick = { showUploadModal = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = activeBrandColor),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "+ Upload Image",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                // 3. "How It Works" Workflow Section
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SlateCard, RoundedCornerShape(16.dp))
                            .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "How It Works",
                            style = TextStyle(
                                color = OnSlateText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // 3 steps layout in a fluid row (horizontal layout matching model)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Step 1 CARD
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SlateBg)
                                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Custom camera/photo vector
                                    Icon(
                                        imageVector = Icons.Default.Search, // Using safe search/lens analog in box
                                        contentDescription = "Image logo",
                                        tint = activeBrandColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "1. Upload Image",
                                    style = TextStyle(color = OnSlateText, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                )
                                Spacer(modifier = Modifier.SpacerHeightPercent())
                                Text(
                                    text = "Select image or wireframe",
                                    style = TextStyle(color = OnSlateSubText, fontSize = 9.sp, textAlign = TextAlign.Center),
                                    maxLines = 2,
                                    minLines = 2
                                )
                            }

                            // Connecting Arrow
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "next step",
                                tint = OnSlateSubText.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(16.dp)
                            )

                            // Step 2 CARD
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SlateBg)
                                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // custom globe/language selection representor
                                    Text("JS", color = activeBrandColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "2. Choose Language",
                                    style = TextStyle(color = OnSlateText, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                )
                                Spacer(modifier = Modifier.SpacerHeightPercent())
                                Text(
                                    text = "Pick Framework/Language",
                                    style = TextStyle(color = OnSlateSubText, fontSize = 9.sp, textAlign = TextAlign.Center),
                                    maxLines = 2,
                                    minLines = 2
                                )
                            }

                            // Connecting Arrow
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "next step",
                                tint = OnSlateSubText.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(16.dp)
                            )

                            // Step 3 CARD
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SlateBg)
                                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Verify",
                                        tint = activeBrandColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "3. Get Code",
                                    style = TextStyle(color = OnSlateText, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                )
                                Spacer(modifier = Modifier.SpacerHeightPercent())
                                Text(
                                    text = "Download your generated code",
                                    style = TextStyle(color = OnSlateSubText, fontSize = 9.sp, textAlign = TextAlign.Center),
                                    maxLines = 2,
                                    minLines = 2
                                )
                            }
                        }
                    }
                }

                // 4. "Code Preview" Live Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Code Preview",
                            style = TextStyle(
                                color = OnSlateText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Language / Target Framework Picker Pills (Choose Language action live!)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Language.values().forEach { lang ->
                                val isSelected = selectedLanguage == lang
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) activeBrandColor else SlateCard)
                                        .border(1.dp, if (isSelected) Color.Transparent else BorderGray, RoundedCornerShape(8.dp))
                                        .clickable { selectedLanguage = lang }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = lang.displayName.split(" ")[0], // Display short language name e.g. React
                                        color = if (isSelected) Color.White else OnSlateSubText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        // Preview Content Cards Root Frame (Grid Layout simulated - Mock layout side-by-side)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Column left: Mock UI panel
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .background(SlateCard, RoundedCornerShape(12.dp))
                                        .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                                ) {
                                    // Title label
                                    Text(
                                        text = "Mock UI",
                                        color = OnSlateSubText,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                                    )

                                    // Dynamic Draw of Selected Mock layout
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 6.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        currentTemplate.mockLayout(activeBrandColor)
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Asset File Name indicator
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(SlateCard.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                        .border(1.dp, BorderGray, RoundedCornerShape(6.dp))
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Input Image:\n${currentTemplate.fileName}",
                                        color = OnSlateSubText,
                                        fontSize = 9.sp,
                                        fontFamily = FontFamily.Monospace,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 11.sp
                                    )
                                }
                            }

                            // Column right: Code Snippet canvas editor view
                            Column(
                                modifier = Modifier
                                    .weight(1.2f)
                                    .fillMaxHeight()
                                    .background(CodeBg, RoundedCornerShape(12.dp))
                                    .border(1.dp, BorderGray, RoundedCornerShape(12.dp))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(SlateCard)
                                        .padding(horizontal = 10.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Generated Code (${selectedLanguage.extension.uppercase()})",
                                        color = OnSlateText,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    
                                    // Small custom status dot
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF10B981))
                                    )
                                }

                                // Syntax highlighted Scrollable code content
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                        .padding(10.dp)
                                ) {
                                    val codeText = currentTemplate.codeSnippet[selectedLanguage] ?: ""
                                    Text(
                                        text = getCodeSyntaxColors(codeText),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.sp,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Large CTA Download/Copy Snippet button
                        Button(
                            onClick = {
                                val codeText = currentTemplate.codeSnippet[selectedLanguage] ?: ""
                                clipboardManager.setText(AnnotatedString(codeText))
                                Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SlateCard),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Text(
                                text = "Download Code Snippet",
                                style = TextStyle(
                                    color = OnSlateText,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }

        // ----------------------------------------------------
        // Slide Up Sheet Modal Dialog: Upload Mock Wireframes
        // ----------------------------------------------------
        if (showUploadModal) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable { showUploadModal = false },
                contentAlignment = Alignment.BottomCenter
            ) {
                // Main upload drawer visual
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .clickable(enabled = false) {}, // Prevent auto close click
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateCard),
                    border = BorderStroke(1.dp, BorderGray)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .navigationBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header handle
                        Box(
                            modifier = Modifier
                                .width(36.dp)
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(BorderGray)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Upload Wireframe Image",
                                style = TextStyle(color = OnSlateText, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )
                            IconButton(onClick = { showUploadModal = false }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = OnSlateSubText)
                            }
                        }

                        Text(
                            text = "Choose a sample design mockup to simulate high-fidelity instant code generation output:",
                            color = OnSlateSubText,
                            fontSize = 13.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Custom List of Mockup templates
                        templates.forEachIndexed { index, template ->
                            val isChosen = selectedTemplateIndex == index
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isChosen) activeBrandColor.copy(alpha = 0.12f) else SlateBg)
                                    .border(
                                        1.dp,
                                        if (isChosen) activeBrandColor else BorderGray,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        selectedTemplateIndex = index
                                        showUploadModal = false
                                        Toast
                                            .makeText(
                                                context,
                                                "Wireframe '${template.name}' simulated successfully!",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Miniature thumbnail representer
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.White)
                                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("📝", fontSize = 18.sp)
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = template.name,
                                        color = OnSlateText,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "File: ${template.fileName}",
                                        color = OnSlateSubText,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }

                                if (isChosen) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(activeBrandColor),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "selected",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Custom image upload from device simulation action card
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SlateBg)
                                .drawBehind {
                                    drawRoundRect(
                                        color = BorderGray,
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f)
                                        ),
                                        cornerRadius = CornerRadius(12.dp.toPx())
                                    )
                                }
                                .clickable {
                                    Toast
                                        .makeText(
                                            context,
                                            "System Media Permission required to pick images. Running simulation.",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "☁️ Tap to upload from your Photo Album",
                                color = OnSlateSubText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }

        // ----------------------------------------------------
        // Animated Overlay sliding Settings drawer / sheet
        // ----------------------------------------------------
        if (isSettingsOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable { isSettingsOpen = false },
                contentAlignment = Alignment.BottomCenter
            ) {
                // The Settings container card (represented on the inset screen in user spec)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .clickable(enabled = false) {}, // Eat click events
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = SlateCard),
                    border = BorderStroke(1.dp, BorderGray)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .navigationBarsPadding()
                    ) {
                        // Header handle spacer
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(36.dp)
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(BorderGray)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Header settings title with close icon
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Settings",
                                style = TextStyle(
                                    color = OnSlateText,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            IconButton(
                                onClick = { isSettingsOpen = false },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(SlateBg, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = OnSlateSubText
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Category: Account Info
                        SettingsCategoryTitle("Account Info")
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = SlateBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("User Name", color = OnSlateSubText, fontSize = 13.sp)
                                    Text("Creative Dev", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Email", color = OnSlateSubText, fontSize = 13.sp)
                                    Text("dev@imagetocode.ai", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Category: Code Settings
                        SettingsCategoryTitle("Code Settings")
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = SlateBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Formatting Style", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text("Tab space / semicolon rules", color = OnSlateSubText, fontSize = 11.sp)
                                    }
                                    Text("Prettier standard", color = activeBrandColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                                Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Frameworks", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text("Selected language defaults", color = OnSlateSubText, fontSize = 11.sp)
                                    }
                                    Text("React, Compose, Dart", color = activeBrandColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Category: Appearance
                        SettingsCategoryTitle("Appearance")
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = SlateBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Theme", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text("Sleek Dark Slate theme", color = OnSlateSubText, fontSize = 11.sp)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .background(activeBrandColor.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text("Dark active", color = activeBrandColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Divider(modifier = Modifier.padding(vertical = 10.dp), color = BorderGray)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("High Contrast Accent", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text("Vibrant neon color highlights", color = OnSlateSubText, fontSize = 11.sp)
                                    }
                                    Switch(
                                        checked = highContrastMode,
                                        onCheckedChange = { checkFlag -> highContrastMode = checkFlag },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = activeBrandColor,
                                            checkedTrackColor = activeBrandColor.copy(alpha = 0.4f)
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Category: About
                        SettingsCategoryTitle("About")
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = SlateBg,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, BorderGray)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("ImageToCode Version", color = OnSlateSubText, fontSize = 13.sp)
                                    Text("v1.2 Stable", color = OnSlateText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Support Channel", color = OnSlateSubText, fontSize = 13.sp)
                                    Text("Online (24/7 coverage)", color = activeBrandColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Log Out Options row
                        Button(
                            onClick = {
                                isSettingsOpen = false
                                Toast.makeText(context, "Log out simulated on active device.", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF87171).copy(alpha = 0.12f)),
                            border = BorderStroke(1.dp, Color(0xFFF87171).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Log Out", color = Color(0xFFF87171), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Icon(
                                    imageVector = Icons.Default.Share, // safe share/logout analog vector
                                    contentDescription = "Log Out Icon",
                                    tint = Color(0xFFF87171),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SettingsCategoryTitle(text: String) {
    Text(
        text = text,
        color = OnSlateSubText,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 2.dp, bottom = 6.dp)
    )
}

// ----------------------------------------------------
// Beautiful Custom Syntax Highlighting code translator
// ----------------------------------------------------
fun getCodeSyntaxColors(code: String): AnnotatedString {
    return buildAnnotatedString {
        val lines = code.split("\n")
        lines.forEachIndexed { lineIdx, line ->
            // Draw original line number
            withStyle(style = SpanStyle(color = CodeComment.copy(alpha = 0.5f))) {
                append(String.format("%2d.  ", lineIdx + 1))
            }
            
            // Lexical colored line parsing logic
            var i = 0
            while (i < line.length) {
                when {
                    line.startsWith("//", i) || line.startsWith("///", i) -> {
                        withStyle(style = SpanStyle(color = CodeComment)) {
                            append(line.substring(i))
                        }
                        i = line.length
                    }
                    line.startsWith("import ", i) || line.startsWith("export ", i) || line.startsWith("return", i) || line.startsWith("function", i) || line.startsWith("const ", i) || line.startsWith("val ", i) || line.startsWith("fun ", i) -> {
                        val wordEnd = i + line.substring(i).takeWhile { it.isLetter() }.length
                        withStyle(style = SpanStyle(color = CodeKeyword, fontWeight = FontWeight.Bold)) {
                            append(line.substring(i, wordEnd))
                        }
                        i = wordEnd
                    }
                    line[i] == '"' || line[i] == '\'' -> {
                        val quoteChar = line[i]
                        var endQuoteIdx = line.indexOf(quoteChar, i + 1)
                        if (endQuoteIdx == -1) endQuoteIdx = line.length - 1
                        withStyle(style = SpanStyle(color = CodeString)) {
                            append(line.substring(i, endQuoteIdx + 1))
                        }
                        i = endQuoteIdx + 1
                    }
                    line.startsWith("class=", i) || line.startsWith("className=", i) -> {
                        val len = if (line.startsWith("className=", i)) 10 else 6
                        withStyle(style = SpanStyle(color = CodeClass)) {
                            append(line.substring(i, i + len))
                        }
                        i += len
                    }
                    else -> {
                        append(line[i].toString())
                        i++
                    }
                }
            }
            if (lineIdx < lines.size - 1) {
                append("\n")
            }
        }
    }
}

// ----------------------------------------------------
// Core Safe sizing helpers
// ----------------------------------------------------
@Composable
fun Modifier.SpacerHeightPercent(): Modifier = this.height(6.dp)

fun layerBorderStroke(): Dp = 1.dp
