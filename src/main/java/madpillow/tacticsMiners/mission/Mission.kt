package madpillow.tacticsMiners.mission

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Mission(val title: String, val lore: MutableList<String>, val material: Material, val needAmount: Int) {
    companion object {
        const val inventoryNamePrefix = "MISSION:"
    }

    val inventory = Bukkit.createInventory(null, getInventorySize(), inventoryNamePrefix + title)
    var nowAmount = 0
    var isSuccess = false

    init {
        val needPos = needAmount / material.maxStackSize
        for (i in needPos until inventory.size)
            inventory.setItem(i, ItemStack(Material.BARRIER))
    }

    fun delivery(itemStack: ItemStack): ItemStack {
        if (isSuccess
                || this.material != itemStack.type) {
            return itemStack
        }

        val remain = needAmount - nowAmount
        return if (remain >= itemStack.amount) {
            inventory.addItem(itemStack)
            nowAmount += itemStack.amount

            ItemStack(Material.AIR)
        } else {
            inventory.addItem(ItemStack(material, remain))
            nowAmount = needAmount
            this.isSuccess = true

            itemStack.amount -= remain
            itemStack
        }
    }

    private fun getInventorySize(): Int {
        val posSize = needAmount / material.maxStackSize
        return when {
            posSize <= 9 -> 9
            posSize <= 18 -> 18
            posSize <= 27 -> 27
            posSize <= 36 -> 36
            posSize <= 45 -> 45
            else -> 54
        }
    }
}