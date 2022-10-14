package pham.hien.honeylibrary.Utils

class Constant {

    object POSITION {
        const val VIEW_SACH = 0
        const val VIEW_PHIEU_MUON = 1
        const val VIEW_HOME = 2
        const val VIEW_HOA_DON = 3
        const val VIEW_OPTION = 4
    }

    object THELOAI {
        const val TB_NAME = "TableTheLoai"
        const val COL_MA = "maTheLoai"
        const val COL_TEN = "tenTheLoai"
        const val COL_VI_TRI = "viTri"
    }

    object SACH {
        const val IMAGE_BOOK_DEFAULT = "https://firebasestorage.googleapis.com/v0/b/honeylibrary-6f5c5.appspot.com/o/TableSach%2F0?alt=media&token=3879a67c-11a6-474b-a4ed-917d3b124407"
        const val TB_NAME = "TableSach"

    }

    object PHIEUMUON {
        const val TB_NAME = "TablePhieuMuon"
        var maPhieuMuon: String = ""
        var maDocGia: String = ""
        var maNhanVien: String = ""
        var soLuong = 0
        var tongTien = 0
        var ngayThue: Long = 0
        var hanTra: Long = 0
        var listSachThue: String = ""
    }

    object USER {
        const val AVATAR_DEFAULT=
            "https://firebasestorage.googleapis.com/v0/b/honeylibrary-6f5c5.appspot.com/o/TableUser%2F0?alt=media&token=bb543483-6a66-4f1d-b79b-52fbb44cfd63"
        const val TB_NAME = "TableUser"
        const val COL_TYPE = "type"
    }

    object QUYEN{
        const val DOC_GIA = 0
        const val THU_THU = 1
        const val ADMIN = 2
    }
}