package com.denomsdevs.inqdev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denomsdevs.inqdev.models.Prompts
import com.denomsdevs.inqdev.models.promptItems
import com.denomsdevs.inqdev.ui.theme.InqdevTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InqdevTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen() {
    val promptText = remember { mutableStateOf("") }
    val textShow = remember { mutableStateOf(true) }
    val iconVal = when (textShow.value){
        false -> Icons.Rounded.KeyboardArrowUp
        true -> Icons.Rounded.KeyboardArrowRight
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(modifier = Modifier.size(24.dp), imageVector = Icons.Filled.Settings, contentDescription = "settings")
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Text(
                    "Recent",
                    fontSize = 14.sp,
                    color = Color(0x88000000)
                )
            }
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(promptItems){
                prompt -> PromptItem(item = prompt)
            }
            item{
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .background(Color.Black.copy(0.5F))
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (textShow.value){
                OutlinedTextField(
                    value = promptText.value,
                    onValueChange = { txt -> promptText.value = txt },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            } else {
                Spacer(modifier = Modifier.size(20.dp))
            }
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .padding(8.dp),
                shape = CircleShape,
                color = Color.DarkGray
            ) {
                IconButton(onClick = { textShow.value = !textShow.value }) {
                    Icon(imageVector = iconVal, contentDescription = "Code")
                }
            }
        }
    }
}

@Composable
fun PromptItem(item: Prompts){
    val title: String = when (item.error){
        true ->  "Compile Error"
        false -> "Compile Successful"
    }
    val titleColor: Color = when (item.error){
        true ->  Color.Red
        false -> Color.Green
    }
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(item.date.toString(), fontSize = 14.sp )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Image(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Drop down" )
                }
            }
        }
    }

}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    InqdevTheme {
        MainScreen()
    }
}