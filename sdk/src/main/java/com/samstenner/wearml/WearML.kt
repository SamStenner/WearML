package com.samstenner.wearml

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import java.lang.Exception
import java.lang.RuntimeException

object WearML {

    private var root: ViewGroup? = null

    private val commands = mutableListOf<Command>()

    fun init(context: Context) {
        try {
            this.root = (context as Activity).window.decorView as ViewGroup
            Log.d("WearML Init", "Successful")
        } catch (ex: Exception) {
            Log.d("WearML Init", "Failed: ${ex.message}")
            throw ex
        }
    }

    fun register(vararg activators: String,
                 extraDirectives: List<Directives>? = null,
                 unit: (commands: List<String>) -> Unit) : Command {
        if (root == null) throw onInitError()
        val list = activators.toList()
        val description = getDescription(list, extraDirectives)
        val command = Command(View(root?.context).apply {
            isClickable = true
            contentDescription = description
            setOnClickListener { unit.invoke(activators.toList()) }
        }, list, description)
        commands.add(command)
        root?.addView(command.view)
        Log.d("WearML Registered", list.joinToString(","))
        return command
    }

    fun unregister(command: Command) {
        if (root == null) throw onInitError()
        Log.d("WearML Unregistered", command.activators.joinToString(", "))
        commands.removeAll { it.view.id == command.view.id }
        root?.removeView(command.view)
    }

    fun unregisterAll() {
        commands.forEach { command -> unregister(command) }
        commands.clear()
    }

    private fun getDescription(commands: List<String>,
                               extraDirectives: List<Directives>?) : String {
        val commandsValue = commands.joinToString(",")
        val hfCommands = Directives.COMMANDS.apply { value = commandsValue }
        val hfShownCommands = Directives.SHOW_HELP.apply { value = commandsValue }
        val directives = mutableListOf(hfCommands, hfShownCommands).apply {
            extraDirectives?.let { addAll(it) }
        }
        val description = directives.joinToString("|")
        Log.d("WearML Description", description)
        return description
    }

    enum class Directives(private val text: String) {

        COMMANDS("hf_commands"),
        SHOW_HELP("hf_show_help_commands"),
        NO_GLOBAL_COMMANDS("hf_no_global_commands"),
        NO_PTT_HOME("hf_no_ptt_home"),
        HIDE_HELP("hf_hide_help");

        var value: String? = null

        override fun toString() = "$text${ value?.let { ":$it" }.orEmpty() }"

    }

    data class Command(val view: View, val activators: List<String>, val description: String)

    private fun onInitError(): Exception = throw RuntimeException("WearML not initialized!")

}