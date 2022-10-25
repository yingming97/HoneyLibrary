package pham.hien.honeylibrary.View.Tab.Option.Activity.DocGia

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.yingming.honeylibrary.Dialog.FailDialog
import java.util.regex.Pattern

class DialogSuaDocGia(
    context: Context,
    user: UserModel,
    done: ((UserModel)->Unit)
) : Dialog(context),
    View.OnClickListener {

    private val mContext = context

    private lateinit var edNameDocGia: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDiaChi: EditText
    private lateinit var edSdt: EditText
    private lateinit var toolBar: RelativeLayout
    private lateinit var tvUpdateDocGia: TextView
    private lateinit var arrUser: List<UserModel>
    private var mUser = user
    private val mDone = done

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_sua_doc_gia)
        window!!.decorView.setBackgroundResource(R.color.transparent)
        window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window!!.attributes = wlp
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(true)
        initView()
    }

    private fun initView() {

        edNameDocGia = findViewById(R.id.ed_sua_name_doc_gia)
        edEmail = findViewById(R.id.ed_sua_email_doc_gia)
        edDiaChi = findViewById(R.id.ed_sua_dia_chi_doc_gia)
        edSdt = findViewById(R.id.ed_sua_sdt_doc_gia)
        tvUpdateDocGia = findViewById(R.id.tv_update_doc_gia)

        tvUpdateDocGia.setOnClickListener(this)
        edNameDocGia.setText(mUser.name)
        edEmail.setText(mUser.email)
        edDiaChi.setText(mUser.diaChi)
        edSdt.setText(mUser.sdt)

        UserDAO().getListUser {
            arrUser = it
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            tvUpdateDocGia-> {
                checkForm(
                    edNameDocGia.text.toString(),
                    edEmail.text.toString(),
                    edDiaChi.text.toString(),
                    edSdt.text.toString()
                ) {
                    if (it) {
                        mUser.name = edNameDocGia.text.toString()
                        mUser.email = edEmail.text.toString()
                        mUser.sdt = edSdt.text.toString()
                        mUser.diaChi = edDiaChi.text.toString()
                        UserDAO().updateDocGia(mContext, mUser)
                        mDone(mUser)
                        dismiss()
                    }
                }

            }
        }
    }

    private fun checkForm(
        hoten: String,
        email: String,
        diachi: String,
        sdt: String,
        callback: (Boolean) -> Unit,
    ) {
        val phonePattern = "(84|0[3|5|7|8|9])+([0-9]{8,9})\\b"
        var title = ""
        var haveError = false
        if (hoten.isNullOrEmpty()) {
            title += "\nTên không được bỏ trống"
            haveError = true
        } else if (email.isNullOrEmpty()) {
            title += "\nEmail không được trống"
            haveError = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            title += "\nEmail sai định dạng"
            haveError = true
        } else if (diachi.isNullOrEmpty()) {
            title += "\nĐịa chỉ không được bỏ trống"
            haveError = true
        } else if (diachi.length!! < 6) {
            title += "\nĐịa chỉ không dưới 6 ký tự"
            haveError = true
        } else if (sdt.isNullOrEmpty()) {
            for (user in arrUser) {
                if (user.sdt == sdt) {
                    title += "\n SDT đã tồn tại"
                    haveError = true
                    break
                }
            }
            title += "\nSố điện thoại không được bỏ trống"
            haveError = true

        } else if (!Pattern.compile(phonePattern).matcher(sdt).matches()) {
            title += "\nSai định dạng số điện thoại"
            haveError = true
        }

        if (haveError) {
            callback(false)
            FailDialog(context, "Thêm Thất Bại", title).show()
        } else {
            callback(true)
        }
    }
}