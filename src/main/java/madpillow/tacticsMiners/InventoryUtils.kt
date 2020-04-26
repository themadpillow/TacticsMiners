package madpillow.tacticsMiners

class InventoryUtils {
    companion object {
        fun getInventorySize(needAmount: Int, materialMaxStackSize: Int): Int {
            val posSize = needAmount / materialMaxStackSize
            return when {
                posSize <= 9 -> 9
                posSize <= 18 -> 18
                posSize <= 27 -> 27
                posSize <= 36 -> 36
                posSize <= 45 -> 45
                posSize <= 54 -> 54
                else -> throw RuntimeException("Inventoryの作成に失敗")
            }
        }
    }
}