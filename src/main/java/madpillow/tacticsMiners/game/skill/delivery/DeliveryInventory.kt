package madpillow.tacticsMiners.game.skill.delivery

import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.skill.Skill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

class DeliveryInventory(private val holder: GamePlayer, skill: Skill, target: GamePlayer) {
    private val inventory = Bukkit.createInventory(holder.player, 18, DeliveryInventoryType.DELIVERY.getInventoryTitle())

    init {
        for (pos in 9 until 18) {
            val deliveryItem = ItemStack(Material.PLAYER_HEAD)
            val deliveryMeta = deliveryItem.itemMeta as SkullMeta
            deliveryMeta.owningPlayer = target.player
            deliveryMeta.setDisplayName("${target.gameTeam!!.teamColor.getChatColor()}配達")
            val (key, data, value) = skill.getPersistentData()
            deliveryMeta.persistentDataContainer.set(key, data, value)
            deliveryItem.itemMeta = deliveryMeta
        }
    }

    fun openInventory() {
        holder.player.openInventory(inventory)
    }
}