package madpillow.tacticsMiners.game.skill.delivery

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scheduler.BukkitRunnable

class DeliveryInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != DeliveryInventoryType.DELIVERY.getInventoryTitle()) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player)
                ?: return
        val skill = gamePlayer.skillInventory.skillList.firstOrNull { it.equal(currentItem) }
                ?: return
        val skullMeta = currentItem.itemMeta as SkullMeta
        val targetGamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(skullMeta.owningPlayer!! as Player)
                ?: return
        val deliveryItems = e.view.topInventory.contents.toMutableList().subList(0, 9)
        if (deliveryItems.firstOrNull { it?.type != Material.AIR } == null) {
            return
        }

        skill.perform(gamePlayer, targetGamePlayer, deliveryItems)
    }


    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.view.title != DeliveryInventoryType.DELIVERY.getInventoryTitle()) {
            return
        }

        val deliveryItems = e.view.topInventory.contents.toMutableList().subList(0, 9)
        object : BukkitRunnable() {
            override fun run() {
                e.player.inventory.addItem(*deliveryItems.toTypedArray())
            }
        }.runTaskLater(TacticsMiners.plugin, 1L)
    }
}