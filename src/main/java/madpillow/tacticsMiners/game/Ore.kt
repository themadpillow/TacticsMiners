package madpillow.tacticsMiners.game

import org.bukkit.Material

enum class Ore {
    COAL, REDSTONE, LAPIS_LAZULI, IRON_ORE, GOLD_ORE, IRON_INGOT, GOLD_INGOT, DIAMOND, EMERALD, REDSTONE_ORE,
    DIAMOND_ORE, EMERALD_ORE;

    fun getPoint(): Int {
        //TODO
        return 0
    }

    fun getMaterial(): Material {
        return Material.valueOf(this.name)
    }
}