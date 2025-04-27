package com.denomsdevs.inqdev.domain

import com.denomsdevs.inqdev.models.Prompt

class Interpreter (private val parser: Parser) {

    fun execute(): Prompt {
        // get the permission required by the target
        // check if permissions are allowed, request if not
        // return error if permissions not granted

        // get the action need to be performed
        // get conditions present

        // run the action & apply the conditions found
        // generate the prompt response after execution
        return Prompt(
            id = System.currentTimeMillis(),
            date = System.currentTimeMillis(),
            prompt = parser.prompt,
            error = true,
            response = "Compiling System under Development"
        )
    }
}