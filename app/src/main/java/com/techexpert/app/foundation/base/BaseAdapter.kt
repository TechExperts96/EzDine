package com.techexpert.app.foundation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.techexpert.app.BR
import kotlinx.coroutines.launch

abstract class BaseAdapter<T : Any, B : ViewDataBinding>(
    @LayoutRes val layoutId: Int,
    val vm: BaseViewModel? = null,
    private var items: List<T?>? = listOf()
) :
    RecyclerView.Adapter<BaseAdapter.GenericViewHolder<T, B>>(), OnClickListener<T> {

    private var inflater: LayoutInflater? = null
    private val _uiState = MutableLiveData<UIEvent>(UIEvent.Empty)
    val uiState: LiveData<UIEvent> = _uiState
    lateinit var binding: B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T, B> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding, this)
    }

    override fun onItemClick(item: T) {}

    override fun getItemCount(): Int = items?.size!!

    override fun onBindViewHolder(holder: GenericViewHolder<T, B>, position: Int) {
        items?.let {
            val item = it[position]
            holder.bind(item, vm, position)
        }
    }

    open fun setItems(items: List<T?>?) {
        items?.let {
            this.items = items
            notifyDataSetChanged()
        }
    }

    fun getItems(): List<T?>? {
        return items
    }

    fun addItems(newItems: List<T?>?) {
        newItems?.let {
            val temp = items?.toMutableList()
            temp?.addAll(newItems)
            this.items = temp
            notifyDataSetChanged()
        }
    }

    fun deleteItems(position: Int?) {
        position?.let {
            val temp = items?.toMutableList()
            temp?.removeAt(it)
            this.items = temp
            notifyDataSetChanged()
        }
    }

    val isEmptyList: Boolean
        get() {
            return items.isNullOrEmpty()
        }

    class GenericViewHolder<T : Any, B : ViewDataBinding>(
        val binding: B,
        var clickListener: OnClickListener<T>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T?, vm: BaseViewModel?) {
//            binding.setVariable(BR.item, item)
//            binding.setVariable(BR.clickListener, clickListener)
            vm?.let { binding.setVariable(BR.vm, vm) }
            binding.executePendingBindings()
        }

        fun bind(item: T?, vm: BaseViewModel?, position: Int) {
//            binding.setVariable(BR.item, item)
//            binding.setVariable(BR.clickListener, clickListener)
            vm?.let { binding.setVariable(BR.vm, vm) }
//            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }

    fun <T : UIEvent> publishUIEvent(event: T) {
        vm!!.viewModelScope.launch {
            _uiState.value = event
        }
    }
}

interface OnClickListener<T> {
    fun onItemClick(item: T)
}
