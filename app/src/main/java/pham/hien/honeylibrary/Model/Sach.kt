package pham.hien.honeylibrary.Model

import pham.hien.honeylibrary.Utils.Constant

class Sach {
    var maSach: Int = 0
    var tenSach: String = ""
    var maLoai: Int = -1
    var soLuong = 0
    var soLuongConLai = 0
    var giaThue = 0
    var giaSach = 0
    var gioiThieu: String? = ""
    var anhBia: String = Constant.SACH.IMAGE_BOOK_DEFAULT
    var thuHoi = false

    constructor()
    constructor(
        maSach: Int,
        tenSach: String,
        maLoai: Int,
        soLuong: Int,
        soLuongConLai: Int,
        giaThue: Int,
        giaSach: Int,
        gioiThieu: String?,
    ) {
        this.maSach = maSach
        this.tenSach = tenSach
        this.maLoai = maLoai
        this.soLuong = soLuong
        this.soLuongConLai = soLuongConLai
        this.giaThue = giaThue
        this.giaSach = giaSach
        this.gioiThieu = gioiThieu
    }


    override fun toString(): String {
        return "Sach(maSach=$maSach, tenSach='$tenSach', maLoai=$maLoai, soLuong=$soLuong, giaThue=$giaThue, giaSach=$giaSach, gioiThieu=$gioiThieu, anhBia='$anhBia')"
    }

}
