package madpillow.tacticsMiners.game.menu

import madpillow.tacticsMiners.game.GamePlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class MenuInventory(private val holder: GamePlayer) {
    companion object {
        const val inventoryName = "メニュー"
    }

    private val inventory = Bukkit.createInventory(null, 9, inventoryName)

    init {
        MenuType.values().forEach {
            if (it == MenuType.SKILL && !holder.canUseSkill()) {
                inventory.setItem(it.getPos(), ItemStack(Material.BARRIER))
            } else {
                inventory.setItem(it.getPos(), it.getItemStack())
            }
        }
    }

    fun openInventory() {
        holder.player.openInventory(inventory)
    }
}