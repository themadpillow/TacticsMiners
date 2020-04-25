package madpillow.tacticsMiners.game.skill

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SkillInventory(private val holder: Player) {
    private val skillList = mutableListOf<Skill>()

    companion object {
        const val inventoryName = "スキル一覧"
    }

    private val inventory = Bukkit.createInventory(holder, 27, inventoryName)

    fun addSkill(skill: Skill) {
        this.skillList.add(skill)
    }

    fun removeSkill(skill: Skill) {
        skillList.remove(skill)
    }

    fun openInventory() {
        val openInv = inventory
        for (i in 0 until skillList.size) {
            openInv.setItem(i, skillList.get(i).itemStack)
        }

        holder.openInventory(openInv)
    }
}