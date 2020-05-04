package madpillow.tacticsMiners.game.mission

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MissionListInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != MissionListInventory.inventoryName) {
            return
        }

        e.isCancelled = true
        if (e.view.topInventory != e.clickedInventory) {
            return
        }

        val gameTeam = TacticsMiners.gameManager.getGameTeamAtList(e.whoClicked as Player)
                ?: return
        val itemName = e.currentItem?.itemMeta?.displayName ?: return
        val mission = gameTeam.missionList.firstOrNull { itemName.startsWith(it.title) }
                ?: return
        if (!mission.isSuccess) {
            e.whoClicked.openInventory(mission.inventory)
        }
    }
}