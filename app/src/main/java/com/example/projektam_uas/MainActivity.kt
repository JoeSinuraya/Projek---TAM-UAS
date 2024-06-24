package com.example.projektam_uas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.ui.tooling.preview.Preview
import com.example.projektam_uas.ui.theme.ProjekTAMUASTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjekTAMUASTheme {
                val navController = rememberNavController()
                val scheduleViewModel = remember { ScheduleViewModel() }
                NavHost(navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("new_schedule") { NewScheduleScreen(navController, scheduleViewModel) }
                    composable("daily_schedule") { DailyScheduleScreen(navController, scheduleViewModel) }
                    composable("organization_schedule") { OrganizationScheduleScreen(navController, scheduleViewModel) }
                    composable("assignment_schedule") { AssignmentScheduleScreen(navController, scheduleViewModel) }
                    composable("priority_schedule") { PriorityScheduleScreen(navController, scheduleViewModel) }
                    composable(
                        "schedule_detail/{category}/{name}/{description}/{deadline}",
                        arguments = listOf(
                            navArgument("category") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType },
                            navArgument("description") { type = NavType.StringType },
                            navArgument("deadline") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val category = backStackEntry.arguments?.getString("category") ?: ""
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val description = backStackEntry.arguments?.getString("description") ?: ""
                        val deadline = backStackEntry.arguments?.getString("deadline") ?: ""
                        ScheduleDetailScreen(
                            navController = navController,
                            category = category,
                            name = name,
                            description = description,
                            deadline = deadline,
                            scheduleViewModel = scheduleViewModel
                        )
                    }
                    composable(
                        "edit_schedule/{category}/{name}/{description}/{deadline}",
                        arguments = listOf(
                            navArgument("category") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType },
                            navArgument("description") { type = NavType.StringType },
                            navArgument("deadline") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val category = backStackEntry.arguments?.getString("category") ?: ""
                        val name = backStackEntry.arguments?.getString("name") ?: ""
                        val description = backStackEntry.arguments?.getString("description") ?: ""
                        val deadline = backStackEntry.arguments?.getString("deadline") ?: ""
                        EditScheduleScreen(
                            navController = navController,
                            category = category,
                            name = name,
                            description = description,
                            deadline = deadline,
                            scheduleViewModel = scheduleViewModel
                        )
                    }
                }
            }
        }
    }
}

data class ScheduleItem(val name: String, val description: String, val deadline: String, val category: String)

class ScheduleViewModel {
    var dailySchedules = mutableStateListOf<ScheduleItem>()
        private set
    var organizationSchedules = mutableStateListOf<ScheduleItem>()
        private set
    var assignmentSchedules = mutableStateListOf<ScheduleItem>()
        private set
    var prioritySchedules = mutableStateListOf<ScheduleItem>()
        private set

    fun addSchedule(schedule: ScheduleItem) {
        when (schedule.category) {
            "Harian" -> dailySchedules.add(schedule)
            "Organisasi" -> organizationSchedules.add(schedule)
            "Tugas" -> assignmentSchedules.add(schedule)
            "Prioritas" -> prioritySchedules.add(schedule)
        }
    }

    fun deleteSchedule(schedule: ScheduleItem) {
        when (schedule.category) {
            "Harian" -> dailySchedules.remove(schedule)
            "Organisasi" -> organizationSchedules.remove(schedule)
            "Tugas" -> assignmentSchedules.remove(schedule)
            "Prioritas" -> prioritySchedules.remove(schedule)
        }
    }

    fun updateSchedule(oldSchedule: ScheduleItem, newSchedule: ScheduleItem) {
        deleteSchedule(oldSchedule)
        addSchedule(newSchedule)
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "Selamat Datang di TASKLY",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "HALO ORANG PRODUKTIF !!",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.taskly2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TombolIkon(teks = "Harian", resGambar = R.drawable.dailyy) {
                navController.navigate("daily_schedule")
            }
            TombolIkon(teks = "Organisasi", resGambar = R.drawable.meetingg) {
                navController.navigate("organization_schedule")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TombolIkon(teks = "Tugas", resGambar = R.drawable.assignmentt) {
                navController.navigate("assignment_schedule")
            }
            TombolIkon(teks = "Prioritas", resGambar = R.drawable.priorityy) {
                navController.navigate("priority_schedule")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
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
fun NewScheduleScreen(navController: NavHostController, scheduleViewModel: ScheduleViewModel) {
    var scheduleName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    val categories = listOf("Harian", "Organisasi", "Tugas", "Prioritas")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "NEW SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Deadline:",
            text = deadline,
            onTextChanged = { deadline = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryDropdown(
            label = "Category:",
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newSchedule = ScheduleItem(scheduleName, description, deadline, selectedCategory)
                scheduleViewModel.addSchedule(newSchedule)
                navController.navigate("main")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Tambah")
        }
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun DailyScheduleScreen(navController: NavHostController, scheduleViewModel: ScheduleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "DAILY SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScheduleList(scheduleViewModel.dailySchedules, navController)
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun OrganizationScheduleScreen(navController: NavHostController, scheduleViewModel: ScheduleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "ORGANIZATION SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScheduleList(scheduleViewModel.organizationSchedules, navController)
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun AssignmentScheduleScreen(navController: NavHostController, scheduleViewModel: ScheduleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "ASSIGNMENT SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScheduleList(scheduleViewModel.assignmentSchedules, navController)
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun PriorityScheduleScreen(navController: NavHostController, scheduleViewModel: ScheduleViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "PRIORITY SCHEDULE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        ScheduleList(scheduleViewModel.prioritySchedules, navController)
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun ScheduleDetailScreen(
    navController: NavHostController,
    category: String,
    name: String,
    description: String,
    deadline: String,
    scheduleViewModel: ScheduleViewModel
) {
    val schedule = ScheduleItem(name, description, deadline, category)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "Schedule Detail",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Category: $category", fontWeight = FontWeight.Bold)
        Text(text = "Name: $name", fontWeight = FontWeight.Bold)
        Text(text = "Description: $description", fontWeight = FontWeight.Bold)
        Text(text = "Deadline: $deadline", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                navController.navigate("edit_schedule/${schedule.category}/${schedule.name}/${schedule.description}/${schedule.deadline}")
            }) {
                Text(text = "Update")
            }
            Button(onClick = {
                scheduleViewModel.deleteSchedule(schedule)
                navController.navigate("main")
            }) {
                Text(text = "Delete")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun EditScheduleScreen(
    navController: NavHostController,
    category: String,
    name: String,
    description: String,
    deadline: String,
    scheduleViewModel: ScheduleViewModel
) {
    var scheduleName by remember { mutableStateOf(name) }
    var scheduleDescription by remember { mutableStateOf(description) }
    var scheduleDeadline by remember { mutableStateOf(deadline) }
    val categories = listOf("Harian", "Organisasi", "Tugas", "Prioritas")
    var selectedCategory by remember { mutableStateOf(category) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF90CAF9))
            .padding(16.dp)
    ) {
        Text(
            text = "Edit Schedule",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Schedule Name:",
            text = scheduleName,
            onTextChanged = { scheduleName = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Description",
            text = scheduleDescription,
            onTextChanged = { scheduleDescription = it },
            height = 120.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputField(
            label = "Deadline:",
            text = scheduleDeadline,
            onTextChanged = { scheduleDeadline = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryDropdown(
            label = "Category:",
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val oldSchedule = ScheduleItem(name, description, deadline, category)
                val newSchedule = ScheduleItem(scheduleName, scheduleDescription, scheduleDeadline, selectedCategory)
                scheduleViewModel.updateSchedule(oldSchedule, newSchedule)
                navController.navigate("main")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Simpan")
        }
        Spacer(modifier = Modifier.weight(1f))
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
            TombolBawah(teks = "Jadwal", resGambar = R.drawable.calendar) {
                navController.navigate("new_schedule")
            }
        }
    }
}

@Composable
fun ScheduleList(schedules: List<ScheduleItem>, navController: NavHostController) {
    Column {
        for (schedule in schedules) {
            ScheduleCard(schedule, navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ScheduleCard(schedule: ScheduleItem, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("schedule_detail/${schedule.category}/${schedule.name}/${schedule.description}/${schedule.deadline}")
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = schedule.name, fontWeight = FontWeight.Bold)
            Text(text = schedule.description)
            Text(text = schedule.deadline)
        }
    }
}

@Composable
fun InputField(label: String, text: String, onTextChanged: (String) -> Unit, height: Dp = 56.dp) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold)
        TextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    }
}

@Composable
fun CategoryDropdown(label: String, categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Column {
        Text(text = label, fontWeight = FontWeight.Bold)
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = selectedCategory.ifEmpty { "Pilih kategori" },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.White)
                    .padding(16.dp)
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                categories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }) {
                        Text(text = category)
                    }
                }
            }
        }
    }
}

@Composable
fun TombolIkon(teks: String, resGambar: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = resGambar),
            contentDescription = teks,
            modifier = Modifier.size(80.dp)
        )
        Text(text = teks, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun TombolBawah(teks: String, resGambar: Int, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = resGambar),
            contentDescription = teks,
            modifier = Modifier.size(40.dp)
        )
        Text(text = teks, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjekTAMUASTheme {
        MainScreen(rememberNavController())
    }
}
