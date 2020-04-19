package madpillow.tacticsMiners.command

import madpillow.tacticsMiners.TacticsMiners
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class RecreateCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (sender.world == TacticsMiners.gameManager.gameWorld) {
                sender.teleport(Bukkit.getWorlds()[0].spawnLocation)
            }

            TacticsMiners.gameManager.reCreateGameWorld()
            object : BukkitRunnable() {
                override fun run() {
                    val world = Bukkit.getWorld(TacticsMiners.gameManager.worldName)
                    world?.also {
                        sender.teleport(world.spawnLocation)
                        this.cancel()
                        return
                    } ?: run {
                        sender.sendMessage("World生成中...")
                    }
                }
            }.runTaskTimer(TacticsMiners.plugin, 20L, 5 * 20L)

            return true
        }

        return false
    }

}