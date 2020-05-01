package madpillow.tacticsMiners.game.skill.soldier

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SoldierSelectTargetInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != SoldierInventoryType.SELECT_TARGET.getInventoryTitle()) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        val skill = gamePlayer.skillInventory.skillList.firstOrNull { it.equal(currentItem) } ?: return
        if (currentItem.type == Material.PLAYER_HEAD) {
            SoldierSelectPlayerInventory(gamePlayer, skill).openInventory()
        } else {
            skill.perform(gamePlayer, gamePlayer.gameTeam!!)
        }
    }
}