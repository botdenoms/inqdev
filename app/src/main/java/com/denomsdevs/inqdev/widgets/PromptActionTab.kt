package com.denomsdevs.inqdev.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.denomsdevs.inqdev.PromptViewModel
import com.denomsdevs.inqdev.domain.Interpreter
import com.denomsdevs.inqdev.domain.Parser


@Composable
fun PromptActionTab(
    context: Context,
    modifier: Modifier,
    model: PromptViewModel
){
    val promptText = remember { mutableStateOf("") }
    val textShow = remember { mutableStateOf(false) }
    val processing = remember { mutableStateOf(false) }
    val iconVal = when (textShow.value){
        false -> Icons.Rounded.KeyboardArrowUp
        true -> Icons.Rounded.KeyboardArrowRight
    }

    fun promptHandler(context: Context){
        if (textShow.value){
            if (promptText.value.isEmpty()) {
                Toast.makeText(
                    context,
                    "Prompt Text is Empty",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            // Process the prompt text
            if (promptText.value.length < 8) {
                Toast.makeText(
                    context,
                    "Prompt Text is not a valid command",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            // show loading ui & prevent new text processing
            processing.value = true
            // pass the prompt text to the parser
            val parser = Parser(promptText.value)
            if (parser.isValid()) {
                // Run the prompt Entered by the interpreter
                val interpreter = Interpreter(parser)
                // wait for response from the interpreter
                val resp =  interpreter.execute()
                // add response to the list of prompts
                model.addPrompt(resp)
                // Clear text-field
                promptText.value = ""
                textShow.value = !textShow.value
                processing.value = false
                // CoroutineScope(Dispatchers.Main).launch {
                // delay(1000)
                // Clear text-field
                //promptText.value = ""
                //textShow.value = !textShow.value
                //processing.value = false
                //}
            }
            else {
                Toast.makeText(
                    context,
                    "Prompt Text is not a valid command",
                    Toast.LENGTH_SHORT
                ).show()
                processing.value = false
                return
            }
        }
        else {
            textShow.value = !textShow.value
        }
    }

    if (processing.value) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            CircularProgressIndicator(color = Color.Black)
        }
    }
    else{
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (textShow.value) {
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
                    placeholder = { Text("Prompt here..", color = Color.White) }
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
                IconButton(onClick = { promptHandler(context) } ) {
                    Icon(imageVector = iconVal, contentDescription = "Code", tint = Color.White)
                }
            }
        }
    }
}