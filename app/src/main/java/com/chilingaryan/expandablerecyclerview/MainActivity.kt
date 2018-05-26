package com.chilingaryan.expandablerecyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chilingaryan.expandablerecyclerview.network.DummyResponse
import com.chilingaryan.expandablerecyclerview.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var dummyData = ArrayList<DummyData>()

    private lateinit var adapter: MyAdapter

    companion object {
        const val DEPTH_0 = 0
        const val DEPTH_1 = 1
        const val DEPTH_2 = 2
        const val DEPTH_3 = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareDummyData()

        adapter = MyAdapter(dummyData, recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun prepareDummyData() {
        RetrofitClient.getApiService().getDummyJson("http://json-schema.org/example/calendar.json").enqueue(object : Callback<DummyResponse?> {
            override fun onResponse(call: Call<DummyResponse?>?, response: Response<DummyResponse?>?) {
                response?.body()?.let { dummyResponse: DummyResponse ->
                    dummyData.add(DummyData(DEPTH_0,"\$schema", dummyResponse.schema ))

                    val required = DummyData(DEPTH_0, "required")
                    dummyResponse.required.forEach {
                        required.children.add(DummyData(DEPTH_1, it))
                    }

                    val properties = DummyData(DEPTH_0, "properties")
                    val dtstart = DummyData(DEPTH_1, "dtstart");
                    dtstart.children.add(DummyData(DEPTH_2, "format", dummyResponse.properties.dtstart.format))
                    dtstart.children.add(DummyData(DEPTH_2, "type", dummyResponse.properties.dtstart.type))
                    dtstart.children.add(DummyData(DEPTH_2, "description", dummyResponse.properties.dtstart.description))
                    properties.children.add(dtstart)

                    dummyData.add(required)
                    dummyData.add(properties)

                    adapter.notifyDataSetChanged()

                }
            }


            override fun onFailure(call: Call<DummyResponse?>?, t: Throwable?) {

            }

        })


    }

}
