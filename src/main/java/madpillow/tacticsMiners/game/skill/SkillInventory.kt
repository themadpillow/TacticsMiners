package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.InventoryUtils
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Bukkit

class SkillInventory(private val holder: GamePlayer) {
    val skillList = mutableListOf<Skill>()

    companion object {
        const val inventoryName = "スキル一覧"
    }

    fun addSkill(skill: Skill) {
        this.skillList.add(skill)
    }

    fun removeSkill(skill: Skill) {
        skillList.remove(skill)
    }

    fun openInventory() {
        val inventory = Bukkit.createInventory(holder.player, InventoryUtils.getInventorySize(skillList.size, 1), inventoryName)
        skillList.forEach { inventory.addItem(it.itemStack) }
        holder.player.openInventory(inventory)
    }
}