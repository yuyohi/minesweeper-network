import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.SwingUtilities

/**
 * マス目のリスナークラス
 *
 * @property game ゲームディスプレイ
 * @property gameBoard ゲームボード
 * @property i マス目の行番号
 * @property j マス目の列番号
 * @property clearNum クリアするのに必要な開いているマス目
 */
class ElementButtonListener(
    private val game: GameDisplay,
    private val gameBoard: Board,
    private val i: Int,
    private val j: Int
) : ActionListener,
    MouseListener {

    private val clearNum = gameBoard.boardSize * gameBoard.boardSize - gameBoard.bombNum

    /**
     * クリックしたときの挙動
     */
    override fun actionPerformed(e: ActionEvent) {
        if (gameBoard.firstTime) {
            gameBoard.initial(i, j)
        }

        val success = gameBoard.openBoard(i, j, true)
        if (!success) {
            game.finishGame(false)
            return
        }

        if (Element.openElementNum == clearNum) {
            game.finishGame(true)
        }
    }

    /**
     * 右クリックしたときに旗を立てる
     */
    override fun mouseClicked(e: MouseEvent) {
        if (SwingUtilities.isRightMouseButton(e)) {  // 右クリックのとき
            gameBoard.board[i][j].changeFrag()
        }
    }

    //  以降は実装する必要があるが中身は使用しないので空
    override fun mouseEntered(e: MouseEvent) {
    }

    override fun mouseExited(e: MouseEvent) {
    }

    override fun mousePressed(e: MouseEvent) {
    }

    override fun mouseReleased(e: MouseEvent?) {
    }

}