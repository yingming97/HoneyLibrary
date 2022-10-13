package pham.hien.honeylibrary.Model

class Sach {
    var maSach: Int = 0
    var tenSach: String = ""
    var maLoai: Int = -1
    var soLuong = 0
    var giaThue = 0
    var giaSach = 0
    var gioiThieu: String? = ""
    var anhBia: String? = ""

    constructor()
    constructor(
        maSach: Int,
        tenSach: String,
        maLoai: Int,
        soLuong: Int,
        giaThue: Int,
        giaSach: Int,
        gioiThieu: String?,
        anhBia: String?
    ) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.maLoai = maLoai
        this.soLuong = soLuong
        this.giaThue = giaThue
        this.giaSach = giaSach
        this.gioiThieu = gioiThieu
        this.anhBia = anhBia
    }
}
