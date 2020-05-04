package madpillow.tacticsMiners

import madpillow.tacticsMiners.game.GameListener
import madpillow.tacticsMiners.game.enchant.EnchantInventoryListener
import madpillow.tacticsMiners.game.menu.MenuInventoryListener
import madpillow.tacticsMiners.game.mission.MissionInventoryListener
import madpillow.tacticsMiners.game.mission.MissionListInventoryListener
import madpillow.tacticsMiners.game.shop.ShopListInventoryListener
import madpillow.tacticsMiners.game.shop.SkillShopInventoryListener
import madpillow.tacticsMiners.game.skill.SkillInventoryListener
import madpillow.tacticsMiners.game.skill.assasin.AssassinInventoryListener
import madpillow.tacticsMiners.game.skill.curse.CurseInventoryListener
import madpillow.tacticsMiners.game.skill.delivery.DeliverySelectTargetInventoryListener
import madpillow.tacticsMiners.game.skill.mute.MuteInventoryListener
import madpillow.tacticsMiners.game.skill.shuffle.ShuffleInventoryListener
import madpillow.tacticsMiners.game.skill.soldier.SoldierSelectPlayerInventoryListener
import madpillow.tacticsMiners.game.skill.soldier.SoldierSelectTargetInventoryListener
import madpillow.tacticsMiners.game.skill.spy.SpyInventoryListener
import madpillow.tacticsMiners.game.skill.steal.StealInventoryListener
import org.bukkit.Bukkit

class ListenerManager {
    init {
        val listeners = mutableListOf(
                GameListener(),
                EnchantInventoryListener(),
                MenuInventoryListener(),
                MissionInventoryListener(),
                MissionListInventoryListener(),
                ShopListInventoryListener(),
                SkillShopInventoryListener(),
                AssassinInventoryListener(),
                CurseInventoryListener(),
                madpillow.tacticsMiners.game.skill.delivery.DeliveryInventoryListener(),
                DeliverySelectTargetInventoryListener(),
                MuteInventoryListener(),
                ShuffleInventoryListener(),
                SoldierSelectPlayerInventoryListener(),
                SoldierSelectTargetInventoryListener(),
                SpyInventoryListener(),
                StealInventoryListener(),
                SkillInventoryListener(),
                madpillow.tacticsMiners.game.delivery.DeliveryInventoryListener()
        )

        listeners.forEach { Bukkit.getPluginManager().registerEvents(it, TacticsMiners.plugin) }
    }


}