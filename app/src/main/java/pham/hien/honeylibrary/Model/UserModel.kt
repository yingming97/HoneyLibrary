package pham.hien.honeylibrary.Model

class UserModel {
    var firebaseId: String = ""
    var type = 0
    var avatar: String? = null
    var name: String = ""
    var email: String = ""
    var sdt: String = ""
    var diaChi: String? = null

    constructor()
    constructor(
        firebaseId: String,
        type: Int,
        name: String,
        email: String,
        avatar: String?,
        sdt: String,
        diaChi: String?
    ) {
        this.firebaseId = firebaseId
        this.type = type
        this.name = name
        this.email = email
        this.avatar = avatar
        this.sdt = sdt
        this.diaChi = diaChi
    }
}