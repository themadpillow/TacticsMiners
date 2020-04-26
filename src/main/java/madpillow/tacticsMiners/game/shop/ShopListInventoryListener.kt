package madpillow.tacticsMiners.game.shop

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class ShopListInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != ShopListInventory.inventoryName) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }
        val currentShopItem = ShopInventoryType.values().firstOrNull { it.getPos() == e.slot } ?: return
        ShopInventory(currentShopItem).openInventory(e.whoClicked as Player)
    }
}