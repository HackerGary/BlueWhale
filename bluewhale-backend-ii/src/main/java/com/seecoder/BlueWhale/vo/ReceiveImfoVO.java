package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.ReceiveImfo;
import com.seecoder.BlueWhale.po.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@NoArgsConstructor
public class ReceiveImfoVO {
    Integer receiveImfoId;
    Integer userId;
    String telephone;
    String address;
    public ReceiveImfo toPO(){
    ReceiveImfo receiveImfo=new ReceiveImfo();
    receiveImfo.setReceiveImfoId(this.receiveImfoId);
    receiveImfo.setUserId(this.userId);
    receiveImfo.setTelephone(this.telephone);
    receiveImfo.setAddress(this.address);
    return receiveImfo;
    }
}
