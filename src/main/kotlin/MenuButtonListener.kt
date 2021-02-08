import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * メニューボタンのリスナークラス
 */
class MenuButtonListener(private val game: MinesweeperGame) : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        game.returnMenu(false)
    }
}