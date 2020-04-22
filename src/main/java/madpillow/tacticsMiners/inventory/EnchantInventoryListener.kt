package madpillow.tacticsMiners.inventory

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import kotlin.random.Random

class EnchantInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != "エンチャント") {
            return
        }
        if (e.clickedInventory == e.view.topInventory) {
            if (e.slot == 1) {
                e.isCancelled = true
                return
            } else if (e.slot == 2) {
                if (e.view.topInventory.getItem(2)?.type != Material.BARRIER) {
                    e.isCancelled = true

                    val bottomItemEnchantment = e.view.topInventory.getItem(1)?.enchantments!!
                    val item = e.view.topInventory.getItem(0)
                    val enchant = bottomItemEnchantment.toList()[Random.nextInt(bottomItemEnchantment.size)]
                    item?.addEnchantment(enchant.first, enchant.second)

                    e.whoClicked.closeInventory()
                    // TODO sound
                }
            }
        }

        object : BukkitRunnable() {
            override fun run() {
                println(e.view.topInventory.getItem(0)?.type)

                if (CanEnchantItem.list.contains(e.view.topInventory.getItem(0)?.type)) {
                    e.view.topInventory.setItem(2, resultShowItem(e.view.getItem(0)!!))
                } else {
                    e.view.topInventory.setItem(2, EnchantInventory.nullResultItem)
                }
            }
        }.runTaskLater(TacticsMiners.plugin, 0L)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.view.title != "エンチャント") {
            return
        }

        if (e.view.topInventory.getItem(0)?.type != Material.AIR) {
            e.view.bottomInventory.addItem(e.view.topInventory.getItem(0))
        }
    }

    private fun resultShowItem(itemStack: ItemStack): ItemStack {
        val resultItem = ItemStack(itemStack.type)
        val resultMeta = resultItem.itemMeta!!
        resultMeta.setDisplayName("エンチャント結果")
        resultMeta.lore = mutableListOf("§kENCHANT")
        resultMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        resultItem.itemMeta = resultMeta
        resultItem.addUnsafeEnchantment(Enchantment.LUCK, 1)

        return resultItem
    }
}