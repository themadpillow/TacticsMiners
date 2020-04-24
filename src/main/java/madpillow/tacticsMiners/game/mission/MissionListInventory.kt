package madpillow.tacticsMiners.game.mission

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class MissionListInventory(missionList: MutableList<Mission>) {
    companion object {
        const val inventoryName = "ミッションリスト"
    }

    val inventory = Bukkit.createInventory(null, 27, inventoryName)

    init {
        missionList.forEach {
            val itemStack = ItemStack(Material.PAPER)
            val itemMeta = itemStack.itemMeta
            itemMeta?.setDisplayName(it.title + "(${it.nowAmount}/${it.needAmount})")
            itemMeta?.lore = it.lore
            if (it.isSuccess) {
                itemMeta?.addEnchant(Enchantment.LUCK, 1, true)
            }
            itemStack.itemMeta = itemMeta

            inventory.addItem(itemStack)
        }
    }
}