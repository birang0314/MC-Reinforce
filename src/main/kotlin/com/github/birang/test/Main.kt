package com.github.birang.test

import com.github.birang.test.command.PluginTestCmd
import com.github.birang.test.event.PluginTestEvt
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin(), Listener {

    override fun onEnable() {
        server.consoleSender.sendMessage("${ChatColor.GOLD} 플러그인 활성화")
        server.pluginManager.registerEvents(PluginTestEvt(), this)
        command()
    }

    fun command() {
        getCommand("cmd")?.setExecutor(PluginTestCmd())
    }
}