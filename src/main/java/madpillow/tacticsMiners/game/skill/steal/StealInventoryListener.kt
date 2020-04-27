package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import kotlin.random.Random

class StealInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != SkillType.STEAL.getName()) {
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
        val targetMission = gameTeam.missionList[Random.nextInt(gameTeam.missionList.size)]

        skill.perform(gamePlayer, targetMission)
    }
}