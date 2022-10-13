package pham.hien.honeylibrary.Model

class MessengerModel {

    var noiDung: String? = ""
    var uid: String = ""
    var time: Long = 0
    var avatar: String? = ""

    constructor()
    constructor(noiDung: String?, uid: String, time: Long, avatar: String?) {
        this.noiDung = noiDung
        this.uid = uid
        this.time = time
        this.avatar = avatar
    }

    override fun toString(): String {
        return "MessengerModel(noiDung=$noiDung, uid='$uid', time=$time, avatar=$avatar)"
    }
}