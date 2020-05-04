package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.config.OreConfig
import org.bukkit.Material

enum class Ore {
    COAL, REDSTONE, LAPIS_LAZULI, IRON_ORE, GOLD_ORE, IRON_INGOT, GOLD_INGOT, DIAMOND, EMERALD, REDSTONE_ORE,
    DIAMOND_ORE, EMERALD_ORE;

    companion object {
        fun contains(material: Material): Ore? {
            return try {
                valueOf(material.name)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    fun getPoint(): Int {
        return OreConfig.getData(this)
    }

    fun getDefaultPoint(): Int {
        return when (this) {
            COAL -> 1
            REDSTONE -> 2
            LAPIS_LAZULI -> 3
            IRON_ORE -> 4
            GOLD_ORE -> 5
            IRON_INGOT -> 6
            GOLD_INGOT -> 7
            DIAMOND -> 8
            EMERALD -> 9
            REDSTONE_ORE -> 10
            DIAMOND_ORE -> 11
            EMERALD_ORE -> 12
        }
    }

    fun getMaterial(): Material {
        return Material.valueOf(this.name)
    }

}