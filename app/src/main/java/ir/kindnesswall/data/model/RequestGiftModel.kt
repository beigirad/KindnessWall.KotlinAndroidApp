package ir.kindnesswall.data.model

import java.io.Serializable

class RequestGiftModel : Serializable {
    var userId: Long = 0
    var contactId: Long = 0
    var chatId: Long = 0
}