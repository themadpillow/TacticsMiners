package madpillow.tacticsMiners.game.team

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

enum class TeamColor {
    RED, BLUE, GREEN, YELLOW, PURPLE, AQUA, GOLD, PINK;

    fun getChatColor(): ChatColor {
        return when (this) {
            RED -> ChatColor.RED
            BLUE -> ChatColor.BLUE
            GREEN -> ChatColor.GREEN
            YELLOW -> ChatColor.YELLOW
            PURPLE -> ChatColor.DARK_PURPLE
            AQUA -> ChatColor.AQUA
            GOLD -> ChatColor.GOLD
            PINK -> ChatColor.LIGHT_PURPLE
        }
    }

    fun getColoredWool(): ItemStack {
        val itemStack = when (this) {
            RED -> ItemStack(Material.RED_WOOL)
            BLUE -> ItemStack(Material.BLUE_WOOL)
            GREEN -> ItemStack(Material.GREEN_WOOL)
            YELLOW -> ItemStack(Material.YELLOW_WOOL)
            PURPLE -> ItemStack(Material.PURPLE_WOOL)
            AQUA -> ItemStack(Material.LIGHT_BLUE_WOOL)
            GOLD -> ItemStack(Material.ORANGE_WOOL)
            PINK -> ItemStack(Material.PINK_WOOL)
        }
        val itemMeta = itemStack.itemMeta!!
        itemMeta.setDisplayName("${getChatColor()}${this}")
        itemStack.itemMeta = itemMeta

        return itemStack
    }
}