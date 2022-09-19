package pham.hien.honeylibrary.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserTest {
    @SerializedName("id")
    @Expose
    private var userId = -1

    @SerializedName("fi")
    @Expose
    private var firebaseId: String? = null

    @SerializedName("ty")
    @Expose
    private var type = 0

    @SerializedName("na")
    @Expose
    private var name: String? = null

    @SerializedName("em")
    @Expose
    private var email: String? = null

    @SerializedName("av")
    @Expose
    private var avatar: String? = null

    @SerializedName("dv")
    @Expose
    private var deviceId: String? = null

    @SerializedName("tk")
    @Expose
    private var tokenId: String? = null

    constructor()
    constructor(
        userId: Int,
        firebaseId: String?,
        type: Int,
        name: String?,
        email: String?,
        avatar: String?,
        deviceId: String?,
        tokenId: String?
    ) {
        this.userId = userId
        this.firebaseId = firebaseId
        this.type = type
        this.name = name
        this.email = email
        this.avatar = avatar
        this.deviceId = deviceId
        this.tokenId = tokenId
    }
}