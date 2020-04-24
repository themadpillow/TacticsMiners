package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.config.SkillConfig
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DigSpeedSkill(private val level: Int) : Skill("採掘カード", Enchantment.DIG_SPEED, level) {
    private val time = SkillConfig.digSkillTimeMap[level]!!
    override fun getLore(): MutableList<String> {
        return mutableListOf(
                "採掘速度上昇${time}秒間"
        )
    }

    override fun perform(player: Player) {
        player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 20 * time, level))
    }
}