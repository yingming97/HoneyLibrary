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
    }

    object SACH {
        const val IMAGE_BOOK_DEFAULT =
            "https://firebasestorage.googleapis.com/v0/b/honeylibrary-6f5c5.appspot.com/o/TableSach%2F0?alt=media&token=3879a67c-11a6-474b-a4ed-917d3b124407"
        const val TB_NAME = "TableSach"
        const val COL_MA_SACH = "maSach"
        const val COL_SO_LUONG = "soLuong"
    }

    object SACHTHUE {
        const val TRA_THIEU = "TRA_THIEU"
        const val TRA_DU = "TRA_DU"
    }

    object SACHTRATHIEU {
        const val TB_NAME = "TableSachTraThieu"

    }

    object PHIEUMUON {
        const val TB_NAME = "TablePhieuMuon"
        const val COL_MA_PHIEU = "maPhieuMuon"
        const val COL_MA_DOC_GIA = "maDocGia"
        const val COL_PHOTO_DOC_GIA = "photoDocGia"
        const val COL_MA_NHAN_VIEN = "maNhanVien"
        const val COL_TEN_DOC_GIA = "tenDocGia"
        const val COL_SO_LUONG = "soLuong"
        const val COL_TONG_TIEN = "tongTien"
        const val COL_NGAY_THUE = "ngayThue"
        const val COL_HAN_TRA = "hanTra"
        const val COL_TRANG_THAI = "trangThai"
        const val COL_LIST_SACH_THUE = "listSachThue"
        const val PHIEUMUON = "PHIEUMUON"

        object TRANGTHAI {
            const val DANG_MUON = "Đang mượn"
            const val DA_TRA = "Đã trả"
            const val QUA_HAN = "Quá hạn"
        }
    }

    object USER {
        const val AVATAR_DEFAULT =
            "https://firebasestorage.googleapis.com/v0/b/honeylibrary-6f5c5.appspot.com/o/TableUser%2F0?alt=media&token=bb543483-6a66-4f1d-b79b-52fbb44cfd63"
        const val TB_NAME = "TableUser"
        const val COL_TYPE = "type"
    }

    object QUYEN {
        const val KHACH = -1
        const val DOC_GIA = 0
        const val THU_THU = 1
        const val ADMIN = 2
    }
}