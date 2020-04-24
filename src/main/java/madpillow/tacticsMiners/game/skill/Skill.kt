package madpillow.tacticsMiners.game.skill

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Skill(name: String, enchantment: Enchantment, level: Int) {
    val itemStack = ItemStack(Material.PAPER)
    init {
        val meta = itemStack.itemMeta!!
        meta.setDisplayName(name)
        meta.lore = getLore()
        meta.addEnchant(enchantment, level, true)
        itemStack.itemMeta = meta
    }

    abstract fun getLore(): MutableList<String>
    abstract fun perform(player : Player)
}