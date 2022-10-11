package pham.hien.honeylibrary.Model

class PhieuMuon {
    var maPhieuMuon: String = ""
    var maDocGia: String = ""
    var maNhanVien: String = ""
    var soLuong = 0
    var tongTien = 0
    var ngayThue: Long = 0
    var hanTra: Long = 0
    var listSachThue: String = ""

    constructor(
        maPhieuMuon: String,
        maDocGia: String,
        maNhanVien: String,
        soLuong: Int,
        tongTien: Int,
        ngayThue: Long,
        hanTra: Long,
        listSachThue: String
    ) {
        this.maPhieuMuon = maPhieuMuon
        this.maDocGia = maDocGia
        this.maNhanVien = maNhanVien
        this.soLuong = soLuong
        this.tongTien = tongTien
        this.ngayThue = ngayThue
        this.hanTra = hanTra
        this.listSachThue = listSachThue
    }
}
