package pham.hien.honeylibrary.Model

import pham.hien.honeylibrary.Utils.Constant

class SachThue {
    var maSach: Int = 0
    var tenSach: String = ""
    var biaSach: String = Constant.SACH.IMAGE_BOOK_DEFAULT
    var soLuong = 0
    var giaThue = 0

    constructor()
    constructor(maSach: Int, tenSach: String, soLuong: Int, giaThue: Int) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.soLuong = soLuong
        this.giaThue = giaThue
    }


    override fun toString(): String {
        return "SachThue(maSach=$maSach, tenSach='$tenSach', biaSach='$biaSach', soLuong=$soLuong, giaThue=$giaThue)"
    }

}
