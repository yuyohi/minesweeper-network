import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.awt.Font
import javax.swing.JLabel
import javax.swing.JPanel


/**
 * ランキングパネルのクラス
 *
 * @property baseURL プレイ記録が保存されているデータベースのURL
 * @property title タイトル
 * @property rankingList 記録のリスト
 */
class Ranking : JPanel() {
    private val baseURL = "https://minesweeper-b5e78-default-rtdb.firebaseio.com/"

    private var service: PlayerDBService? = null

    private val title = JLabel("    Ranking    ")

    private val rankingList = mutableListOf<JLabel>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PlayerDBService::class.java)

        title.font = Font("Arial", Font.BOLD, 28)
    }

    /**
     * データベースに記録をアップロードする関数
     */
    fun put(player: Player) {
        val repos = service?.put(player.userName, player)
        val response = repos?.execute()

        if (response?.isSuccessful == true) {
            println("PUT Success (Sync)")
        } else {
            println("PUT Failed (Sync): " + response?.message())
        }
    }

    /**
     * データベースから記録一覧を読み込む
     */
    fun get(): Map<String, Player>? {
        val repos = service?.get()
        val response = repos?.execute()

        if (response?.isSuccessful == true) {
            println("GET Success (Sync)")
        } else {
            println("GET Failed (Sync): " + response?.message())
        }

        return response?.body()
    }

    /**
     * ランキングを画面に表示する
     */
    fun showRanking() {
        val logMap = get()

        // タイムの早い順にソートしたマップ
        val logMapSorted = logMap?.toSortedMap { k1, k2 ->
            if (logMap[k1]!!.time - logMap[k2]!!.time < 0) {
                -1
            } else {
                1
            }
        }

        // 前のランキングをクリア
        rankingList.clear()
        this.removeAll()

        var rank = 0 //順位
        run loop@{
            logMapSorted?.forEach { (k, v) ->
                rank += 1
                val text = rank.toString() + ". " + k + ":   " + v.time + "秒"
                rankingList.add(JLabel(text))

                if (rankingList.size == 10) {  // 10位まで表示
                    return@loop
                }
            }
        }

        this.add(title)

        rankingList.forEach { score ->
            score.font = Font("游ゴシック", Font.BOLD, 14)
            this.add(score)
        }
    }
}