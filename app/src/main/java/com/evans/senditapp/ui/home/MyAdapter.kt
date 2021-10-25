package com.evans.senditapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evans.senditapp.R
import com.evans.senditapp.data.models.OrderResponse
import kotlinx.android.synthetic.main.fragment_orders.view.*
import kotlinx.android.synthetic.main.order_layout.view.*

class MyAdapter(val context: Context, var orders: List<OrderResponse>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var myList = emptyList<OrderResponse>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var orderId: TextView
        var vehicle: TextView
        var ownerId: TextView
        var description: TextView
        var location: TextView
        var destination: TextView
        var receiver: TextView
        var receiverNumber: TextView
        var weight: TextView
        var dateView: TextView


        init {
            orderId = itemView.order_id
            vehicle = itemView.vehicle
            ownerId = itemView.owner_id
            description = itemView.description
            location = itemView.pickup_location
            destination = itemView.destination
            receiver = itemView.reciever_name
            receiverNumber = itemView.reciever_number
            weight = itemView.weight
            dateView = itemView.dateView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.orderId.text ="Order ID: "+ orders[position].id.toString()
        holder.vehicle.text ="Vehicle: " + orders[position].vehicle
        holder.ownerId.text ="Owner ID: "+ orders[position].owner_id.toString()
        holder.description.text ="Decription: " + orders[position].description
        holder.location.text ="Pickup Location: " + orders[position].pickup_location
        holder.destination.text ="Destination : " + orders[position].destination
        holder.receiver.text ="Reciever name: "+ orders[position].reciever_name
        holder.receiverNumber.text ="Reciever number: "+ orders[position].reciever_number
        holder.weight.text ="Weight: "+ orders[position].weight
        holder.dateView.text ="Date: "+ orders[position].date
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun setData(newList: List<OrderResponse>) {
        orders = newList
        notifyDataSetChanged()
    }
}