package ir.kindnesswall.data.model.user

data class User(
    var id: Long = 0,
    var name: String? = null,
    var phoneNumber: String? = null,
    var image: String? = null,
    var isCharity: Boolean? = false,
    var charityName: String? = null
)