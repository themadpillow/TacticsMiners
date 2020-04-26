package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Skill(private val skillType: SkillType, val level: Int) : ItemStack(Material.PAPER) {
    init {
        if (level > skillType.maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています")
        }

        val itemMeta = this.itemMeta!!
        itemMeta.setDisplayName(skillType.getName(level))
        itemMeta.lore = skillType.getLore(level)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        this.itemMeta = itemMeta
        this.addUnsafeEnchantment(Enchantment.LUCK, 1)
    }

    fun click(gamePlayer: GamePlayer) {
        skillType.click(gamePlayer, this)
    }

    fun perform(gamePlayer: GamePlayer, target: Any) {
        skillType.perform(gamePlayer, this, target)
    }
}