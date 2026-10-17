

package com.pathumnimsara.autoglow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Orange = Color(0xFFFF6A00)
private val Dark = Color(0xFF111318)
private val LightBg = Color(0xFFF4F5F7)

data class Car(
    val name: String,
    val year: Int,
    val price: String,
    val details: String,
    val emoji: String,
    val location: String = "Colombo, Sri Lanka",
    val description: String = "A well-maintained vehicle with comfortable features."
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AutoGlowApp()
            }
        }
    }
}

@Composable
fun AutoGlowApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }
    var selectedCar by remember { mutableStateOf<Car?>(null) }
    var showSellForm by remember { mutableStateOf(false) }

    val savedCars = remember { mutableStateListOf<Car>() }

    val cars = remember {
        listOf(
            Car(
                "Toyota Aqua", 2018, "Rs. 7,450,000",
                "Hybrid • Automatic • 65,000 km", "🚗",
                "Colombo, Sri Lanka",
                "Fuel-efficient hybrid hatchback. Ideal for city driving."
            ),
            Car(
                "Honda Vezel", 2020, "Rs. 12,900,000",
                "Hybrid • Automatic • 42,000 km", "🚙",
                "Kandy, Sri Lanka",
                "Stylish hybrid SUV with a spacious interior."
            ),
            Car(
                "Suzuki Wagon R", 2019, "Rs. 6,250,000",
                "Hybrid • Automatic • 58,000 km", "🚘",
                "Galle, Sri Lanka",
                "Compact, practical and economical for everyday use."
            ),
            Car(
                "Toyota Prius", 2017, "Rs. 8,950,000",
                "Hybrid • Automatic • 72,000 km", "🚗",
                "Kurunegala, Sri Lanka",
                "Comfortable hybrid vehicle with excellent fuel economy."
            )
        )
    }

    val filteredCars = cars.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        containerColor = LightBg,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val tabs = listOf("Home", "Search", "Sell", "Saved", "Profile")
                val icons = listOf(
                    Icons.Default.Home,
                    Icons.Default.Search,
                    Icons.Default.AddCircle,
                    Icons.Default.Favorite,
                    Icons.Default.Person
                )

                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            selectedCar = null
                            showSellForm = false
                        },
                        icon = {
                            Icon(
                                icons[index],
                                contentDescription = label
                            )
                        },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Orange,
                            selectedTextColor = Orange,
                            indicatorColor = Color(0xFFFFE6D5)
                        )
                    )
                }
            }
        }
    ) { padding ->

        if (selectedCar != null) {
            CarDetailsScreen(
                car = selectedCar!!,
                isSaved = savedCars.contains(selectedCar),
                onBack = { selectedCar = null },
                onToggleSaved = {
                    val car = selectedCar!!
                    if (savedCars.contains(car)) {
                        savedCars.remove(car)
                    } else {
                        savedCars.add(car)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        } else {
            when (selectedTab) {
                0, 1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Dark)
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 20.dp,
                                        bottom = 28.dp
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DirectionsCar,
                                        contentDescription = null,
                                        tint = Orange,
                                        modifier = Modifier.size(32.dp)
                                    )

                                    Spacer(Modifier.width(8.dp))

                                    Column {
                                        Text(
                                            "AutoGlow",
                                            color = Color.White,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            "BUY • SELL • DISCOVER",
                                            color = Color.LightGray,
                                            fontSize = 10.sp,
                                            letterSpacing = 1.5.sp
                                        )
                                    }

                                    Spacer(Modifier.weight(1f))

                                    Icon(
                                        Icons.Default.NotificationsNone,
                                        contentDescription = "Notifications",
                                        tint = Color.White
                                    )
                                }

                                Spacer(Modifier.height(26.dp))

                                Text(
                                    "Find Your",
                                    color = Color.White,
                                    fontSize = 29.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    "Dream Car.",
                                    color = Orange,
                                    fontSize = 29.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(8.dp))

                                Text(
                                    "Discover your next ride in Sri Lanka.",
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )

                                Spacer(Modifier.height(20.dp))

                                OutlinedTextField(
                                    value = search,
                                    onValueChange = { search = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    placeholder = {
                                        Text("Search brand or model...")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null
                                        )
                                    },
                                    singleLine = true,
                                    shape = RoundedCornerShape(14.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = Orange,
                                        unfocusedBorderColor = Color.Transparent
                                    )
                                )
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 18.dp,
                                    vertical = 18.dp
                                )
                            ) {
                                Text(
                                    "Browse by Category",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.height(14.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf(
                                        "🚗" to "Cars",
                                        "🚙" to "SUVs",
                                        "🏎️" to "Sports",
                                        "🚐" to "Vans"
                                    ).forEach { (emoji, label) ->
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(14.dp)
                                                )
                                                .clickable {
                                                    search = when (label) {
                                                        "SUVs" -> "Vezel"
                                                        "Sports" -> "Prius"
                                                        else -> ""
                                                    }
                                                }
                                                .padding(vertical = 12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(emoji, fontSize = 25.sp)
                                            Spacer(Modifier.height(6.dp))
                                            Text(
                                                label,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 18.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    if (selectedTab == 1) "Search Results"
                                    else "Featured Cars",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(Modifier.weight(1f))

                                Text(
                                    "${filteredCars.size} cars",
                                    color = Orange,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(Modifier.height(12.dp))
                        }

                        items(filteredCars) { car ->
                            CarCard(
                                car = car,
                                isSaved = savedCars.contains(car),
                                onClick = { selectedCar = car },
                                onToggleSaved = {
                                    if (savedCars.contains(car)) {
                                        savedCars.remove(car)
                                    } else {
                                        savedCars.add(car)
                                    }
                                }
                            )
                        }

                        if (filteredCars.isEmpty()) {
                            item {
                                Text(
                                    "No cars found. Try another search.",
                                    modifier = Modifier.padding(24.dp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                2 -> {
                    if (showSellForm) {
                        SellCarScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            onBack = { showSellForm = false }
                        )
                    } else {
                        PlaceholderScreen(
                            title = "Sell Your Car",
                            subtitle = "Create a listing for your vehicle.",
                            icon = Icons.Default.AddCircle,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            buttonText = "Create Listing",
                            onClick = { showSellForm = true }
                        )
                    }
                }

                3 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentPadding = PaddingValues(
                            top = 20.dp,
                            bottom = 20.dp
                        )
                    ) {
                        item {
                            Text(
                                "Saved Cars",
                                modifier = Modifier.padding(
                                    horizontal = 18.dp,
                                    vertical = 12.dp
                                ),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (savedCars.isEmpty()) {
                            item {
                                EmptyState(
                                    title = "No saved cars yet",
                                    subtitle = "Tap the heart on a car to save it."
                                )
                            }
                        } else {
                            items(savedCars.toList()) { car ->
                                CarCard(
                                    car = car,
                                    isSaved = true,
                                    onClick = { selectedCar = car },
                                    onToggleSaved = { savedCars.remove(car) }
                                )
                            }
                        }
                    }
                }

                else -> {
                    ProfileScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun CarCard(
    car: Car,
    isSaved: Boolean,
    onClick: () -> Unit,
    onToggleSaved: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .background(Color(0xFFE8EAEE)),
                contentAlignment = Alignment.Center
            ) {
                Text(car.emoji, fontSize = 82.sp)

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp),
                    color = Orange,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        "FOR SALE",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        )
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .size(38.dp)
                        .clickable { onToggleSaved() },
                    color = Color.White,
                    shape = CircleShape
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            if (isSaved) Icons.Default.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = "Save car",
                            tint = if (isSaved) Orange else Dark
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    "${car.name} (${car.year})",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    car.details,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        car.price,
                        color = Orange,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.weight(1f))

                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "View details",
                        tint = Orange
                    )
                }
            }
        }
    }
}

@Composable
fun CarDetailsScreen(
    car: Car,
    isSaved: Boolean,
    onBack: () -> Unit,
    onToggleSaved: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(LightBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                "Car Details",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.weight(1f))

            IconButton(onClick = onToggleSaved) {
                Icon(
                    if (isSaved) Icons.Default.Favorite
                    else Icons.Default.FavoriteBorder,
                    contentDescription = "Save car",
                    tint = Orange
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE5E7EB)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(car.emoji, fontSize = 110.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "${car.name} (${car.year})",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                car.price,
                color = Orange,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Text(
                car.location,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                "Vehicle Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    DetailRow("Transmission / Type", car.details)
                    HorizontalDivider()
                    DetailRow("Year", car.year.toString())
                    HorizontalDivider()
                    DetailRow("Location", car.location)
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                car.description,
                color = Color.DarkGray,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(16.dp))
        }

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(
                Icons.Default.Call,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Contact Seller",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(
            label,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    buttonText: String? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(LightBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Orange,
            modifier = Modifier.size(58.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            subtitle,
            color = Color.Gray
        )

        if (buttonText != null) {
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun EmptyState(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.FavoriteBorder,
            contentDescription = null,
            tint = Orange,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            subtitle,
            color = Color.Gray
        )
    }
}

@Composable
fun SellCarScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(LightBg)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                "Create Car Listing",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (submitted) {
            EmptyState(
                title = "Listing form completed",
                subtitle = "This demo does not save listings yet."
            )
        } else {
            Text(
                "Enter your vehicle details",
                color = Color.Gray
            )

            Spacer(Modifier.height(18.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Car brand and model") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Manufacturing year") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price (LKR)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = details,
                onValueChange = { details = it },
                label = { Text("Mileage and transmission") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = { submitted = true },
                enabled = name.isNotBlank() &&
                        year.isNotBlank() &&
                        price.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text("Submit Listing")
            }
        }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(LightBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color(0xFFFFE6D5)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Orange,
                    modifier = Modifier.size(58.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "My Profile",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Welcome to AutoGlow",
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.PersonOutline,
                        contentDescription = null,
                        tint = Orange
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("Personal Information")
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Orange
                    )
                    Spacer(Modifier.width(12.dp))
                    Text("My Saved Cars")
                }
            }
        }
    }
}
