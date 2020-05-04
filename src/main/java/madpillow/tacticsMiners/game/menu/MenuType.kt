package madpillow.tacticsMiners.game.menu

import madpillow.tacticsMiners.game.GamePlayer
import madpillow.tacticsMiners.game.delivery.DeliveryInventory
import madpillow.tacticsMiners.game.shop.ShopListInventory
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class MenuType {
    DELIVERY, MISSION, SHOP, SKILL;

    fun getItemStack(): ItemStack {
        val itemStack = ItemStack(getMaterial())
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName(getName())
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    fun getPos(): Int {
        return when (this) {
            DELIVERY -> 1
            MISSION -> 3
            SHOP -> 5
            SKILL -> 7
        }
    }

    fun getMaterial(): Material {
        return when (this) {
            DELIVERY -> Material.MINECART
            MISSION -> Material.PAPER
            SHOP -> Material.EMERALD
            SKILL -> Material.NETHER_STAR
        }
    }

    fun getName(): String {
        return when (this) {
            DELIVERY -> "${ChatColor.GOLD}納品"
            MISSION -> "${ChatColor.GRAY}ミッション"
            SHOP -> "${ChatColor.GREEN}ショップ"
            SKILL -> "${ChatColor.LIGHT_PURPLE}スキルカード"
        }
    }

    fun perform(gamePlayer: GamePlayer) {
        when (this) {
            DELIVERY -> DeliveryInventory().openInventory(gamePlayer)
            MISSION -> gamePlayer.openMissionInventory()
            SHOP -> ShopListInventory.openInventory(gamePlayer)
            SKILL -> gamePlayer.openSkillInventory()
        }
    }
}