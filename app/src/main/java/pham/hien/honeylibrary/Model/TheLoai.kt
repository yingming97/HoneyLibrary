package pham.hien.honeylibrary.Model

import com.google.gson.annotations.SerializedName

class TheLoai {
    @SerializedName("maTheLoai")
    var maTheLoai: String = ""

    @SerializedName("tenTheLoai")
    var tenTheLoai: String = ""

    constructor()
    constructor(maTheLoai: String, tenTheLoai: String) {
        this.maTheLoai = maTheLoai
        this.tenTheLoai = tenTheLoai
    }
}
