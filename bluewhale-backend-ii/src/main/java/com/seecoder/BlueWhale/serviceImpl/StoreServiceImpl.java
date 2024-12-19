package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.Store;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.StoreRepository;
import com.seecoder.BlueWhale.service.StoreService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Override
    public Boolean createStore(StoreVO storeVO){
        if (storeRepository.findByStoreId(storeVO.getStoreId()) != null
            || storeRepository.findByName(storeVO.getName()) != null) {
            throw BlueWhaleException.storeAlreadyExists();
        }

        Store newStore = storeVO.toPO();
        newStore.setTimes(0);
        newStore.setScore(5.0);
        storeRepository.save(newStore);
        return true;
    }

    @Override
    public List<StoreVO> getStoreList(){
        List<Store> storePOList = storeRepository.findAll();
        List<StoreVO> storeVOList = new ArrayList<>();
        for(int i=0;i<storePOList.size();i++){
            storeVOList.add(storePOList.get(i).toVO());
        }
        return storeVOList;
    }

    @Override
    public StoreVO getStoreById(Integer storeId){
        StoreVO storeVO = storeRepository.findByStoreId(storeId).toVO();
        return storeVO;
    }


}
