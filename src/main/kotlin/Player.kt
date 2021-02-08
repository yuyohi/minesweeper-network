import org.apache.http.client.utils.DateUtils.parseDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * プレイヤーの名前と時間を保持するクラス
 */
class Player {
    var userName = "unknown"

    var time = 0.0

    /**
     * 名前と日付, 時刻をユーザーネームとしてセットする
     *
     * @param name ユーザーネーム
     */
    fun setNameDate(name: String) {
        val dateAndTime = LocalDateTime.now()

        val format = "yyyy年MM月dd日 HH時mm分ss秒"
        val formatter = DateTimeFormatter.ofPattern(format)

        userName = name + "(" + dateAndTime.format(formatter) + ")"
    }
}