package pham.hien.honeylibrary.Model

class Message {
    var message: String = ""
    var email: String = ""

    constructor(){}

    constructor(message: String, email: String) {
        this.message = message
        this.email = email
    }
}

