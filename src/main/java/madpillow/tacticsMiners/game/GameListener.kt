package madpillow.tacticsMiners.game

import madpillow.tacticsMiners.PlayerUtils
import madpillow.tacticsMiners.TacticsMiners
import madpillow.tacticsMiners.game.bossbar.BossBarUtils
import madpillow.tacticsMiners.game.enchant.EnchantInventory
import madpillow.tacticsMiners.game.enchant.EnchantLevel
import madpillow.tacticsMiners.game.menu.MenuInventory
import madpillow.tacticsMiners.game.menu.MenuItem
import madpillow.tacticsMiners.game.scoreboard.ScoreBoardUtils
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.*

class GameListener : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.player)
                ?: return

        if (!gamePlayer.canChat()) {
            e.isCancelled = true
            gamePlayer.sendMutingChatMessage()
            return
        }

        val gameTeam = gamePlayer.gameTeam ?: return
        e.isCancelled = true
        gameTeam.players.filter { it.canChat() }.forEach {
            PlayerUtils.sendMessage(it, "<${e.player.name}> ${e.message}")
        }
    }

    @EventHandler
    fun onRightClickEvent(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_AIR
                && e.action != Action.RIGHT_CLICK_BLOCK) {
            return
        }

        val handMeta = e.item?.itemMeta ?: return
        val gamePlayer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.player)
                ?: return
        if (handMeta.displayName == MenuInventory.inventoryName) {
            MenuInventory(gamePlayer).openInventory()
        } else if (EnchantLevel.getPersistentValue(handMeta) != null) {
            val enchantLevel = EnchantLevel.valueOf(EnchantLevel.getPersistentValue(handMeta)!!)
            EnchantInventory(enchantLevel).openInventory(gamePlayer)
        }
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (!TacticsMiners.gameManager.isGaming) {
            return
        }

        TacticsMiners.gameManager.getHardGamePlayerAtPlayer(e.player)?.also {
            if (it.player != e.player) {
                it.player = e.player
            }
            ScoreBoardUtils.createScoreBoard(it)
            BossBarUtils.setBossBar(e.player)

        } ?: run {
            e.player.gameMode = GameMode.SPECTATOR
            e.player.sendMessage("ゲーム中のため観戦となりました")
        }
    }

    @EventHandler
    fun onPlayerDamage(e: EntityDamageEvent) {
        if (e.entity !is Player) {
            return
        }

        if (!TacticsMiners.gameManager.isGaming || TimerTask.isInterval) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(e: PlayerDeathEvent) {
        if (!TacticsMiners.gameManager.isGaming) {
            return
        }

        val entity = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.entity)
                ?: return

        entity.player.inventory.removeAll { it.type == MenuItem().type }

        if (e.entity.killer is Player) {
            val killer = TacticsMiners.gameManager.getGamePlayerAtPlayer(e.entity.killer as Player)
                    ?: return
            e.deathMessage = "${entity.getDisplayName()}は${killer.getDisplayName()}に暗殺された"
        }
    }

    @EventHandler
    fun onDropMenu(e: PlayerDropItemEvent) {
        if (e.itemDrop.customName == MenuInventory.inventoryName
                || e.itemDrop.name == MenuInventory.inventoryName) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onRespawn(e: PlayerRespawnEvent) {
        if (TacticsMiners.gameManager.isGaming) {
            e.player.inventory.setItem(8, MenuItem())
        }
    }
}