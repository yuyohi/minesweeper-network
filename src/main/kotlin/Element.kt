import java.awt.Color
import java.awt.Font
import javax.swing.ImageIcon
import javax.swing.JButton


/**
 * マス目を表すクラス
 *
 * @property hiddenFlag 隠れているかどうか
 * @property bomb 爆弾があるかどうか
 * @property aroundBomb 周りの爆弾の数
 * @property flag 旗が立っているかどうか
 * @property openElementNum 開いているマス目の数を表す静的フィールド
 */
class Element(
    var hiddenFlag: Boolean = true,
    var bomb: Boolean = false,
    var aroundBomb: Int = 0,
    var flag: Boolean = false
) : JButton() {

    companion object {
        var openElementNum = 0
    }

    /**
     * マス目を開ける
     */
    fun openElement() {
        if (hiddenFlag) openElementNum += 1
        hiddenFlag = false
        background = Color.WHITE
        if (bomb) { // 自分自身が爆弾のとき
            icon = ImageIcon(this::class.java.getResource("/img/bomb.png"))
            //  周りの爆弾の数を表示せずにreturn
            return
        }
        if (aroundBomb != 0) {  // 周りに爆弾があるとき
            text = aroundBomb.toString()
            font = Font("Arial", Font.PLAIN, 28)
        }
    }

    /**
     * 旗を立てる・取り消す
     */
    fun changeFrag() {
        if (hiddenFlag) {
            icon = if (flag) {
                null
            } else {
                ImageIcon(this::class.java.getResource("/img/flag.png"))
            }
            flag = !flag
        }
    }

    /**
     * マス目をリセットする
     */
    fun resetElement() {
        hiddenFlag = true
        bomb = false
        aroundBomb = 0
        flag = false
        background = Color.LIGHT_GRAY
        text = null
        icon = null
        openElementNum = 0
    }
}