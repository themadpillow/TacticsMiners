package madpillow.tacticsMiners.game.skill.delivery

enum class DeliveryInventoryType {
    SELECT_TARGET, DELIVERY;

    fun getInventoryTitle(): String {
        return when (this) {
            SELECT_TARGET -> "配達先指定"
            DELIVERY -> "配達物を入れてください"
        }
    }
}