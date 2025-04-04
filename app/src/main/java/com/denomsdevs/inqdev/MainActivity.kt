package com.denomsdevs.inqdev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.KeyboardArrowDown
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
    val textShow = remember { mutableStateOf(false) }
    val iconVal = when (textShow.value){
        false -> Icons.Rounded.KeyboardArrowUp
        true -> Icons.Rounded.KeyboardArrowRight
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(modifier = Modifier.size(28.dp), imageVector = Icons.Filled.Settings, contentDescription = "settings")
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.height(2.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Recents",
                        fontSize = 14.sp,
                        color = Color.Black.copy(.8F)
                    )
                }
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
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            if (textShow.value){
                OutlinedTextField(
                    value = promptText.value,
                    onValueChange = { txt -> promptText.value = txt },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(0.8F),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.Black,
                        textColor = Color.White
                    ),
                    placeholder = { Text("Prompt here..", color = Color.White ) }
                )
            } else {
                Spacer(modifier = Modifier.size(20.dp))
            }
            Surface(
                modifier = Modifier
                    .size(64.dp)
                    .padding(8.dp),
                shape = CircleShape,
                color = Color.Black,
            ) {
                IconButton(onClick = { textShow.value = !textShow.value }) {
                    Icon(imageVector = iconVal, contentDescription = "Code", tint = Color.White)
                }
            }
        }
    }
}

@Composable
fun PromptItem(item: Prompts){
    val expanded = remember {
        mutableStateOf(false)
    }
    val title: String = when (item.error){
        true ->  "Compile Error"
        false -> "Compile Successful"
    }
    val titleColor: Color = when (item.error){
        true ->  Color.Red
        false -> Color.Green
    }
    val iconVal = when (expanded.value){
        false -> Icons.Rounded.KeyboardArrowDown
        true -> Icons.Rounded.KeyboardArrowUp
    }


    Surface(
        color = Color.Black.copy(.6F),
        shape = RoundedCornerShape(5),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
            Text(item.date.toString(), fontSize = 14.sp, color = Color.White )
            Spacer(modifier = Modifier.height(5.dp))
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
                IconButton(onClick = { expanded.value = !expanded.value }) {
                    Icon(
                        imageVector = iconVal,
                        contentDescription = "expand toggle",
                        tint = Color.White
                    )
                }
            }
            if (expanded.value){
                Spacer(modifier = Modifier.height(5.dp))
                Text(item.response, fontSize = 14.sp, color = Color.White)
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Drop down",
                            tint = Color.Red
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Drop down",
                            tint = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
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