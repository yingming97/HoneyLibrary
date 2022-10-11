package pham.hien.honeylibrary.Model

class SachThue {
    var maSach: String = ""
    var tenSach: String = ""
    var soLuong = 0
    var giaThue = 0

    constructor(maSach: String, tenSach: String, soLuong: Int, giaThue: Int) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.soLuong = soLuong
        this.giaThue = giaThue
    }
}
