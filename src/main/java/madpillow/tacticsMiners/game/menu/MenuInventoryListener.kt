package madpillow.tacticsMiners.game.menu

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MenuInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != MenuInventory.inventoryName) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val menuType = MenuType.values().first { it.getMaterial() == currentItem.type }
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player)
                ?: return

        menuType.perform(gamePlayer)
    }
}