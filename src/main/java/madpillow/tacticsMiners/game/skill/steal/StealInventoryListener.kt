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
        if (currentItem !is StealTargetItemStack) {
            return
        }
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        val gameTeam = TacticsMiners.gameManager.gameTeamList
                .firstOrNull { it.teamColor.getColoredWool().type == currentItem.type } ?: return
        val targetMission = gameTeam.missionList[Random.nextInt(gameTeam.missionList.size)]

        currentItem.skill.perform(gamePlayer, targetMission)
    }
}