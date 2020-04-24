package madpillow.tacticsMiners.game.mission

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MissionInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (!e.view.title.startsWith(Mission.inventoryNamePrefix)) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gamePlayer: GamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        val mission = gamePlayer.gameTeam?.getMission(e.view.title) ?: return

        e.isCancelled = true
        if (e.clickedInventory == e.view.bottomInventory) {
            val itemStack = mission.delivery(currentItem)
            e.view.bottomInventory.setItem(e.slot, itemStack)
            (e.whoClicked as Player).updateInventory()
            if (mission.isSuccess) {
                mission.inventory.viewers.filterIsInstance<Player>().forEach { it.closeInventory() }
            }
        }
    }
}