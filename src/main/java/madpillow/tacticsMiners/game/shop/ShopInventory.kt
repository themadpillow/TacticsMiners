package madpillow.tacticsMiners.game.shop

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.MerchantRecipe

class ShopInventory(shopInventoryType: ShopInventoryType) {
    private val merchant = Bukkit.createMerchant(shopInventoryType.getName())

    init {
        val merchantRecipes = mutableListOf<MerchantRecipe>()
        shopInventoryType.getRecipes().forEach { (result, needList) ->
            val recipe = MerchantRecipe(result, Int.MAX_VALUE)
            needList.forEach { recipe.addIngredient(it) }
            recipe.setExperienceReward(false)
            merchantRecipes.add(recipe)
        }
        merchant.recipes = merchantRecipes
    }

    fun openInventory(player: Player) {
        player.openMerchant(merchant, true)
    }
}