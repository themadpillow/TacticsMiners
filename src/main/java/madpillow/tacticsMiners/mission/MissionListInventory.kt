package madpillow.tacticsMiners.mission

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class MissionListInventory(missionMap: Map<String, Mission>) {
    val inventory = Bukkit.createInventory(null, 27)

    init {
        missionMap.forEach { (missionName, mission) ->
            val itemStack = ItemStack(Material.PAPER)
            val itemMeta = itemStack.itemMeta
            itemMeta?.setDisplayName(missionName)
            itemMeta?.lore = mission.lore
            if (mission.isSuccess) {
                itemMeta?.addEnchant(Enchantment.LUCK, 1, true)
            }
            itemStack.itemMeta = itemMeta

            inventory.addItem(itemStack)
        }
    }
}