package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.view.*
import java.net.URL


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
        myView = inflater.inflate(R.layout.fragment_user, container, false)

        return myView
    }

    override fun onResume(){
        super.onResume()

        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        var loginState = sharedPreferences.getString("loginState", "")
        var userIcon = sharedPreferences.getString("userIcon", "")
        var username = sharedPreferences.getString("username", "")

        myView.loginPageBtn.setOnClickListener {
            if (loginState == "logout") {
                val intent = Intent(getActivity(), loginActivity::class.java)
                getActivity()?.startActivity(intent)
            }else{
                val intent = Intent(getActivity(), logoutActivity::class.java)
                getActivity()?.startActivity(intent)
            }
        }

        myView.debugbtn.setOnClickListener {
            Toast.makeText(getActivity(), loginState.toString(), Toast.LENGTH_SHORT).show()
        }

        Log.d("Network", "State: ${loginState}")

        if (loginState == "logout"){
            myView.userIcon.setImageResource(R.mipmap.man_foreground);
            myView.userName.setText("User Name")
        }
        else if (loginState == "login"){
            Picasso.get().load(userIcon).into(myView.userIcon)
            myView.userName.setText(username)
        }
    }


}