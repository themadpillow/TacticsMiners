package madpillow.tacticsMiners.inventory

import org.bukkit.Material

class CanEnchantItem {
    companion object {
        val list = mutableListOf<Material>(
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.IRON_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE
        )
    }
}