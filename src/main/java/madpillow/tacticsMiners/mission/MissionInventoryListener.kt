package madpillow.tacticsMiners.mission

import madpillow.tacticsMiners.GamePlayer
import madpillow.tacticsMiners.team.GameTeam
import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MissionInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (!e.view.title.contains("MISSION")) {
            return
        }
        val currentItem = e.currentItem ?: return
        val gamePlayer: GamePlayer = TeamUtils.getGamePlayerAtList(e.whoClicked as Player) ?: return
        val mission = gamePlayer.gameTeam?.getMission(e.view.title) ?: return

        e.isCancelled = true
        if (e.clickedInventory == e.view.bottomInventory) {
            val itemStack = mission.delivery(currentItem)
            e.inventory.setItem(e.slot, itemStack)
            (e.whoClicked as Player).updateInventory()
            if (mission.isSuccess) {
                e.whoClicked.closeInventory()
            }
        }
    }
}