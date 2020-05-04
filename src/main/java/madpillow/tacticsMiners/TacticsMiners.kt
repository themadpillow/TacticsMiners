package madpillow.tacticsMiners

import madpillow.tacticsMiners.command.AllCommand
import madpillow.tacticsMiners.command.RecreateCommand
import madpillow.tacticsMiners.command.StartCommand
import madpillow.tacticsMiners.command.TeamCommand
import madpillow.tacticsMiners.config.*
import madpillow.tacticsMiners.game.GameManager
import madpillow.tacticsMiners.game.bossbar.BossBarUtils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin


class TacticsMiners : JavaPlugin() {
    companion object {
        lateinit var plugin: TacticsMiners
        lateinit var gameManager: GameManager
    }

    override fun onEnable() {
        plugin = this
        DefaultConfig.init()
        MissionConfig.init()
        SkillConfig.init()
        TextConfig.init()
        ShopConfig.init()
        OreConfig.init()
        ScoreboardConfig.init()
        gameManager = GameManager()

        getCommand("recreate")?.setExecutor(RecreateCommand())
        getCommand("start")?.setExecutor(StartCommand())
        getCommand("team")?.setExecutor(TeamCommand())
        getCommand("all")?.setExecutor(AllCommand())

        checkConfig()
//        Debug()
    }

    override fun onDisable() {
        BossBarUtils.destroy()
        HandlerList.unregisterAll(this)
    }

    fun reload() {
        HandlerList.unregisterAll(this)
        gameManager = GameManager()
    }

    fun checkConfig(sender: CommandSender? = null) {
        if (Bukkit.getAllowNether()) {
            if (sender == null)
                println("[WARNING] ネザーに行ける設定になっています(server.properties)")
            else
                sender.sendMessage("[WARNING] ネザーに行ける設定になっています(server.properties)")
        }
        if (Bukkit.getAllowEnd()) {
            if (sender == null)
                println("[WARNING] エンドに行ける設定になっています(bukkit.yml)")
            else
                sender.sendMessage("[WARNING] エンドに行ける設定になっています(bukkit.yml)")
        }
    }
}