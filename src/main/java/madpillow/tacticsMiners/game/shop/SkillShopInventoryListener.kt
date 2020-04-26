package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.TradeSelectEvent

class SkillShopInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e: TradeSelectEvent) {
        if (e.view.title != ShopInventoryType.SKILL.getName()) {
            return
        }

        val itemStack = e.merchant.recipes[e.index].result
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.whoClicked as Player) ?: return
        if (itemStack is Skill) {
            gamePlayer.skillInventory.addSkill(itemStack)
        }
    }
}