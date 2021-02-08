import java.awt.Color
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

/**
 * ゲーム(ボード)画面のクラス
 *
 * @property gameBoard ゲームのボード
 * @property startTime 開始時間
 * @property endTime 終了時間
 */
class GameDisplay(boardSize: Int, bombNum: Int, private val game: MinesweeperGame) : JPanel() {

    val gameBoard = Board(bombNum, boardSize)

    var startTime = 0.0

    var endTime = 0.0

    init {
        layout = null

        setButton(50)

        startTime = System.currentTimeMillis().toDouble()
    }


    /**
     * ボタンを配置するメソッド
     */
    private fun setButton(buttonSize: Int) {
        gameBoard.board.forEachIndexed { i, row ->
            row.forEachIndexed { j, button ->
                button.background = Color.LIGHT_GRAY
                button.setBounds(i * buttonSize, j * buttonSize, buttonSize, buttonSize)

                add(button)

                val listener = ElementButtonListener(this, gameBoard, i, j)
                button.addActionListener(listener)
                button.addMouseListener(listener)
            }
        }
    }

    /**
     * ゲームを終了する
     *
     * @param winner ユーザーが勝っているかどうか
     */
    fun finishGame(winner: Boolean) {
        endTime = System.currentTimeMillis().toDouble()
        val time = (endTime - startTime) / 1000

        val finishDialog = JLabel(
            if (winner) {
                game.player.time = time
                "<html>Game clear!!<br>$time sec<html>"
            } else {
                "Game over"
            }
        )

        finishDialog.font = Font("Arial", Font.PLAIN, 28)

        JOptionPane.showMessageDialog(this, finishDialog)

        game.returnMenu(winner)
    }
}