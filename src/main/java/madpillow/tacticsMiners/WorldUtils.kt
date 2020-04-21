package madpillow.tacticsMiners

import madpillow.tacticsMiners.config.DefaultConfigUtils
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import java.io.File

class WorldUtils {
    companion object {
        fun reCreateWorld(worldName: String): World {
            var world: World? = Bukkit.getWorld(worldName)
            deleteWorld(world)

            world = WorldCreator(worldName).createWorld()
            world?.worldBorder?.size = DefaultConfigUtils.getWorldBorderSize()
            world?.pvp = false
            return world!!
        }

        private fun deleteWorld(world: World?): Boolean {
            world?.worldFolder?.let {
                Bukkit.unloadWorld(world.name, false)
                val file = world.worldFolder
                return deleteFile(file)
            } ?: run {
                return false
            }
        }

        private fun deleteFile(path: File): Boolean {
            if (path.exists()) {
                val files = path.listFiles()
                files?.forEach { file ->
                    if (file.isDirectory) {
                        deleteFile(file)
                    } else {
                        file.delete()
                    }
                }
            }
            return path.delete()
        }
    }

}