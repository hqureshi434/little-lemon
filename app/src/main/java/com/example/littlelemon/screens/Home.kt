package com.example.littlelemon.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.data.LemonViewModel
import com.example.littlelemon.data.MenuItemRoom
import com.example.littlelemon.navigation.ProfileDest
import com.example.littlelemon.ui.theme.LemonGreen
import com.example.littlelemon.ui.theme.LemonYellow
import com.example.littlelemon.ui.theme.Secondary3
import com.example.littlelemon.ui.theme.Secondary4

@Composable
fun Home(navController: NavController) {
    val viewModel: LemonViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val searchPhrase = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMenuDataIfNeeded()
    }

    Column() {
        Header(navController)
        UpperPanel(){
            searchPhrase.value = it
        }
        LowerPanel(databaseMenuItems, searchPhrase)
    }
}

@Composable
fun Header(navController: NavController){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(50.dp))
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(top = 20.dp)
        )

        Box(modifier = Modifier
            .size(70.dp)
            .clickable { navController.navigate(ProfileDest.route) }
        ){
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                tint = LemonGreen,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp))
        }
    }
}

@Composable
fun UpperPanel(search : (parameter: String)-> Unit) {
    val searchPhrase = remember {
        mutableStateOf("")
    }

    Log.d("AAAAA", "UpperPanel: ${searchPhrase.value}")
    Column(modifier = Modifier
        .background(LemonGreen)
        .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {

        Text(text = "Little Lemon", style = MaterialTheme.typography.titleLarge, color = LemonYellow)
        Text(text = "Anaheim", style = MaterialTheme.typography.titleSmall, color = Secondary3)

        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with  a modern twist. Turkish, Italian, Indian and chinese recipes are our speciality.",
                modifier = Modifier.fillMaxWidth(0.7f),
                color = Secondary3,
                style = MaterialTheme.typography.bodySmall
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
                search(searchPhrase.value)
            },
            placeholder = {
                Text(text = "Enter Search Phrase", color = Secondary3)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Secondary3
                )
            },
            colors = OutlinedTextFieldDefaults.colors(Secondary3),
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar {character ->
            character.uppercase()
        }
    }.toSet()
    val selectedCategory = remember {
        mutableStateOf("")
    }
    val items = if(search.value == ""){
        databaseMenuItems

    }
    else{
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)

        }
    }
    val filteredItems = if(selectedCategory.value == "" || selectedCategory.value == "all"){
        items
    }
    else{
        items.filter { it.category.contains(selectedCategory.value, ignoreCase = true) }
    }
    Column {
        MenuCategories(categories) {selectedCategory.value = it }
        Spacer(modifier = Modifier.size(2.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (item in filteredItems){
                MenuItem(item = item)
            }
        }
    }
}


@Composable
fun MenuCategories(categories: Set<String>, categoryLambda : (sel: String) -> Unit) {
    val cat = remember {
        mutableStateOf("")
    }

    Card(elevation = CardDefaults.cardElevation(10.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(text = "ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)

            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                CategoryButton(category = "All"){
                    cat.value = it.lowercase()
                    categoryLambda(it.lowercase())
                }

                for (category in categories){
                    CategoryButton(category = category){
                        cat.value = it
                        categoryLambda(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(category:String, selectedCategory: (sel: String) -> Unit) {
    val isClicked = remember{
        mutableStateOf(false)
    }
    Button(onClick = {
        isClicked.value = !isClicked.value
        selectedCategory(category)

    },
        colors = ButtonDefaults.buttonColors(
            containerColor = LemonYellow,
            contentColor = Secondary4
        )
    ) {
        Text(text = category)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    val itemDescription = if(item.description.length>100) {
        item.description.substring(0,100) + ". . ."
    }
    else{
        item.description
    }

    Card(elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .clickable {}) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = item.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$%.2f".format(item.price), fontWeight = FontWeight.Bold)
            }

            GlideImage(
                model = item.image,
                contentDescription = "",
                Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}