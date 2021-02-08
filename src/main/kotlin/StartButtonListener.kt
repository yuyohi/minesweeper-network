import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JTextField


/**
 * スタートボタンのリスナークラス
 */
class StartButtonListener(
    private val game: MinesweeperGame,
    private val textField: JTextField,
    private var player: Player
) : ActionListener {
    override fun actionPerformed(e: ActionEvent) {
        var userName = textField.text
        if (userName == "") {
            userName = "unknown"
        }
        player.setNameDate(userName)
        // ゲームを開始
        game.startGame()
    }

}