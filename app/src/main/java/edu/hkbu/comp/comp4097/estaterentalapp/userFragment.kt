package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user.view.*


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

    lateinit var userView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userView = inflater.inflate(R.layout.fragment_user, container, false)

        return userView
    }

    override fun onResume(){
        super.onResume()

        val sharedPreferences = this.requireActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE)
        var loginState = sharedPreferences.getString("loginState", "")
        var userIcon = sharedPreferences.getString("userIcon", "")
        var username = sharedPreferences.getString("username", "")

        userView.loginPageBtn.setOnClickListener {
            if (loginState == "logout") {
                it.findNavController().navigate(
                    R.id.action_userFragment_to_loginFragment)
            }else{
                it.findNavController().navigate(
                    R.id.action_userFragment_to_logoutFragment)
            }
        }

        userView.debugbtn.setOnClickListener {
            Toast.makeText(getActivity(), loginState.toString(), Toast.LENGTH_SHORT).show()
        }

        Log.d("Network", "State: ${loginState}")

        if (loginState == "logout"){
            userView.userIcon.setImageResource(R.mipmap.man_foreground);
            userView.userName.setText("User Name")
        }
        else if (loginState == "login"){
            Picasso.get().load(userIcon).into(userView.userIcon)
            userView.userName.setText(username)
        }
    }


}