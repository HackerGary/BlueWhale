package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.ReceiveImfo;
import com.seecoder.BlueWhale.po.Store;
import com.seecoder.BlueWhale.po.User;
import com.seecoder.BlueWhale.repository.ReceiveImfoRepository;
import com.seecoder.BlueWhale.repository.UserRepository;
import com.seecoder.BlueWhale.service.StoreService;
import com.seecoder.BlueWhale.service.UserService;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.util.TokenUtil;
import com.seecoder.BlueWhale.vo.ReceiveImfoVO;
import com.seecoder.BlueWhale.vo.StoreVO;
import com.seecoder.BlueWhale.vo.UserVO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @Author: GaoZhaolong
 * @Date: 14:46 2023/11/26
 *
 * 注册登录功能实现
*/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReceiveImfoRepository receiveImfoRepository;
    
    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    StoreService storeService;


    public static String decrypt(String str, String key) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str));
        return new String(doFinal);
    }



    @Override
    public Boolean register(UserVO userVO) throws Exception {
        User user = userRepository.findByPhone(userVO.getPhone());
        if (user != null) {
            throw BlueWhaleException.phoneAlreadyExists();
        }
        User newUser = userVO.toPO();
        newUser.setCreateTime(new Date());
        newUser.setPassword(decrypt(userVO.getPassword(),"hello603hello603"));
        userRepository.save(newUser);
        return true;
    }

    @Override
    public String login(String phone, String password) throws Exception {
        String really=decrypt(password,"hello603hello603");
        User user = userRepository.findByPhoneAndPassword(phone, really);
        if (user == null) {
            throw BlueWhaleException.phoneOrPasswordError();
        }
        return tokenUtil.getToken(user);
    }

    @Override
    public UserVO getInformation() {
        User user=securityUtil.getCurrentUser();
        UserVO userVO= user.toVO();
        if(userVO.getStoreId()!=null)
        {
            userVO.setStoreName(storeService.getStoreById(userVO.getStoreId()).getName());
        }
        return userVO;
    }

    @Override
    public Boolean updateInformation(UserVO userVO) throws Exception {
        User user=securityUtil.getCurrentUser();
        if (userVO.getPassword()!=null){
            user.setPassword(decrypt(userVO.getPassword(),"hello603hello603"));
        }
        if (userVO.getName()!=null){
            user.setName(userVO.getName());
        }
        if (userVO.getAddress()!=null){
            user.setAddress(userVO.getAddress());
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public List<ReceiveImfoVO> findReceiveImfoByUserId(Integer userId) {
        List<ReceiveImfo> ReceiveImfoList = receiveImfoRepository.findByUserId(userId);
        List<ReceiveImfoVO> ReceiveImfoVOList = new ArrayList<>();
        for(int i=0;i<ReceiveImfoList.size();i++){
            ReceiveImfoVOList.add(ReceiveImfoList.get(i).toVO());
        }
        return ReceiveImfoVOList;
    }

    @Override
    public Boolean addReceiveImfo(ReceiveImfoVO receiveImfoVO) {
        ReceiveImfo receiveImfo=receiveImfoVO.toPO();
        receiveImfoRepository.save(receiveImfo);
        return true;
    }

    @Override
    public Boolean deleteReceiveImfo(Integer receiveImfoId) {

        ReceiveImfo receiveImfo=receiveImfoRepository.findByReceiveImfoId(receiveImfoId);
        receiveImfoRepository.delete(receiveImfo);
        return true;
    }

}
