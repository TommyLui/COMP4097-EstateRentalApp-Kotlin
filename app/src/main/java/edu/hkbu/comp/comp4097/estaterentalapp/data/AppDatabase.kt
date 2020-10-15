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
            Log.d("DB",  "Check instance already build?")
            if (instance != null && doInitDB == false)
                return instance!!
            //build an instance
            Log.d("DB",  "Create instance")
            instance = Room.databaseBuilder(context, AppDatabase::class.java,
                "HousesInfo").build()
            Log.d("DB",  "Check database data")
            var housesInDao = instance?.housesDao()?.findAllHouses()
            Log.d("DB",  "DB data: ${housesInDao.toString()}")
            if (housesInDao != null) {
                if (housesInDao.isEmpty() || doInitDB){
                    if (housesInDao.isEmpty()){
                        Log.d("DB",  "initDB because of empty DB")
                    }else if(doInitDB){
                        Log.d("DB",  "doInitDB request true")
                    }
                    initDB()
                }
            }
            return instance!!
        }
        suspend fun initDB() {
            //instance?.clearAllTables() //add this line when you are still debugging
            instance?.housesDao()?.findAllHouses()?.forEach {instance?.housesDao()?.delete(it)}
            Log.d("DB",  "DB: Remove all data")
            var HOUSES = reloadData()
            Log.d("DB",  "DB: Reload data from data")
//            Log.d("DB",  HOUSES.toString())
            HOUSES.forEach {instance?.housesDao()?.insert(it)}
            Log.d("DB",  "DB: Reinsert all data")
        }
    }
}

private suspend fun reloadData(): List<Houses>{
    val HOUSES_URL = "https://morning-plains-00409.herokuapp.com/property/json"
    var houses = listOf<Houses>()
    var job = CoroutineScope(Dispatchers.IO).launch {
        try {
            Log.d("Network",  "Json: get house data")
            val json = Network.getTextFromNetwork(HOUSES_URL)
//                Log.d("Network",  json.toString())
            Log.d("Network",  "Gson: change house data")
            houses = Gson().fromJson<List<Houses>>(json,object :
                TypeToken<List<Houses>>() {}.type)
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