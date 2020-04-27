package madpillow.tacticsMiners.game.skill.spy

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SpyInventoryListener : Listener {
    @EventHandler
    fun onInventoryClickEvent(e: InventoryClickEvent) {
        if (e.view.title.startsWith(SkillType.SPY.getName())) {
            return
        }

        e.isCancelled = true
        if (e.clickedInventory != e.view.topInventory) {
            return
        }

        val currentItem = e.currentItem ?: return
        val gameTeam = TacticsMiners.gameManager.gameTeamList
                .firstOrNull { it.getColoredWool().type == currentItem.type } ?: return
        val inventory = Bukkit.createInventory(e.whoClicked, InventoryUtils.getInventorySize(gameTeam.missionList.size, 1),
                "${SkillType.SPY.getName()}(${gameTeam.teamColor.getChatColor()}$gameTeam${ChatColor.RESET}のミッション)")
        gameTeam.missionList.forEach {
            inventory.addItem(it.getItemStack())
        }

        e.whoClicked.openInventory(inventory)
    }
}