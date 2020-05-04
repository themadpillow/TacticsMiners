package madpillow.tacticsMiners.game.skill.ore

import madpillow.tacticsMiners.config.SkillConfig
import madpillow.tacticsMiners.game.skill.SkillType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class OreType {
    WHITE, RED, BLUE;

    companion object {
        fun valueOf(level: Int): OreType {
            return when (level) {
                1 -> WHITE
                2 -> RED
                3 -> BLUE
                else -> throw RuntimeException("OreTypeの取得に失敗")
            }
        }
    }

    private fun getLevel(): Int {
        return when (this) {
            WHITE -> 1
            RED -> 2
            BLUE -> 3
        }
    }

    fun getDisplayName(): String {
        return when (this) {
            WHITE -> "白"
            RED -> "赤"
            BLUE -> "青"
        }
    }

    fun getMaterial(): Material {
        return when (this) {
            WHITE -> Material.IRON_INGOT
            RED -> Material.REDSTONE
            BLUE -> Material.LAPIS_LAZULI
        }
    }

    fun getItemStack(): ItemStack {
        return ItemStack(this.getMaterial(), SkillConfig.getData(SkillType.ORE, this.getLevel()) as Int)
    }
}