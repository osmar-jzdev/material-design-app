package dgtic.unam.modulosiete

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AdapterViewHolder(private val context: Context, private val data: List<Data>) : RecyclerView.Adapter<AdapterViewHolder.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name:TextView
        var description:TextView
        var image:ImageView
        var contraint:ConstraintLayout
        init {
            name=view.findViewById(R.id.name_view)
            description=view.findViewById(R.id.description_view)
            image=view.findViewById(R.id.imageView)
            contraint=view.findViewById(R.id.data_constraint)
        }
    }

    //pasara el layout a viewholder para el pintado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li=LayoutInflater.from(parent.context)
        val v=li.inflate(R.layout.item_data,parent,false)
        return ViewHolder(v)
    }

    //este realizara el pinta de cada elemento
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info=data[position]
        holder.name.text=info.name
        holder.description.text=info.description
        holder.image.setImageResource(R.drawable.foto)
        holder.contraint.setOnClickListener{
            Toast.makeText(context,"Item" + data[position].name,Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}