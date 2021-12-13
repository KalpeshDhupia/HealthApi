package com.example.healthapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapi.model.Healthid
import kotlinx.android.synthetic.main.item_layout.view.*

class HealthAdapter(private val healthIdList: List<Healthid>) :
    RecyclerView.Adapter<HealthAdapter.HealthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return HealthViewHolder(view)
    }

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        val model = healthIdList[position]
        holder.setData(model)
    }

    override fun getItemCount(): Int {
        return healthIdList.size
    }


    class HealthViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun setData(healthid: Healthid) {
            view.apply {
                tvName.text = healthid.firstName + " " + healthid.lastName
                tvMobileno.text = healthid.mobile
                tvHealthId.text = healthid.id
            }
        }
    }


}