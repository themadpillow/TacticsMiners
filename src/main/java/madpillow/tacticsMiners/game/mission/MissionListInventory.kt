package madpillow.tacticsMiners.game.mission

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.team.GameTeam
import org.bukkit.Bukkit

class MissionListInventory(private val holder: GameTeam) {
    companion object {
        const val inventoryName = "ミッションリスト"
    }

    fun openInventory(gamePlayer: GamePlayer) {
        val inventory = Bukkit.createInventory(null, InventoryUtils.getInventorySize(holder.missionList.size, 1), inventoryName)

        holder.missionList.forEach {
            inventory.addItem(it.getItemStack())
        }

        gamePlayer.player.openInventory(inventory)
    }
}