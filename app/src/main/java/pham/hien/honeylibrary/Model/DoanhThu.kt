package pham.hien.honeylibrary.Model

class DoanhThu {
    var soTien = 0
    var time: Long = 0

    constructor()
    constructor(soTien: Int, time: Long) {
        this.soTien = soTien
        this.time = time
    }
}
