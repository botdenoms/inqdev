package com.denomsdevs.inqdev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denomsdevs.inqdev.ui.theme.InqdevTheme
import com.denomsdevs.inqdev.widgets.PromptActionTab
import com.denomsdevs.inqdev.widgets.PromptItem

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
    val context = LocalContext.current
    val promptsViewModel = PromptViewModel()
    promptsViewModel.getPrompts()

    Box(modifier = Modifier.fillMaxSize()) {
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
                        Image(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "settings"
                        )
                    }
                }
            }
            item {
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
            if (promptsViewModel.fetching.value){
                item{
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Spacer(modifier = Modifier.height(80.dp))
                        CircularProgressIndicator(color = Color.Black)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
            else {
                if (promptsViewModel.prompts.value.isEmpty()){
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(80.dp))
                            Text(
                                "No Prompts found!\n\tTry adding one",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                else{
                    items(promptsViewModel.prompts.value) { prompt ->
                        PromptItem(
                            item = prompt
                        ) { prompt -> promptsViewModel.deletePrompt(prompt) }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        PromptActionTab(
            context = context,
            modifier = Modifier.align(Alignment.BottomEnd),
            model = promptsViewModel
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    InqdevTheme {
        MainScreen()
    }
}