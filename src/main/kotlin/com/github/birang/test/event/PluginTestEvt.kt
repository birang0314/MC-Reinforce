package com.github.birang.test.event

import com.github.birang.test.command.PluginTestCmd
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.bukkit.inventory.meta.ItemMeta
import kotlin.random.Random

class PluginTestEvt: Listener {
    private var ptc: PluginTestCmd = PluginTestCmd()

    @EventHandler
    open fun enchantGUI(e: InventoryClickEvent) {
        if (e.inventory != ptc.enchant) return
        var p: Player = e.whoClicked as Player
        var upgradeItem = ItemStack(Material.AIR, 1)
        var spellBook = ItemStack(Material.BOOK)
        var firstSlot = ItemStack(Material.BOOK, 1)

        var spell: ItemMeta = spellBook.itemMeta
        spell.setDisplayName("${ChatColor.GOLD}마법부여주문서")
        spellBook.itemMeta = spell
        firstSlot.itemMeta = spell
        var pi: PlayerInventory = p.inventory
        ptc.spellSlot = 50;
        var i = 0

        for (i in i until pi.maxStackSize) {
            var temp: Int = pi.first(firstSlot)
            if (temp < ptc.spellSlot!! && temp != -1) ptc.spellSlot = temp
            firstSlot.amount = firstSlot.amount + 1
        }

        when (e.currentItem?.type) {
            Material.IRON_SWORD, Material.IRON_AXE, Material.IRON_PICKAXE, Material.IRON_SHOVEL,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.GOLDEN_SWORD, Material.GOLDEN_AXE, Material.GOLDEN_PICKAXE, Material.GOLDEN_SHOVEL,
            Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS,
            Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SHOVEL,
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
            Material.NETHERITE_SWORD, Material.NETHERITE_AXE, Material.NETHERITE_PICKAXE, Material.NETHERITE_SHOVEL,
            Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS,
            Material.SHIELD, Material.TRIDENT, Material.BOW, Material.CROSSBOW
                -> if (e.slotType != InventoryType.SlotType.CONTAINER || e.slot > 8) {
                    upgradeItem.type = e.currentItem!!.type
                    upgradeItem.data = e.currentItem?.data
                    upgradeItem.itemMeta = e.currentItem?.itemMeta
                    ptc.tempSlot = e.slot
                    ptc.enchant?.setItem(4, upgradeItem)
            }
        }

        when (e.slot) {
            4 -> if (e.slotType == InventoryType.SlotType.CONTAINER && pi.containsAtLeast(spellBook, 1)) {
                ptc.abilityCnt = 1
                for (i in 0 until (Enchantment.values()).size) e.currentItem?.removeEnchantment(Enchantment.values()[i])
                var rand:Double = Math.random()
                while (rand < 0.7) {
                    ptc.abilityCnt = ptc.abilityCnt!! + 1
                    rand = Math.random()
                    if (ptc.abilityCnt!! > 4) rand = 1.0
                }

                for (j in 0 until ptc.abilityCnt!!) {
                    var randEnchant = Enchantment.values()[(Math.random() * (Enchantment.values()).size).toInt()]
                    var rand2 = Random.nextInt(randEnchant.maxLevel)+1
                    if (rand2 > randEnchant.maxLevel) rand2 = 0
                    e.currentItem?.addUnsafeEnchantment(randEnchant,rand2)
                    pi.setItem(ptc.tempSlot!!, e.currentItem)
                }

                var usedSpell = ItemStack(pi.getItem(ptc.spellSlot!!)!!.type, pi.getItem(ptc.spellSlot!!)!!.amount - 1)
                usedSpell.itemMeta = spell
                pi.setItem(ptc.spellSlot!!, usedSpell)
                p.playSound(p.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.0F)
            }
        }
        e.isCancelled = true
    }

    @EventHandler
    fun guiOpen(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock!!.type == Material.ENCHANTING_TABLE) {
            e.isCancelled = true
            ptc.enchantTable(e.player)
        }
    }
}