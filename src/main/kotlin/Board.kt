import java.awt.Color

/**
 * マインスイーパーのボードを表すクラス
 *
 * @property bombNum 爆弾の数
 * @property boardSize ボードの縦横のマス目
 * @property board マインスイーパーのマス目を表現する二次元配列
 * @property firstTime ユーザーが一回もマス目を開けていないかどうか
 */
class Board(val bombNum: Int, val boardSize: Int) {

    val board = Array(boardSize) { Array(boardSize) { Element() } }

    var firstTime = true

    /**
     * ボードを初期化する関数
     *
     * @param i ユーザーが選択した行数
     * @param j ユーザーが選択した列数
     */
    fun initial(i: Int, j: Int) {
        setBomb(i, j)
        countAroundBomb()
        firstTime = false
    }

    /**
     * 爆弾を配置する
     *
     * @param x ユーザーが選択したマス目の行番号
     * @param y ユーザーが選択したマス目の列番号
     */
    private fun setBomb(x: Int, y: Int) {
        val bombPosition = MutableList(boardSize * boardSize) { it }
        bombPosition.shuffle()
        var i = 0


        for (num in bombPosition) {
            val position = Pair(num / boardSize, num % boardSize)

            // ユーザが選択した場所と周囲8マスに爆弾が配置されてしまった場合
            var continueFlag = false
            for (n in -1..1) {
                if (continueFlag) continue
                for (m in -1..1) {
                    if (continueFlag) continue
                    continueFlag = (x + n) == position.first && (y + m) == position.second
                }
            }
            if (continueFlag) {
                continue
            }

            board[position.first][position.second].bomb = true

            // デバッグ用 爆弾の位置を黒くする
            // board[position.first][position.second].background = Color.BLACK

            i += 1
            if (i == bombNum) {
                break
            }
        }
    }

    /**
     * 周りの爆弾を数える
     */
    private fun countAroundBomb() {
        var aroundBomb: Int

        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, element ->
                //  周りの爆弾の個数を数える
                aroundBomb = 0
                for (n in -1..1) {
                    for (m in -1..1) {
                        if (n == 0 && m == 0) {  // 自分自身のとき数えない
                            continue
                        }
                        val temp = board.getOrNull(i + n)?.getOrNull(j + m)

                        if (temp?.bomb == true) {
                            aroundBomb += 1
                        }

                    }
                }
                element.aroundBomb = aroundBomb
            }
        }
    }

    /**
     * マス目を空ける
     *
     * @param i 行
     * @param j 列
     * @param user ユーザーがクリックしたものかどうか(ユーザーがクリックしたときは爆弾でも空ける)
     * @return ゲームオーバーではないときtrue
     */
    fun openBoard(i: Int, j: Int, user: Boolean): Boolean {
        // 範囲外のとき
        if (board.getOrNull(i)?.getOrNull(j) == null) {
            return true
        }
        // 既に開けられているところを開けた場合
        if (!board[i][j].hiddenFlag) {
            return true
        }
        // 旗があるとき
        if (board[i][j].flag) {
            return true
        }
        // ユーザーが開けたとき
        if (user) {
            if (board[i][j].bomb) {
                board[i][j].openElement()
                return false
            }
        }

        if (!board[i][j].bomb) {
            board[i][j].openElement()

            if (board[i][j].aroundBomb == 0) {  // 周りに爆弾が1つもないとき周りを探索
                for (n in -1..1) {
                    for (m in -1..1) {
                        if (n == 0 && m == 0) {  // 自分自身のとき
                            continue
                        }
                        openBoard(i + n, j + m, false)
                    }
                }
            }
        }

        return true
    }

    /**
     * ボードをリセットする
     */
    fun resetBoard() {
        board.forEach { row ->
            row.forEach {
                it.resetElement()
            }
        }
        firstTime = true
    }

}