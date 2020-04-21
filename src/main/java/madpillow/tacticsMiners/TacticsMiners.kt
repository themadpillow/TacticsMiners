package madpillow.tacticsMiners

import madpillow.tacticsMiners.command.CheckCommand
import madpillow.tacticsMiners.command.RecreateCommand
import madpillow.tacticsMiners.command.StartCommand
import madpillow.tacticsMiners.command.TeamCommand
import madpillow.tacticsMiners.config.DefaultConfigUtils
import madpillow.tacticsMiners.debug.DebugListener
import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin


class TacticsMiners : JavaPlugin() {
    companion object {
        lateinit var plugin: TacticsMiners
        lateinit var gameManager: GameManager
    }

    override fun onEnable() {
        plugin = this
        DefaultConfigUtils.init()
        gameManager = GameManager()
        TeamUtils.initCreateTeam()

        getCommand("check")?.setExecutor(CheckCommand())
        getCommand("recreate")?.setExecutor(RecreateCommand())
        getCommand("start")?.setExecutor(StartCommand())
        getCommand("team")?.setExecutor(TeamCommand())

        debug()
    }

    private fun debug() {
        Bukkit.getPluginManager().registerEvents(DebugListener(), this)

        Bukkit.getOnlinePlayers().forEach {
            val gamePlayer = GamePlayer(it)
        }

        val list = ArrayList<Material>()
        list.add(Material.DIAMOND)
        list.add(Material.EMERALD)
        config.set("debug", Material.DIAMOND)
        config.set("debug1", list)
        println(config.get("debug"))
        println(config.get("debug") as Material)
        println(config.get("debug1"))
        println(config.get("debug1") as ArrayList<*>)

        saveConfig()
    }

    override fun onDisable() {
        TeamUtils.removeAllEntryAtAllTeam()
    }
}