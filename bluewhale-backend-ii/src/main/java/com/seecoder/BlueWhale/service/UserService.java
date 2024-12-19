package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.vo.ReceiveImfoVO;
import com.seecoder.BlueWhale.vo.UserVO;

import java.util.List;

public interface UserService {
    Boolean register(UserVO userVO) throws Exception;

    String login(String phone,String password) throws Exception;

    UserVO getInformation();

    Boolean updateInformation(UserVO userVO) throws Exception;

    List<ReceiveImfoVO> findReceiveImfoByUserId(Integer userId);

    Boolean addReceiveImfo(ReceiveImfoVO receiveImfoVO);

     Boolean deleteReceiveImfo(Integer receiveImfoId);
}
