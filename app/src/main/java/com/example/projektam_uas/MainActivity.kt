package com.example.projektam_uas

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projektam_uas.ui.theme.ProjekTAMUASTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjekTAMUASTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("new_schedule") { NewScheduleScreen(navController) }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9)) // Latar belakang biru muda
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome to Dejory Scheduler",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "HELLO PRODUCTIVE PEOPLE !!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Tempat untuk grafik batang
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            // Gunakan pustaka grafik sebenarnya untuk menampilkan grafik batang
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TombolIkon(teks = "Daily", resGambar = R.drawable.dailyy)
            TombolIkon(teks = "Organization", resGambar = R.drawable.meetingg)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TombolIkon(teks = "Assignment", resGambar = R.drawable.assignmentt)
            TombolIkon(teks = "Priority", resGambar = R.drawable.priorityy)
        }
        Spacer(modifier = Modifier.weight(1f)) // Spacer mengambil ruang kosong
        // Baris untuk ikon Home dan Jadwal di bagian bawah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TombolBawah(teks = "Home", resGambar = R.drawable.home)
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun NewScheduleScreen(navController: NavHostController) {
    var scheduleName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9)) // Latar belakang biru muda
            .padding(16.dp)
    ) {
        Text(
            text = "NEW SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            label = "Schedule Name:",
            text = scheduleName,
            onTextChanged = { scheduleName = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Description",
            text = description,
            onTextChanged = { description = it },
            height = 120.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        InputField(
            label = "Deadline:",
            text = deadline,
            onTextChanged = { deadline = it }
            )
        Spacer(modifier = Modifier.height(16.dp))
        NotificationSetting()
        Spacer(modifier = Modifier.weight(1f)) // Spacer mengambil ruang kosong
        // Baris untuk ikon Home dan Jadwal di bagian bawah
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TombolBawah(teks = "Home", resGambar = R.drawable.home) {
                navController.navigate("main")
            }
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar)
        }
    }
}

@Composable
fun InputField(
    label: String,
    text: String,
    onTextChanged: (String) -> Unit,
    height: Dp = 56.dp,
    isDateField: Boolean = false) {
    Column {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray
            ),
            singleLine = !isDateField,
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun NotificationSetting() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = "Setting Notification",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Date", color = Color.Gray)
            Text(text = "Hour", color = Color.Gray)
            Text(text = "Minute", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* TODO: Implement onClick */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "ON/OFF")
        }
    }
}

@Composable
fun TombolIkon(teks: String, resGambar: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resGambar),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = teks, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun TombolBawah(teks: String, resGambar: Int, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Image(
                painter = painterResource(id = resGambar),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = teks, fontSize = 14.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewNewScheduleScreen() {
    val navController = rememberNavController()
    NewScheduleScreen(navController)
}
