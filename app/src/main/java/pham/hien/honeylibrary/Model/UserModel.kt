package pham.hien.honeylibrary.Model

import pham.hien.honeylibrary.Utils.Constant

class UserModel {
    var userId: Int = -1
    var firebaseId: String = ""
    var type = -1
    var avatar: String = Constant.USER.AVATAR_DEFAULT
    var name: String = ""
    var email: String = ""
    var sdt: String = ""
    var diaChi: String? = null

    constructor()

    constructor(
        userId: Int,
        firebaseId: String?,
        type: Int,
        name: String,
        email: String,
        sdt: String,
        diaChi: String?
    ) {
        this.userId = userId
        this.firebaseId = firebaseId!!
        this.type = type
        this.name = name
        this.email = email
        this.sdt = sdt
        this.diaChi = diaChi
    }

    override fun toString(): String {
        return "UserModel(userId=$userId, firebaseId='$firebaseId', type=$type, avatar='$avatar', name='$name', email='$email', sdt='$sdt', diaChi=$diaChi)"
    }

}