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
                    val description = DummyData(DEPTH_0)
                    description.text = dummyResponse.description

                    val required = DummyData(DEPTH_1, "required")

                    dummyResponse.required.forEach {
                        required.children.add(DummyData(DEPTH_2, it))
                    }

                    val properties = DummyData(DEPTH_1, "properties")

                    val dtstart = DummyData(DEPTH_2, "dtstart");
                    dtstart.children.add(DummyData(DEPTH_3, dummyResponse.properties.dtstart.description, dummyResponse.properties.dtstart.format))

                    val dtend = DummyData(DEPTH_2, "dtend");
                    dtend.children.add(DummyData(DEPTH_3, dummyResponse.properties.dtend.description, dummyResponse.properties.dtend.format))

                    val summary = DummyData(DEPTH_2, "summary");
                    summary.children.add(DummyData(DEPTH_3, dummyResponse.properties.summary.type))

                    val location = DummyData(DEPTH_2, "location");
                    location.children.add(DummyData(DEPTH_3, dummyResponse.properties.location.type))

                    properties.children.add(dtstart)
                    properties.children.add(dtend)
                    properties.children.add(summary)
                    properties.children.add(location)

                    description.children.add(required)
                    description.children.add(properties)

                    dummyData.add(description)

                    adapter.notifyDataSetChanged()

                }
            }


            override fun onFailure(call: Call<DummyResponse?>?, t: Throwable?) {

            }

        })


    }

}
