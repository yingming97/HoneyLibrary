package pham.hien.honeylibrary.Model

class Sach {
    var maSach: String = ""
    var tenSach: String = ""
    var maLoai: Int = -1
    var soLuong = 0
    var giaThue = 0
    var gioiThieu: String? = ""

    constructor()
    constructor(
        maSach: String,
        tenSach: String,
        maLoai: Int,
        soLuong: Int,
        giaThue: Int,
        gioiThieu: String?
    ) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.maLoai = maLoai
        this.soLuong = soLuong
        this.giaThue = giaThue
        this.gioiThieu = gioiThieu
    }
}
