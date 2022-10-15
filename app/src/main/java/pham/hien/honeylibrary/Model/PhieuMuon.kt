package pham.hien.honeylibrary.Model

import java.io.Serializable

class PhieuMuon : Serializable{
    var maPhieuMuon: String = ""
    var maDocGia: String = ""
    var photoDocGia: String = ""
    var tenDocGia: String = ""
    var maNhanVien: String = ""
    var soLuong = 0
    var tongTien = 0
    var ngayThue: Long = 0
    var hanTra: Long = 0
    var trangThai: String = ""
    var listSachThue: String = ""

    constructor()
    constructor(
        maPhieuMuon: String,
        maDocGia: String,
        photoDocGia: String,
        tenDocGia: String,
        maNhanVien: String,
        soLuong: Int,
        tongTien: Int,
        ngayThue: Long,
        hanTra: Long,
        trangThai: String,
        listSachThue: String,
    ) {
        this.maPhieuMuon = maPhieuMuon
        this.maDocGia = maDocGia
        this.photoDocGia = photoDocGia
        this.tenDocGia = tenDocGia
        this.maNhanVien = maNhanVien
        this.soLuong = soLuong
        this.tongTien = tongTien
        this.ngayThue = ngayThue
        this.hanTra = hanTra
        this.trangThai = trangThai
        this.listSachThue = listSachThue
    }


    override fun toString(): String {
        return "PhieuMuon(maPhieuMuon=$maPhieuMuon, maDocGia='$maDocGia', maNhanVien='$maNhanVien', soLuong=$soLuong, tongTien=$tongTien, ngayThue=$ngayThue, hanTra=$hanTra, trangThai=$trangThai, listSachThue='$listSachThue')"
    }

}
