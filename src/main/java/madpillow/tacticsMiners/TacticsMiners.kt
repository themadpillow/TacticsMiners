package madpillow.tacticsMiners

import madpillow.tacticsMiners.command.CheckCommand
import madpillow.tacticsMiners.command.RecreateCommand
import madpillow.tacticsMiners.command.StartCommand
import madpillow.tacticsMiners.command.TeamCommand
import madpillow.tacticsMiners.config.ConfigUtils
import madpillow.tacticsMiners.debug.DebugListener
import madpillow.tacticsMiners.team.TeamUtils
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class TacticsMiners : JavaPlugin() {
    companion object {
        lateinit var plugin: TacticsMiners
        lateinit var gameManager: GameManager
    }

    override fun onEnable() {
        plugin = this
        ConfigUtils.init()
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

    }

    override fun onDisable() {
        TeamUtils.removeAllEntryAtAllTeam()
    }
}