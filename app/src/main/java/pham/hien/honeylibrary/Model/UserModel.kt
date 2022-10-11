package pham.hien.honeylibrary.Model

class UserModel {
    private var firebaseId: String? = null
    private var type = 0
    private var avatar: String? = null
    private var name: String? = null
    private var email: String? = null
    private var SDT: String? = null
    private var diaChi: String? = null

    constructor()
    constructor(
        firebaseId: String?,
        type: Int,
        name: String?,
        email: String?,
        avatar: String?,
        sdt: String?,
        diaChi: String?
    ) {
        this.firebaseId = firebaseId
        this.type = type
        this.name = name
        this.email = email
        this.avatar = avatar
        this.SDT = sdt
        this.diaChi = diaChi
    }
}