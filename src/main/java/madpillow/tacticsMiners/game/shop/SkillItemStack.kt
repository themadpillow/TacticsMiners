package madpillow.tacticsMiners.game.shop

import madpillow.tacticsMiners.game.skill.Skill
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class SkillItemStack(skillType: SkillType, level: Int) : ItemStack(Material.PAPER) {
    val skill: Skill = Skill(skillType, level)
}