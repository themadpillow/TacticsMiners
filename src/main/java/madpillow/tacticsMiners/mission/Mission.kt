package madpillow.tacticsMiners.mission

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Mission(private val material: Material, val lore: List<String>, private val needCount: Int, missionPos: Int) {
    private val inventory = Bukkit.createInventory(null, (needCount / 10 + 1) * 9, "MISSION$missionPos")
    private var nowCount = 0
    var isSuccess = false

    init {
        for (i in needCount until inventory.size)
            inventory.setItem(i, ItemStack(Material.BARRIER))
    }

    fun delivery(itemStack: ItemStack): ItemStack {
        if (isSuccess
                || this.material != itemStack.type) {
            return itemStack
        }

        if ((needCount - nowCount) >= itemStack.amount) {
            for (i in 0 until itemStack.amount) {
                inventory.setItem(nowCount + i, ItemStack(material))
            }
            nowCount += itemStack.amount
            return ItemStack(Material.AIR)
        } else {
            val need = needCount - nowCount
            for (i in 0 until need) {
                inventory.setItem(nowCount + i, ItemStack(material))
            }
            itemStack.amount -= need
            nowCount = needCount
            this.isSuccess = true

            return itemStack
        }
    }
}