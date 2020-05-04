package madpillow.tacticsMiners.game.delivery

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.Ore
import madpillow.tacticsMiners.game.menu.MenuType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class DeliveryInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != MenuType.DELIVERY.getName()) {
            return
        }

        val currentItem = e.currentItem ?: return
        if (Ore.contains(currentItem.type) == null) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.view.title != MenuType.DELIVERY.getName()) {
            return
        }

        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.player as Player)
                ?: return
        e.view.topInventory.filterNotNull().forEach {
            val ore = Ore.contains(it.type)
            if (ore == null) {
                e.player.inventory.addItem(it)
            } else {
                gamePlayer.gameTeam!!.addPoint(ore.getPoint() * it.amount)
            }
        }
    }

}