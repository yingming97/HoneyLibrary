package pham.hien.honeylibrary.Model

class MessengerModel {

    var noiDung: String? = ""
    var uid: String = ""
    var time: Long = 0
    var avatar: String? = ""

    constructor(noiDung: String?, uid: String, time: Long, avatar: String?) {
        this.noiDung = noiDung
        this.uid = uid
        this.time = time
        this.avatar = avatar
    }
}