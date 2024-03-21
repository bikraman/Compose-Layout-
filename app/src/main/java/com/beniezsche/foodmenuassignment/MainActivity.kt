package com.beniezsche.foodmenuassignment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.beniezsche.foodmenuassignment.models.FoodItem
import com.beniezsche.foodmenuassignment.ui.theme.FoodMenuAssignmentTheme
import com.beniezsche.foodmenuassignment.ui.theme.Orange

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            FoodMenuAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(windowSizeClass)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(onDismiss: () -> Unit) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {

        Column(modifier = Modifier.padding(horizontal = 18.dp)) {
            Row {
                Text("Schedule cooking time", fontWeight = FontWeight.Bold, fontSize = 26.sp)
            }

            TimePicker(Modifier.padding(vertical = 20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Delete",
                    color = Color.Red,
                    fontSize = 28.sp,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.border(2.dp, Orange, RoundedCornerShape(20.dp))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Re-Schedule",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange
                        )
                    }
                }

                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Cook Now", fontSize = 28.sp, color = Color.White)
                    }
                }
            }
        }

    }
}

@SuppressLint("InflateParams")
@Composable
fun TimePicker(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        AndroidView(factory = { context ->

            val timePickerView = LayoutInflater.from(context).inflate(R.layout.number_picker, null)

            val hourPicker = timePickerView.findViewById<NumberPicker>(R.id.hourPicker)
            val minutePicker = timePickerView.findViewById<NumberPicker>(R.id.minutePicker)
            val colon = timePickerView.findViewById<TextView>(R.id.colon)

            hourPicker.setFormatter { value -> String.format("%02d", value) }
            minutePicker.setFormatter { value -> String.format("%02d", value) }

            val textSize = 120F

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                hourPicker.textSize = textSize
                minutePicker.textSize = textSize

                colon.textSize = (textSize / 2.5).toFloat()

                hourPicker.selectionDividerHeight = 0
                minutePicker.selectionDividerHeight = 0
            }


            hourPicker.maxValue = 12
            hourPicker.minValue = 1

            minutePicker.maxValue = 59
            minutePicker.minValue = 0

            timePickerView

        })
    }

}

data class MenuItem(val label: String, val icon: Int)

@Composable
fun Main(windowSizeClass: WindowSizeClass) {

    val navItems = arrayOf(
        MenuItem("Cook", R.drawable.outdoor_grill),
        MenuItem("Favorites", R.drawable.favorites),
        MenuItem("Manual", R.drawable.manual),
        MenuItem("Device", R.drawable.devices),
        MenuItem("Preferences", R.drawable.preferences),
        MenuItem("Settings", R.drawable.settings)
    )
    Scaffold(
        bottomBar = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                NavigationBar(navItems)
        },
        content = { contentPadding ->

            Row(
                modifier = Modifier
                    .padding(contentPadding)
            ) {

                if (windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact) {
                    NavigationRail {
                        var selectedItem by remember { mutableIntStateOf(0) }

                        Spacer(Modifier.weight(1f))

                        navItems.forEachIndexed { index, menuItem ->

                            NavigationRailItem(
                                label = { Text(text = menuItem.label) },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = menuItem.icon),
                                        contentDescription = "",
                                        modifier = Modifier.size(28.dp)
                                    )
                                },
                                selected = selectedItem == index,
                                onClick = { selectedItem = index }
                            )
                        }

                        Spacer(Modifier.weight(1f))
                    }
                }

                Column(
                    modifier = Modifier
                        .background(color = Color(245, 244, 249, 255))
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                ) {
                    CurrentTab(windowSizeClass)
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodMenuAssignmentTheme {
//        Main()
    }
}

@Composable
fun CurrentTab(windowSizeClass: WindowSizeClass) {
    CookingTab(windowSizeClass)
}

@Composable
fun TopBar(windowSizeClass: WindowSizeClass) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {

        SearchBar(Modifier.weight(6f))
        InQueue(windowSizeClass, Modifier.weight(4f))

        Image(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier.weight(1f)
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.power_settings_new),
            contentDescription = "Power On/Off",
            colorFilter = ColorFilter.tint(Color(212, 86, 107, 255)),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier) {
    val text = remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.border(1.dp, Color(87, 83, 112), RoundedCornerShape(60.dp))
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text.value,
            onValueChange = { newText -> text.value = newText },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = { Text(text = "Search for dish or ingredient") },
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search for dish or ingredient",
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            },
            shape = RoundedCornerShape(60.dp)
        )
    }
}

@Composable
fun InQueue(windowSizeClass: WindowSizeClass, modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .background(Color(20, 40, 52, 255), RoundedCornerShape(60.dp))
    ) {

        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
            InQueueBadge()
        else
            InQueueChip()

    }
}

@Composable
fun InQueueChip() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = "Worker",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Text(
                text = "Italian Spaghetti",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Scheduled at 6:30am",
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun InQueueBadge() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = "Worker",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape)
        )
    }

}

@Composable
fun CookingTab(windowSizeClass: WindowSizeClass) {

    val cuisines = arrayOf("Rice Items", "Indian Food", "Curries", "Soups", "Desserts")

    TopBar(windowSizeClass)

    Column {

        Text(
            "What's on your mind?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(cuisines) { string ->
                Recipe(data = string)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recommendations",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = "Show all",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 10.dp)
            )

        }

        FoodItemCards()

        Spacer(Modifier.weight(2f))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BottomStrip(text = "Explore all dishes")
            BottomStrip(text = "Confused what to cook?")
        }
    }

}


@Composable
fun Recipe(data: String) {
    Card(
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.pizza),
                contentDescription = data
            )
            Text(
                text = data,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

@Composable
fun FoodItemCards() {

    BoxWithConstraints {

        if (this.maxHeight <= 200.dp)
            LazyRow {
                items(10) {
                    val foodItem = FoodItem()
                    FoodItemCardHorizontal(data = foodItem)
                }
            }
        else if (this.maxHeight > 200.dp)
            LazyRow {
                items(10) {
                    val foodItem = FoodItem()
                    FoodItemCardVertical(data = foodItem)
                }
            }

    }
}

@Composable
fun FoodItemCardHorizontal(data: FoodItem) {

    var showDialog by remember { mutableStateOf(false) }


    Card(shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable(onClick = { showDialog = true })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(id = R.drawable.pasta),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .width(150.dp)
                    .height(70.dp),
                contentDescription = data.name
            )

            Column {
                Text(text = data.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)

                Row {
                    Text(text = data.prepTime)
                    Text(text = data.prepGroup)
                }
            }
        }
    }

    if (showDialog) {
        Dialog(onDismiss = {
            showDialog = false
        })
    }
}

@Composable
fun FoodItemCardVertical(data: FoodItem) {

    var showDialog by remember { mutableStateOf(false) }

    Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(end = 8.dp)
            .clickable(
                onClick = { showDialog = true }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(id = R.drawable.pasta),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .height(height = 170.dp),
                contentDescription = ""
            )
            Text(
                textAlign = TextAlign.Center,
                maxLines = 2,
                text = data.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = data.prepTime)
                Text(text = data.prepGroup)
            }
        }
    }

    if (showDialog) {
        Dialog(onDismiss = {
            showDialog = false
        })
    }
}

@Composable
fun BottomStrip(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .background(color = Orange, shape = RoundedCornerShape(15.dp))
            .size(300.dp, 40.dp)
    ) {

        Text(text = text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun NavigationBar(navItems: Array<MenuItem>) {
    BottomAppBar(
        modifier = Modifier.height(50.dp)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            navItems.forEach { menuItem ->
                BottomItem(label = menuItem.label, icon = menuItem.icon)
            }
        }
    }
}

@Composable
fun BottomItem(label: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Favorites",
            modifier = Modifier.size(28.dp)
        )
        Text(text = label, fontSize = 12.sp)
    }
}