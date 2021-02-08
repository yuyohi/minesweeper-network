import java.awt.BorderLayout
import javax.swing.*


/**
 * ゲームを行うクラス
 *
 * @property gamePanel ゲーム画面
 * @property ranking ランキング画面
 * @property player プレイヤー
 */
class MinesweeperGame : JFrame() {

    /**
     * ゲームディスプレイ
     */
    private val gamePanel = GameDisplay(9, 10, this)

    /**
     * ランキング表示画面
     */
    private val ranking = Ranking()

    /**
     * プレイヤー
     */
    val player = Player()

    init {
        initialize()
    }

    /**
     * ゲームの初期化をする
     */
    private fun initialize() {

        title = "Minesweeper"
        setSize(460, 530)
        defaultCloseOperation = DISPOSE_ON_CLOSE

        showMenu()
    }

    /**
     * ボードをリセットする
     */
    fun reset() {
        gamePanel.gameBoard.resetBoard()
    }

    /**
     * メニューを表示する
     */
    private fun showMenu() {
        val nameField = JTextField("ユーザ名", 15)
        val promptText = JLabel("名前を入力して下さい.")
        val startButton = JButton("Start")

        val p = JPanel()

        startButton.addActionListener(StartButtonListener(this, nameField, player))

        p.add(nameField)
        p.add(promptText)
        p.add(startButton)

        contentPane.add(p, BorderLayout.NORTH)
        contentPane.add(ranking, BorderLayout.CENTER)

        ranking.showRanking()

        validate()
        repaint()
    }

    /**
     * ゲーム画面に遷移する
     */
    fun startGame() {
        contentPane.removeAll()

        contentPane.add(gamePanel, BorderLayout.CENTER)

        val setupPanel = JPanel()

        contentPane.add(setupPanel, BorderLayout.SOUTH)

        val resetButton = JButton("Reset")
        val menuButton = JButton("Menu")

        setupPanel.add(resetButton)
        setupPanel.add(menuButton)
        resetButton.addActionListener(ResetButtonListener(this))
        menuButton.addActionListener(MenuButtonListener(this))

        validate()
        repaint()

        gamePanel.startTime = System.currentTimeMillis().toDouble()
    }

    /**
     * メニュー画面に戻る
     */
    fun returnMenu(win: Boolean) {
        contentPane.removeAll()
        reset()

        // クラウドに書き込む
        if (win) {
            ranking.put(player)
        }

        showMenu()
    }


}