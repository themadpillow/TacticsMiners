package madpillow.tacticsMiners.game.skill

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SkillInventory(holder: Player) {
    companion object {
        const val inventoryName = "スキル一覧"
    }

    val inventory = Bukkit.createInventory(holder, 27, inventoryName)
}