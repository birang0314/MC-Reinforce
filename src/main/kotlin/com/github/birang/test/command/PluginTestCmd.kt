package com.github.birang.test.command

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class PluginTestCmd: Listener, CommandExecutor {
    var enchant: Inventory? = null
    var tempSlot: Int? = null
    var abilityCnt: Int? = null
    var spellSlot: Int? = null

    fun enchantTable(p: Player) {
        this.enchant = Bukkit.createInventory(null, 9, "${ChatColor.BOLD}Cube")
        var item = ItemStack(Material.ENCHANTED_BOOK, 1)
        var meta: ItemMeta = item.itemMeta
        meta.setDisplayName("")
        item.itemMeta = meta
        this.enchant!!.setItem(0, item)
        this.enchant!!.setItem(1, item)
        this.enchant!!.setItem(2, item)
        this.enchant!!.setItem(3, item)
        this.enchant!!.setItem(5, item)
        this.enchant!!.setItem(6, item)
        this.enchant!!.setItem(7, item)
        this.enchant!!.setItem(8, item)
        p.openInventory(this.enchant!!)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            var p: Player = sender as Player
            if (args.size === 0) {
                p.sendMessage("Cube / Starforce")
            } else if (args.isNotEmpty()) {
                if (args[0] == "cube") {
                    var cube = ItemStack(Material.BOOK, 1)
                    var cubeMeta: ItemMeta = cube.itemMeta
                    cubeMeta.setDisplayName("${ChatColor.GOLD}마법부여주문서")
                    cube.itemMeta = cubeMeta
                    p.inventory.addItem(cube)
                } else if (args[0] == "sf") {
                    var sf = ItemStack(Material.PAPER, 1)
                    var sfMeta: ItemMeta = sf.itemMeta
                    sfMeta.setDisplayName("${ChatColor.GOLD}강화주문서")
                    sf.itemMeta = sfMeta
                    p.inventory.addItem(sf)
                }
            }
        } else {
            sender.sendMessage("Not Usage")
        }
        return false
    }

}