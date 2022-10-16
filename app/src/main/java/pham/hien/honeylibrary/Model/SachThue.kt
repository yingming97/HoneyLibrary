package pham.hien.honeylibrary.Model

import pham.hien.honeylibrary.Utils.Constant

class SachThue {
    var maSach: Int = 0
    var tenSach: String = ""
    var biaSach: String = ""
    var soLuong = 0
    var giaThue = 0
    var giaSach = 0
    var traThaiTra: String = ""

    constructor()
    constructor(
        maSach: Int,
        tenSach: String,
        biaSach: String,
        soLuong: Int,
        giaThue: Int,
        giaSach: Int,
        traThaiTra: String,
    ) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.biaSach = biaSach
        this.soLuong = soLuong
        this.giaThue = giaThue
        this.giaSach = giaSach
        this.traThaiTra = traThaiTra
    }


    override fun toString(): String {
        return "SachThue(maSach=$maSach, tenSach='$tenSach', biaSach='$biaSach', soLuong=$soLuong, giaThue=$giaThue)"
    }

}
