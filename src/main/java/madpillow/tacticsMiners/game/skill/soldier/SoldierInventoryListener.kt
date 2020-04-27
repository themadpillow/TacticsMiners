package madpillow.tacticsMiners.game.skill.soldier

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class SoldierInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != SoldierInventoryType.SELECT.getInventoryTitle()
                && e.view.title != SoldierInventoryType.PLAYER.getInventoryTitle()) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        if (currentItem.type == gamePlayer.gameTeam!!.getColoredWool().type) {
            //TODO
        }
        //TODO
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        if (e.view.title != SoldierInventoryType.SELECT.getInventoryTitle()
                && e.view.title != SoldierInventoryType.PLAYER.getInventoryTitle()) {
            val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.player as Player)!!
            gamePlayer.soldierInventory = null
        }
    }
}