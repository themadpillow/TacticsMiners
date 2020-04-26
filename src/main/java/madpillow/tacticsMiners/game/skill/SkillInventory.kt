package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Bukkit

class SkillInventory(private val holder: GamePlayer) {
    private val skillList = mutableListOf<Skill>()

    companion object {
        const val inventoryName = "スキル一覧"
    }

    val inventory = Bukkit.createInventory(holder.player, 27, inventoryName)

    fun addSkill(skill: Skill) {
        this.skillList.add(skill)
    }

    fun removeSkill(skill: Skill) {
        skillList.remove(skill)
    }

    fun openInventory() {
        val openInv = inventory
        for (i in 0 until skillList.size) {
            openInv.setItem(i, skillList[i])
        }

        holder.player.openInventory(openInv)
    }
}