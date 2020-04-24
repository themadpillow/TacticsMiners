package madpillow.tacticsMiners.game.enchant

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class EnchantInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title.startsWith(EnchantInventory.inventoryNamePrefix)) {
            return
        }
        if (e.clickedInventory == e.view.topInventory) {
            if (e.slot == 1) {
                e.isCancelled = true
                return
            } else if (e.slot == 2) {
                if (e.view.topInventory.getItem(2)?.type != Material.BARRIER) {
                    e.isCancelled = true

                    val bottomItemEnchantment = e.view.topInventory.getItem(1)?.enchantments ?: return
                    val item = e.view.topInventory.getItem(0) ?: return
                    val enchant = bottomItemEnchantment.toList()[Random.nextInt(bottomItemEnchantment.size)]
                    item.addEnchantment(enchant.first, enchant.second)

                    e.whoClicked.closeInventory()
                    (e.whoClicked as Player).playSound(e.whoClicked.location, Sound.BLOCK_ANVIL_USE, 1F, 0.5F)
                    (e.whoClicked as Player).playSound(e.whoClicked.location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1F, 1F)
                }
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                println(e.view.topInventory.getItem(0)?.type)
                e.view.topInventory.setItem(2, EnchantInventory.resultShowItem(e.view.getItem(0)))
            }
        }.runTaskLater(TacticsMiners.plugin, 0L)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (!e.view.title.startsWith(EnchantInventory.inventoryNamePrefix)) {
            return
        }

        if (e.view.topInventory.getItem(0)?.type != Material.AIR) {
            e.view.bottomInventory.addItem(e.view.topInventory.getItem(0))
        }
    }
}