package com.denomsdevs.inqdev.domain

class Parser(val prompt: String) {
    private var tokens: List<String> = prompt.split(" ")
    private var promptTree: MutableMap<String, String> = mutableMapOf()

    fun isValid(): Boolean {
        // loop over the tokens present.
        for ( t in tokens ){
            // check for target, cannot be empty/null
            if ( isTarget(t) ) {
                //  two target not currently allowed
                if (promptTree.containsKey("target")) return false
                promptTree["target"] = t
                continue
            }
            // check for target type, can be empty/null
            if ( isTargetType(t) ) {
                //  two target type not currently allowed
                if (promptTree.containsKey("type")) return false
                promptTree["type"] = t
                continue
            }
            // check for target action, cannot be null/empty
            if ( isTargetAction(t) ) {
                //  two target action not currently allowed
                if (promptTree.containsKey("action")) return false
                promptTree["action"] = t
                continue
            }
            // check for conditions, can be empty/null
            if (isCondition(t)){
                //  conditions chaining not currently allowed
                if (promptTree.containsKey("condition")) return false
                promptTree["condition"] = t
                continue
            }
            // check for conditions values, cannot be empty/null if conditions is not null
            if (promptTree.containsKey("condition")){
                // match value to condition or type field or get/set field
                // logic check
                if (isLogic(t)){
                    TODO()
                }
            }

        }
        
        if (!promptTree.containsKey("target")) return false
        if (!promptTree.containsKey("action")) return false
        return true
    }

    private fun isTarget(token: String): Boolean {
        // check if string is a valid target
        return when(token.lowercase()) {
            "sms", "message", "messages" -> true
            "contact", "contacts", "phone", "phonebook" -> true
            "storage", "media" -> true
            else -> false
        }
    }

    private fun isTargetType(token: String): Boolean {
        // check if string is a valid target type
        return when(token.lowercase()) {
            "sent", "received", "incoming", "outgoing" -> true
            "sim", "card", "email", "line" -> true
            "ext", "external", "int", "internal" -> true
            "all" -> true
            else -> false
        }
    }

    private fun isTargetAction(token: String): Boolean {
        // check if string is a valid target action
        return when(token.lowercase()) {
            "list" -> true
            "set", "get" -> true
            "find", "search", "in" -> true
            "count" -> true
            "delete" -> true
            else -> false
        }
    }

    private fun isCondition(token: String): Boolean {
        // check if string is a valid condition
        return when(token.lowercase()) {
            "if", "where" -> true
            else -> false
        }
    }

    private fun isLogic(token: String): Boolean {
        // check if string is a valid condition
        return when(token.lowercase()) {
            "is", "equal", "not" -> true
            "less", "more", "<=", ">=", ">", "<" -> true
            "like" -> true
            "and", "or" -> true
            else -> false
        }
    }

    private fun getTarget(): Target {
        return when (promptTree["target"].toString().lowercase()) {
            "sms", "message", "messages" -> Target.SMS
            "contact", "contacts", "phone", "phonebook" -> Target.CONTACTS
            "storage", "media" -> Target.STORAGE
            else -> Target.NULL
        }
    }

    fun getAction(): String? {
        return promptTree["action"]
    }

    fun getTargetPermissions(): List<String> {
        // return the Permission required for the target
        val perms = emptyList<String>().toMutableList()
        when (getTarget()){
            Target.APPS -> perms.add("READ")
            Target.SMS -> TODO()
            Target.CONTACTS -> TODO()
            Target.STORAGE -> TODO()
            Target.EMAIL -> TODO()
            Target.NULL -> TODO()
        }
        return perms
    }
}