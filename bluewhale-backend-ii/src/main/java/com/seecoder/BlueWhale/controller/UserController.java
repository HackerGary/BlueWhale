package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.vo.ReceiveImfoVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import com.seecoder.BlueWhale.vo.StoreVO;
import com.seecoder.BlueWhale.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResultVO<Boolean> register(@RequestBody UserVO userVO) throws Exception {
        return ResultVO.buildSuccess(userService.register(userVO));
    }

    @PostMapping("/login")
    public ResultVO<String> login(@RequestParam("phone") String phone, @RequestParam("password") String password) throws Exception {
        return ResultVO.buildSuccess(userService.login(phone, password));
    }


    @GetMapping("/findReceiveImfoByUserId")
    public ResultVO<List<ReceiveImfoVO>> findReceiveImfoByUserId(@RequestParam("userId") Integer userId){
        return ResultVO.buildSuccess(userService.findReceiveImfoByUserId(userId));
    }

    @PostMapping("/addReceiveImfo")
    public ResultVO<Boolean> addReceiveImfo(@RequestBody ReceiveImfoVO receiveImfoVO){
        return ResultVO.buildSuccess(userService.addReceiveImfo(receiveImfoVO));
    }
    @GetMapping("/deleteReceiveImfo")
    public ResultVO<Boolean> deleteReceiveImfo(@RequestParam("receiveImfoId")Integer receiveImfoId){
        return ResultVO.buildSuccess(userService.deleteReceiveImfo(receiveImfoId));
    }
    @GetMapping
    public ResultVO<UserVO> getInformation(){
        return ResultVO.buildSuccess(userService.getInformation());
    }

    @PostMapping
    public ResultVO<Boolean> updateInformation(@RequestBody UserVO userVO) throws Exception {
        return ResultVO.buildSuccess(userService.updateInformation(userVO));
    }
}
