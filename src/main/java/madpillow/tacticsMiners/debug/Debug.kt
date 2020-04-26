package madpillow.tacticsMiners.debug

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Bukkit

class Debug {
    init {
        Bukkit.getPluginManager().registerEvents(DebugListener(), TacticsMiners.plugin)
    }
}