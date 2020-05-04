package madpillow.tacticsMiners.game.skill.delivery

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.SkullMeta

class DeliverySelectTargetInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != DeliveryInventoryType.SELECT_TARGET.getInventoryTitle()) {
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

        DeliveryInventory(gamePlayer, skill, targetGamePlayer).openInventory()
    }
}