package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class Skill(private val skillType: SkillType, val level: Int = 1) {
    val itemStack = ItemStack(Material.PAPER)

    init {
        if (level > skillType.maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています")
        }

        val itemMeta = this.itemStack.itemMeta!!
        itemMeta.setDisplayName(skillType.getName(level))
        itemMeta.lore = skillType.getLore(level)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        itemMeta.persistentDataContainer.set(NamespacedKey(TacticsMiners.plugin, "SKILL"), PersistentDataType.INTEGER, this.hashCode())
        this.itemStack.itemMeta = itemMeta
        this.itemStack.addUnsafeEnchantment(Enchantment.LUCK, 1)
    }

    fun click(gamePlayer: GamePlayer) {
        skillType.click(gamePlayer, this)
    }

    fun perform(gamePlayer: GamePlayer, target: Any) {
        skillType.perform(gamePlayer, this, target)
    }

    fun equal(itemStack: ItemStack): Boolean {
        return this.hashCode() != itemStack.itemMeta!!.persistentDataContainer.get(NamespacedKey(TacticsMiners.plugin, "SKILL"), PersistentDataType.INTEGER)
    }
}