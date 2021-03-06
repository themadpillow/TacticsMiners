package madpillow.tacticsMiners.game.skill.spy

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SpyInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.view.title != SkillType.SPY.getName()) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        val skill = gamePlayer.skillInventory.skillList.firstOrNull { it.equal(currentItem) } ?: return
        val gameTeam = TacticsMiners.gameManager.gameTeamList
                .firstOrNull { it.getColoredWool().type == currentItem.type } ?: return

        skill.perform(gamePlayer, gameTeam)
    }
}