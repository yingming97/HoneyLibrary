package pham.hien.honeylibrary.Model

class UserModel {
    var userId: Int = -1
    var firebaseId: String = ""
    var type = 0
    var avatar: String? = null
    var name: String = ""
    var email: String = ""
    var sdt: String = ""
    var diaChi: String? = null

    constructor()
    constructor(
        userId: Int,
        firebaseId: String,
        type: Int,
        avatar: String?,
        name: String,
        email: String,
        sdt: String,
        diaChi: String?
    ) {
        this.userId = userId
        this.firebaseId = firebaseId
        this.type = type
        this.avatar = avatar
        this.name = name
        this.email = email
        this.sdt = sdt
        this.diaChi = diaChi
    }
}