import javax.swing.SwingUtilities

/**
 * Main関数
 */
fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        val frame = MinesweeperGame()
        frame.isVisible = true
    }
}