import java.awt.event.ActionEvent
import java.awt.event.ActionListener

/**
 * リセットボタンのリスナークラス
 */
class ResetButtonListener(private val game: MinesweeperGame) : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        game.reset()
    }
}