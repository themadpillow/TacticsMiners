package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class Skill(private val skillType: SkillType, private val level: Int) {
    val itemStack = ItemStack(Material.PAPER)

    init {
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName(skillType.getName(level))
        itemMeta.lore = skillType.getLore(level)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        itemStack.itemMeta = itemMeta
        itemStack.addUnsafeEnchantment(Enchantment.LUCK, 1)
    }

    fun perform(gamePlayer: GamePlayer) {
        skillType.perform(gamePlayer, level)
    }
}