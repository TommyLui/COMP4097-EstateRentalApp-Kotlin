package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.layout_login_page.view.*
import kotlinx.android.synthetic.main.layout_user_function.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [userFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class userFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.layout_user_function, container, false)

        myView.loginPageBtn.setOnClickListener {
            val intent = Intent(getActivity(), loginActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        var account = sharedPreferences.getString("account", "")
        var password = sharedPreferences.getString("password", "")
        var loginState = sharedPreferences.getString("loginState", "")

        myView.debugbtn.setOnClickListener {
            Toast.makeText(getActivity(), loginState.toString(), Toast.LENGTH_SHORT).show()
        }

        return myView
    }

    override fun onResume(){
        super.onResume()

        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        var account = sharedPreferences.getString("account", "")
        var password = sharedPreferences.getString("password", "")
        var loginState = sharedPreferences.getString("loginState", "")

        if (loginState == "logout"){
            myView.userIcon.setImageResource(R.mipmap.man_foreground);
            myView.userName.setText("User Name")
        }
        else if (loginState == "login"){
            myView.userIcon.setImageResource(R.drawable.ic_baseline_perm_identity_24);
            myView.userName.setText("logined name")
        }
    }


}