package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.shop.SkillItemStack
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SkillInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if (e.view.title != SkillInventory.inventoryName) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val itemStack = e.currentItem ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        if (itemStack is SkillItemStack) {
            // TODO open confirm inventory
            e.whoClicked.closeInventory()
            gamePlayer.skillInventory.removeSkill(itemStack.skill)
            itemStack.skill.perform(gamePlayer)
        }
    }
}