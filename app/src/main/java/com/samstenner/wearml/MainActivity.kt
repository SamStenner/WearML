package com.samstenner.wearml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.samstenner.wearml.WearML.Command

class MainActivity : AppCompatActivity() {

    private lateinit var command: Command

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCommands()
    }

    private fun initCommands() {

        // Initialises WearML SDK
        WearML.init(this)

        // Registers permanent command
        WearML.register("Hello", "Hi", "Hey") {
            /*  performGreeting()  */
        }

        // Registers command and stores a reference to it
        command = WearML.register("Launch rockets") {
            /*  launchRockets()  */
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregisters command
        WearML.unregister(command)

        // Unregisters all commands in list
        WearML.unregisterAll()
    }

}