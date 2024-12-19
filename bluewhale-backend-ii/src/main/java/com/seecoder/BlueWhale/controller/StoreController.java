package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.StoreService;
import com.seecoder.BlueWhale.vo.ResultVO;
import com.seecoder.BlueWhale.vo.StoreVO;
import com.seecoder.BlueWhale.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @PostMapping("/createStore")
    public ResultVO<Boolean> createStore(@RequestBody StoreVO storeVO){
        return ResultVO.buildSuccess(storeService.createStore(storeVO));
    }

    @GetMapping
    public ResultVO<List<StoreVO>> getStoreList(){
        return ResultVO.buildSuccess(storeService.getStoreList());
    }

    @GetMapping("/getStore")
    public ResultVO<StoreVO> getStoreById(@RequestParam("storeId") Integer storeId){
        return ResultVO.buildSuccess(storeService.getStoreById(storeId));
    }

}
