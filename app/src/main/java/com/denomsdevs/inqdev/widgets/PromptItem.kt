package com.denomsdevs.inqdev.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denomsdevs.inqdev.models.Prompt
import java.util.Date

@Composable
fun PromptItem(item: Prompt){
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
    val date = Date(item.date)


    Surface(
        color = Color.Black.copy(.6F),
        shape = RoundedCornerShape(5),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)) {
            Text(date.toString(), fontSize = 14.sp, color = Color.White )
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
