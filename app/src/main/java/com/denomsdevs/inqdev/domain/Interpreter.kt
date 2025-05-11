package com.denomsdevs.inqdev.domain

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import com.denomsdevs.inqdev.models.Prompt
import kotlinx.coroutines.delay

class Interpreter(
    private val parser: Parser,
    private val context: Context,
    private val launcher:  ManagedActivityResultLauncher<Array<String?>, Map<String?, Boolean?>?>,
) {
    init {
        var permGranted = false
        for (perm in parser.getTargetPermissions()){
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED ) {
                permGranted = false
                break
            }
            if (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED ) {
                permGranted = true
            }
        }
        if (!permGranted) {
            launcher.launch(parser.getTargetPermissions().toTypedArray())
        }
    }

    private fun isPermissionsResolved(): Boolean {
        val perms = parser.getTargetPermissions()
        for (perm in perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED ) {
                return false
            }
        }
        return true
    }

    private fun resolvePermissions() {
        val perms = parser.getTargetPermissions()
        val resp = launcher.launch(perms.toTypedArray())
        println(resp.toString())
    }

    suspend fun execute(): Prompt {
        // get & resolve the permissions required by the target
        var tries = 0
        while (!isPermissionsResolved() && tries < 3) {
            resolvePermissions()
            tries++
            delay(2000)
        }
        if (!isPermissionsResolved()){
            return Prompt(
                id = System.currentTimeMillis(),
                date = System.currentTimeMillis(),
                prompt = parser.prompt,
                error = true,
                response = "Permission Required for the Prompt Denied"
            )
        }
        // get the action need to be performed
        // get conditions present

        // run the action & apply the conditions found
        // generate the prompt response after execution
        return Prompt(
            id = System.currentTimeMillis(),
            date = System.currentTimeMillis(),
            prompt = parser.prompt,
            error = true,
            response = "Actions under Development"
        )
    }
}