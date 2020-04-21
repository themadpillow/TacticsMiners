package madpillow.tacticsMiners.mission

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class MissionListInventory(missionList: MutableList<Mission>) {
    val inventory = Bukkit.createInventory(null, 27)

    init {
        missionList.forEach {
            val itemStack = ItemStack(Material.PAPER)
            val itemMeta = itemStack.itemMeta
            itemMeta?.setDisplayName(it.title)
            itemMeta?.lore = it.lore
            if (it.isSuccess) {
                itemMeta?.addEnchant(Enchantment.LUCK, 1, true)
            }
            itemStack.itemMeta = itemMeta

            inventory.addItem(itemStack)
        }
    }
}