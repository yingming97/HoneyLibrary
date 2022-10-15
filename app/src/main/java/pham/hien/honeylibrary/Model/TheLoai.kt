package pham.hien.honeylibrary.Model

class TheLoai {
    var maTheLoai: Int = 0
    var tenTheLoai: String = ""

    constructor()
    constructor(maTheLoai: Int, tenTheLoai: String) {
        this.maTheLoai = maTheLoai
        this.tenTheLoai = tenTheLoai
    }

    override fun toString(): String {
        return "TheLoai(maTheLoai=$maTheLoai, tenTheLoai='$tenTheLoai')"
    }
}
