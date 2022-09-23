package com.arif.storedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arif.storedetails.adapter.StoreDataAdapter
import com.arif.storedetails.databinding.FragmentSdataBinding
import com.arif.storedetails.models.StoreInfo
import com.arif.storedetails.viewModel.StoreViewModel

class SdataFragment : Fragment() {
    private val storeViewModel: StoreViewModel by activityViewModels()
    private lateinit var binding:FragmentSdataBinding
    private var nextpage=1
    private val adapter = StoreDataAdapter()
    private val myList= mutableListOf<StoreInfo.Data>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSdataBinding.inflate(inflater,container,false)
        binding.storeRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.storeRV.adapter = adapter
        callApi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.setOnClickListener {
            callApi()
            binding.nextBtn.isActivated=false
        }
    }

    private fun callApi(){
        storeViewModel.fetchData(nextpage)
        storeViewModel.storeLiveData.observe(viewLifecycleOwner){
            if(it.data.isNotEmpty()){
                myList.addAll(it.data)
                adapter.submitList(myList)
                if (nextpage<=it.meta.lastPage){
                    nextpage=it.meta.currentPage+1
                    binding.nextBtn.isActivated=true
                }
            }

        }
    }

}