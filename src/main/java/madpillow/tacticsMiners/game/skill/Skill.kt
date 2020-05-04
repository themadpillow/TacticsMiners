package madpillow.tacticsMiners.game.skill

import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class Skill(val skillType: SkillType, val level: Int = 1) {
    val itemStack = ItemStack(Material.PAPER)

    init {
        if (level > skillType.maxLevel) {
            throw RuntimeException("Skillの最大Levelを超えています(${skillType.name})")
        }

        val itemMeta = this.itemStack.itemMeta!!
        itemMeta.setDisplayName(skillType.getName(level))
        itemMeta.lore = skillType.getLore(level)
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        val (key, data, value) = this.getPersistentData()
        itemMeta.persistentDataContainer.set(key, data, value)
        this.itemStack.itemMeta = itemMeta
        this.itemStack.addUnsafeEnchantment(Enchantment.LUCK, 1)
    }

    fun click(gamePlayer: GamePlayer) {
        skillType.click(gamePlayer, this)
    }

    fun perform(gamePlayer: GamePlayer, target: Any? = null, content: MutableList<ItemStack>? = null) {
        skillType.perform(gamePlayer, this, target, content)
    }

    fun equal(itemStack: ItemStack): Boolean {
        return this.hashCode() == itemStack.itemMeta!!.persistentDataContainer.get(NamespacedKey(TacticsMiners.plugin, "SKILL"), PersistentDataType.INTEGER)
    }

    fun getPersistentData(): Triple<NamespacedKey, PersistentDataType<Int, Int>, Int> {
        return Triple(NamespacedKey(TacticsMiners.plugin, "SKILL"), PersistentDataType.INTEGER, this.hashCode())
    }
}