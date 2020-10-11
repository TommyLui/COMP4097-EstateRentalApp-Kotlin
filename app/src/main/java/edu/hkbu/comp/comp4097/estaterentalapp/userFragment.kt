package edu.hkbu.comp.comp4097.estaterentalapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.layout_user_function.*
import kotlinx.android.synthetic.main.layout_user_function.view.*
import kotlin.math.log

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

    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView=inflater.inflate(R.layout.layout_user_function,container,false)

        mView.loginPageBtn.setOnClickListener {
//            Toast.makeText(activity, "You clicked me.", Toast.LENGTH_SHORT).show()
//            val intent = Intent(activity, loginActivity::class.java)
//            activity.startActivity(intent)
            val intent = Intent (getActivity(), loginActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        return mView
    }
}