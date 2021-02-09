    package com.daferarevalo.espapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentTemperatureBinding
import com.daferarevalo.espapp.server.Datos
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

    class TemperatureFragment : Fragment() {

        private lateinit var binding: FragmentTemperatureBinding

        private var temperatureList: MutableList<Datos> = mutableListOf()


        companion object {
            private val TAG = "TemperatureFragment"
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTemperatureBinding.bind(view)

        //binding.lineChart.onChartGestureListener
        //binding.lineChart.setOnChartValueSelectedListener(this@TemperatureFragment)

        cargarDesdeFirebase()

        val yValues = ArrayList<Entry>()

        yValues.add(Entry(0f, 60f))
        yValues.add(Entry(1f, 50f))
        yValues.add(Entry(2f, 70f))
        yValues.add(Entry(3f, 30f))
        yValues.add(Entry(4f, 50f))
        yValues.add(Entry(5f, 65f))

        val lineDataSet = LineDataSet(yValues, "Cells")

        lineDataSet.color = Color.RED
        lineDataSet.lineWidth = 3f
        lineDataSet.setCircleColor(Color.GREEN)

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        val data = LineData(lineDataSet)
        binding.barChart.animateX(2000)
        binding.barChart.data = data

        //binding.pruebaTextView.text = temperatureList.get(1).toString()

    }

        private fun cargarDesdeFirebase() {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val uidUser = user.uid
                val database = FirebaseDatabase.getInstance()
                val myDispRef = database.getReference("usuarios").child(uidUser)
                    .child("Sensores").child("TemperaturaReg")

                val postListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data: DataSnapshot in snapshot.children) {
                            val temp = data.getValue(Datos::class.java)
                            temp?.let { temperatureList.add(it) }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }
                myDispRef.addValueEventListener(postListener)
            }
        }

    }
