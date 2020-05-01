package madpillow.tacticsMiners.game.skill.curse

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.meta.SkullMeta

class CurseInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.view.title != SkillType.CURSE.getName()) {
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

        skill.perform(gamePlayer, targetGamePlayer)
    }
}