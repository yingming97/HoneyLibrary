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
        const val TB_NAME = "TableUser"
    }
}