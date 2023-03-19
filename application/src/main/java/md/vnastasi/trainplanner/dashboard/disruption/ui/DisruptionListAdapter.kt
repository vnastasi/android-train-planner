package md.vnastasi.trainplanner.dashboard.disruption.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import md.vnastasi.trainplanner.databinding.DisruptionItemDisturbBinding
import md.vnastasi.trainplanner.databinding.DisruptionItemHeaderBinding
import md.vnastasi.trainplanner.databinding.DisruptionItemMaintenanceBinding
import md.vnastasi.trainplanner.databinding.DisruptionItemNotificationBinding

class DisruptionListAdapter : ListAdapter<DisruptionListItem, DisruptionListAdapter.ItemViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is DisruptionListItem.Heading -> ItemViewHolder.Heading.VIEW_TYPE
            is DisruptionListItem.Notification -> ItemViewHolder.Notification.VIEW_TYPE
            is DisruptionListItem.Maintenance -> ItemViewHolder.Maintenance.VIEW_TYPE
            is DisruptionListItem.Disturbance -> ItemViewHolder.Disturbance.VIEW_TYPE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemViewHolder.Heading.VIEW_TYPE -> ItemViewHolder.Heading(DisruptionItemHeaderBinding.inflate(layoutInflater, parent, false))
            ItemViewHolder.Notification.VIEW_TYPE -> ItemViewHolder.Notification(DisruptionItemNotificationBinding.inflate(layoutInflater, parent, false))
            ItemViewHolder.Maintenance.VIEW_TYPE -> ItemViewHolder.Maintenance(DisruptionItemMaintenanceBinding.inflate(layoutInflater, parent, false))
            ItemViewHolder.Disturbance.VIEW_TYPE -> ItemViewHolder.Disturbance(DisruptionItemDisturbBinding.inflate(layoutInflater, parent, false))
            else -> error("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DisruptionListItem.Heading -> (holder as ItemViewHolder.Heading).bind(item)
            is DisruptionListItem.Notification -> (holder as ItemViewHolder.Notification).bind(item)
            is DisruptionListItem.Maintenance -> (holder as ItemViewHolder.Maintenance).bind(item)
            is DisruptionListItem.Disturbance -> (holder as ItemViewHolder.Disturbance).bind(item)
        }
    }

    sealed class ItemViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        class Heading(private val binding: DisruptionItemHeaderBinding) : ItemViewHolder(binding.root) {

            fun bind(item: DisruptionListItem.Heading) {
                binding.heading.setText(item.textResId)
            }

            companion object {

                const val VIEW_TYPE = 2345
            }
        }

        class Notification(private val binding: DisruptionItemNotificationBinding) : ItemViewHolder(binding.root) {

            fun bind(item: DisruptionListItem.Notification) {
                binding.title.text = item.value.title
            }

            companion object {

                const val VIEW_TYPE = 1325
            }
        }

        class Maintenance(private val binding: DisruptionItemMaintenanceBinding) : ItemViewHolder(binding.root) {

            fun bind(item: DisruptionListItem.Maintenance) {
                binding.title.text = item.value.title
            }

            companion object {

                const val VIEW_TYPE = 344
            }
        }

        class Disturbance(private val binding: DisruptionItemDisturbBinding) : ItemViewHolder(binding.root) {

            fun bind(item: DisruptionListItem.Disturbance) {
                binding.title.text = item.value.title
            }

            companion object {

                const val VIEW_TYPE = 3286
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<DisruptionListItem>() {

        override fun areItemsTheSame(oldItem: DisruptionListItem, newItem: DisruptionListItem): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: DisruptionListItem, newItem: DisruptionListItem): Boolean =
            oldItem == newItem
    }
}