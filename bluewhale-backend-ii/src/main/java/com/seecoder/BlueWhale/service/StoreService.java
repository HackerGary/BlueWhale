package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.StoreVO;
import com.seecoder.BlueWhale.vo.UserVO;

import java.util.List;

public interface StoreService {

    Boolean createStore(StoreVO storeVO);

    List<StoreVO> getStoreList();

    StoreVO getStoreById(Integer storeId);

//    Boolean updateInformation(StoreVO storeVO);
}
