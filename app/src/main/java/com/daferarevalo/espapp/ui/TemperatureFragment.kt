package com.daferarevalo.espapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daferarevalo.espapp.R
import com.daferarevalo.espapp.databinding.FragmentTemperatureBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class TemperatureFragment : Fragment() {

    private lateinit var binding: FragmentTemperatureBinding

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


        val yValues = ArrayList<BarEntry>()

        yValues.add(BarEntry(8f, 0f))
        yValues.add(BarEntry(2f, 1f))
        yValues.add(BarEntry(5f, 2f))
        yValues.add(BarEntry(20f, 3f))
        yValues.add(BarEntry(15f, 4f))
        yValues.add(BarEntry(19f, 5f))

        val barDataSet = BarDataSet(yValues, "Cells")

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        val data = BarData(barDataSet)
        binding.barChart.data = data


    }
}