package edu.hkbu.comp.comp4097.estaterentalapp.data
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.hkbu.comp.comp4097.estaterentalapp.ui.Home.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Houses::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun housesDao() : HousesDao
    companion object {
        private var instance: AppDatabase? = null
        suspend fun getInstance(context: Context, doInitDB: Boolean = false) : AppDatabase {
            Log.d("DB",  "DB checkpoint 1")
            if (instance != null && doInitDB == false)
                return instance!!
            //build an instance
            Log.d("DB",  "DB checkpoint 2")
            instance = Room.databaseBuilder(context, AppDatabase::class.java,
                "HousesInfo").build()
            Log.d("DB",  "DB checkpoint 3")
            var housesInDao = instance?.housesDao()?.findAllHouses()
            Log.d("DB",  housesInDao.toString())
            if (housesInDao != null) {
                if (housesInDao.isEmpty() || doInitDB){
                    initDB()
                }
            }
            return instance!!
        }
        suspend fun initDB() {
            //instance?.clearAllTables() //add this line when you are still debugging
            Log.d("DB",  "DB checkpoint 4")
            instance?.housesDao()?.findAllHouses()?.forEach {instance?.housesDao()?.delete(it)}
            var HOUSES = reloadData()
            Log.d("DB",  "DB checkpoint 5")
//            Log.d("DB",  HOUSES.toString())
            HOUSES.forEach {instance?.housesDao()?.insert(it)}
            Log.d("DB",  "DB checkpoint 6")
        }
    }
}

private suspend fun reloadData(): List<Houses>{
    val HOUSES_URL = "https://morning-plains-00409.herokuapp.com/property/json"
    var houses = listOf<Houses>()
    var job = CoroutineScope(Dispatchers.IO).launch {
        try {
            val json = Network.getTextFromNetwork(HOUSES_URL)
            Log.d("Network",  "house checkpoint 4")
//                Log.d("Network",  json.toString())
            houses = Gson().fromJson<List<Houses>>(json,object :
                TypeToken<List<Houses>>() {}.type)
            Log.d("Network",  "house checkpoint 5")
//                Log.d("Network",  houses.toString())
        } catch (e: Exception) {
            Log.d("Network", "Error in loading data")
            houses =
                listOf(Houses("", "", "","Please check your network connection","Cannot fetch houses", "","","","","","",""))
            }
        }
        job.join()
        return houses
}