package com.example.app_specific_external

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_specific_external.ui.theme.AppSpecific_ExternalTheme
import java.io.File

// BITS Pilani Branding Colors
val BitsBlue = Color(0xFF003366)
val BitsRed = Color(0xFFC41230)
val BackgroundGray = Color(0xFFF2F2F2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSpecific_ExternalTheme {
                ExternalStorageDemo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExternalStorageDemo() {
    val context = LocalContext.current
    var logEntry by remember { mutableStateOf("") }
    var displayedData by remember { mutableStateOf("No data fetched yet.") }
    var statusMessage by remember { mutableStateOf("Ready to export.") }
    val fileName = "app_report_log.txt"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("BITS EXTERNAL STORAGE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("App-Specific Large File Management", color = Color.White, fontSize = 10.sp)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BitsBlue)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = BitsBlue) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("JIBIN N | Course Faculty", color = Color.White, fontWeight = FontWeight.Medium)
                    Text("BITS Pilani - WILP 2026", color = Color.LightGray, fontSize = 11.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Export & Fetch Large Reports", color = BitsBlue, fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = logEntry,
                onValueChange = { logEntry = it },
                label = { Text("Enter log/report data...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Logic 1: EXPORTING DATA
            Button(
                onClick = {
                    if (logEntry.isNotBlank()) {
                        try {
                            // getExternalFilesDir(null) targets: /Android/data/com.example.app_specific_external/files/
                            val directory = context.getExternalFilesDir(null)
                            val file = File(directory, fileName)

                            // appendText adds data to the end of the file without deleting existing content
                            file.appendText("Entry: $logEntry\n")

                            logEntry = ""
                            statusMessage = "Data exported to: ${file.name}"
                        } catch (e: Exception) {
                            statusMessage = "Export failed."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BitsBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Export to External Storage")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Logic 2: FETCHING DATA
            Button(
                onClick = {
                    try {
                        val directory = context.getExternalFilesDir(null)
                        val file = File(directory, fileName)

                        // Check if file exists before trying to read
                        if (file.exists()) {
                            displayedData = file.readText() // Reads the entire file content as a String
                            statusMessage = "Fetch successful!"
                        } else {
                            displayedData = "No file found to read."
                        }
                    } catch (e: Exception) {
                        statusMessage = "Fetch failed."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BitsRed),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Fetch Exported Data")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display Areas
            Text("Status: $statusMessage", color = BitsBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = displayedData,
                    modifier = Modifier.padding(16.dp),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}