package forest.zykov.speech_directory.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import forest.zykov.speech_directory.EditActivity
import forest.zykov.speech_directory.R

class Adapter(listMain: ArrayList<DbElem>, contextM: Context) : RecyclerView.Adapter<Adapter.MHolder>() {
    var listArray = listMain
    var context = contextM

    class MHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tvElem: TextView = itemView.findViewById(R.id.tv_elem)
        val context = contextV

        fun setData(item: DbElem) {
            var str = item.word
            str += " - "
            str += item.translation
            str += ". Level: " + item.level
            tvElem.text = str
            itemView.setOnClickListener {
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(IntentConstants.I_ID_KEY, item.id)
                    putExtra(IntentConstants.I_LEVEL_KEY, item.level)
                    putExtra(IntentConstants.I_WORD_KEY, item.word)
                    putExtra(IntentConstants.I_TRANSLATION_KEY, item.translation)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): MHolder { // Применяем шаблон для элементов
        val inflater = LayoutInflater.from(parent.context)
        return MHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun getItemCount(): Int { // Сколько элеменов будет в списке
        return listArray.size
    }

    override fun onBindViewHolder(holder: MHolder, position: Int) { // Поздкючает данные из массива к шаблону
        holder.setData(listArray.get(position))
    }

    fun updateAdapter(listItems:List<DbElem>) {
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged() // Сообщение адаптеру, чтобы он обновился
    }

    fun removeItem(pos: Int, dbManager: MyDbManager) {
        dbManager.removeItemFromDb(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }
}