package madpillow.tacticsMiners.game.skill.soldier

enum class SoldierInventoryType {
    SELECT_TARGET, SELECT_PLAYER;

    fun getInventoryTitle():String{
        return when(this){
            SELECT_TARGET -> "防衛先指定"
            SELECT_PLAYER -> "防衛先指定（未防衛プレイヤー）"
        }
    }
}