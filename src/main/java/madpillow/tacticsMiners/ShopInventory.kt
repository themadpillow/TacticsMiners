package madpillow.tacticsMiners

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.MerchantRecipe
import java.util.ArrayList

class ShopInventory(title: String, recipeList: Map<ItemStack, MutableList<ItemStack>>) {
    private val merchant = Bukkit.createMerchant(title)

    init {
        val merchantRecipes = mutableListOf<MerchantRecipe>()
        recipeList.forEach { (result, needList) ->
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

    fun openInventory(gamePlayer: GamePlayer) {
        openInventory(gamePlayer.player)
    }
}