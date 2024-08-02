package com.example.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.data.AppDatabase
import com.example.littlelemon.data.MenuItemRoom

@Composable
fun Home(navController: NavController, database: AppDatabase) {
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())

    Column {
        TopAppBar(navController)
        HeroSection(databaseMenuItems)
    }
}

@Composable
fun HeroSection(menuItemsLocal: List<MenuItemRoom>) {
    var menuItems = menuItemsLocal
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.lemon_green))
        ) {
            Text(
                stringResource(id = R.string.restaurant_name),
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
                color = colorResource(id = R.color.lemon_yellow)
            )
            Text(
                stringResource(id = R.string.restaurant_city),
                fontSize = 24.sp,
                color = colorResource(id = R.color.secondary_3)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    stringResource(id = R.string.restaurant_desc),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(0.6f),
                    color = colorResource(id = R.color.secondary_3)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Hero Image",
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        Column(modifier = Modifier.background(
                color = colorResource(id = R.color.secondary_3)
            )
        ) {
            var searchTerm by remember { mutableStateOf("") }

            OutlinedTextField(
                label = { Text(text = "Enter search phrase") },
                value = searchTerm,
                onValueChange = { searchTerm = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
                    .background(
                        color = colorResource(id = R.color.secondary_3)
                    ),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search, contentDescription = "Search"
                    )
                },
            )
            if (searchTerm.isNotEmpty()) {
                menuItems = menuItems.filter { it.title.contains(searchTerm, ignoreCase = true) }
            }
        }


        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.secondary_3)
                )
        ) {
            Text(
                text = "ORDER FOR DELIVERY!",
                modifier = Modifier.padding(top = 15.dp),
            )
            val scrollState = rememberScrollState()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .horizontalScroll(scrollState)
            ) {
                Button(
                    onClick = {
                        selectedCategory = "starters"
                    },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lemon_yellow),
                    contentColor = colorResource(id = R.color.secondary_4)
                )
                ) {
                    Text(text = "Starters", style = MaterialTheme.typography.bodyMedium)
                }

                Button(
                    onClick = {
                        selectedCategory = "mains"
                    },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lemon_yellow),
                    contentColor = colorResource(id = R.color.secondary_4)
                )
                ) {
                    Text(text = "Mains", style = MaterialTheme.typography.bodyMedium)
                }

                Button(
                    onClick = {
                        selectedCategory = "desserts"
                    },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lemon_yellow),
                    contentColor = colorResource(id = R.color.secondary_4)
                )
                ) {
                    Text(text = "Desserts", style = MaterialTheme.typography.bodyMedium)
                }

                Button(
                    onClick = {
                        selectedCategory = "drinks"
                    },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lemon_yellow),
                    contentColor = colorResource(id = R.color.secondary_4)
                )
                ) {
                    Text(text = "Drinks", style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (selectedCategory.isNotEmpty()) {
                menuItems = menuItems.filter { it.category.contains(selectedCategory) }
            }
            MenuItems(menuItems)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MenuItems(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(text = menuItem.title, style = MaterialTheme.typography.headlineSmall)
                        Text(
                            text = menuItem.description, style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .padding(top = 5.dp)
                                .padding(bottom = 5.dp)
                        )
                        Text(
                            text = "$%.2f".format(menuItem.price),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "Menu Item Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        )
    }
}