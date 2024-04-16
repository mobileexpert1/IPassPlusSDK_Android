import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.ipassplus.R
import com.app.ipassplus.Utils.SharedPref
import com.app.ipassplus.ui.dashboard.fragment.DashboardFragment
import com.app.ipassplus.ui.dashboard.model.ScenariosItemModel

class ScenariosListAdapter(private val mList: ArrayList<ScenariosItemModel>, val context: Context) : RecyclerView.Adapter<ScenariosListAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    private lateinit var pref: SharedPref


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_scenarios, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemModel = mList[position]
        holder.bind(itemModel)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setOnClickListener(onClickListener: DashboardFragment) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onScenarioPickClick(position: Int, model: ScenariosItemModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        private val tvFullProcess: TextView = itemView.findViewById(R.id.tvFullProcess)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvdescreption)
        private val rlMain: RelativeLayout = itemView.findViewById(R.id.rlMain)

        fun bind(model: ScenariosItemModel) {
            ivImage.setImageResource(model.image)
            tvFullProcess.text = model.process
            tvDescription.text = model.description
            //pref = SharedPref(context)

            if (model.isSelected){
                rlMain.background = AppCompatResources.getDrawable(context, R.drawable.bg_purple)
                ivImage.setColorFilter(ContextCompat.getColor(context, R.color.white))
                tvFullProcess.setTextColor(context.getColor(R.color.white))
                tvDescription.setTextColor(context.getColor(R.color.lightgrey))
            }else{
                rlMain.background = AppCompatResources.getDrawable(context, R.drawable.bg_scenario)
                ivImage.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                tvFullProcess.setTextColor(context.getColor(R.color.black))
                tvDescription.setTextColor(context.getColor(R.color.black))
            }

            itemView.setOnClickListener {
                if (!model.isSelected){
                    model.isSelected = true
                    rlMain.background = context.getDrawable(R.color.purple)

                    for (position in 0 until mList.size) {
                        if (position == adapterPosition) {
                            mList[position].isSelected = true
                        } else {
                            mList[position].isSelected = false
                        }
                    }
                    notifyDataSetChanged()

                }else{
                    onClickListener?.onScenarioPickClick(adapterPosition, model)
                }
            }

           /* if (pref.getSelectedPosition() == adapterPosition) {
                rlMain.background = context.getDrawable(R.color.purple)
                ivImage.setColorFilter(ContextCompat.getColor(context, R.color.white))
                tvFullProcess.setTextColor(context.getColor(R.color.white))
                tvDescription.setTextColor(context.getColor(R.color.white))
            } else {
                rlMain.background = context.getDrawable(R.color.lightbg)
                ivImage.setColorFilter(ContextCompat.getColor(context, R.color.purple))
                tvFullProcess.setTextColor(context.getColor(R.color.black))
                tvDescription.setTextColor(context.getColor(R.color.black))
            }

            itemView.setOnClickListener {
                if (pref.getSelectedPosition() != adapterPosition) {
                    pref.saveSelectedPosition(adapterPosition)
                    notifyDataSetChanged()
                }

                val color = (rlMain.background as ColorDrawable).color
                val hexColor = String.format("#%06X", 0xFFFFFF and color)
                Log.d("ButtonColor", "Button color code: $hexColor")

                if (hexColor.equals("#F9FAFB")) {
                    mList.get(adapterPosition).isSelected = true
                    rlMain.background = context.getDrawable(R.color.purple)

                    for (position in 0 until mList.size) {
                        if (position == adapterPosition) {
                            mList[position].isSelected = true
                        } else {
                            mList[position].isSelected = false
                        }
                    }
                    notifyDataSetChanged()
                } else {
                    onClickListener?.onScenarioPickClick(adapterPosition, model)
                }
            }*/
        }
    }
}
