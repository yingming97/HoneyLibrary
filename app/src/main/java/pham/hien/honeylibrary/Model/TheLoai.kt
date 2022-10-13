package pham.hien.honeylibrary.Model

class TheLoai {
    var maTheLoai: Int = 0
    var tenTheLoai: String = ""
    var viTri: String? = ""

    constructor()
    constructor(maTheLoai: Int, tenTheLoai: String, viTri: String) {
        this.maTheLoai = maTheLoai
        this.tenTheLoai = tenTheLoai
        this.viTri = viTri
    }
}
