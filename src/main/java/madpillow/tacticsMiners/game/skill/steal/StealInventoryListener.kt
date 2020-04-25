package madpillow.tacticsMiners.game.skill.steal

import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class StealInventoryListener : Listener {
    @EventHandler
    fun onInventoryClick(e : InventoryClickEvent){
        if(e.view.title != SkillType.STEAL.getName()){
            return
        }

        e.isCancelled = true
        if(e.clickedInventory != e.view.topInventory){
            return
        }

        val currentItem = e.currentItem ?: return
        // TODO get gameTeam & steal
    }
}