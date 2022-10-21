package pham.hien.honeylibrary.View.Tab.Option.Activity

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import pham.hien.honeylibrary.FireBase.Auth.CreateNewAccount
import pham.hien.honeylibrary.FireBase.FireStore.UserDAO
import pham.hien.honeylibrary.Model.UserModel
import pham.hien.honeylibrary.R
import pham.hien.honeylibrary.Utils.Constant

class DialogSuaDocGia(
    context: Context,
    user: UserModel,
    done: (()->Unit)
) : Dialog(context),
    View.OnClickListener {

    private val mContext = context

    private lateinit var edNameDocGia: EditText
    private lateinit var edEmail: EditText
    private lateinit var edDiaChi: EditText
    private lateinit var edSdt: EditText
    private lateinit var toolBar: RelativeLayout
    private lateinit var tvUpdateDocGia: TextView
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

    }

    override fun onClick(v: View?) {
        when (v) {
            tvUpdateDocGia-> {
                mUser.name = edNameDocGia.text.toString()
                mUser.email = edEmail.text.toString()
                mUser.sdt = edSdt.text.toString()
                mUser.diaChi = edDiaChi.text.toString()
                UserDAO().updateDocGia(mContext,mUser)
                //CreateNewAccount().createNewUser(mContext,mUser)
                mDone()
                dismiss()
            }
        }
    }
}