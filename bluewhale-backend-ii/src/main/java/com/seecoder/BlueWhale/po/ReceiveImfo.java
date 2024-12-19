package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.vo.ReceiveImfoVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ReceiveImfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "receiveImfo_id")
    Integer receiveImfoId;

    @Column(name = "user_id")
    Integer userId;

    @Column(name = "telephone")
    String telephone;

    @Column(name = "address")
    String address;
    public ReceiveImfoVO toVO(){
        ReceiveImfoVO receiveImfoVO=new ReceiveImfoVO();
        receiveImfoVO.setReceiveImfoId(this.receiveImfoId);
        receiveImfoVO.setUserId(this.userId);
        receiveImfoVO.setTelephone(this.telephone);
        receiveImfoVO.setAddress(this.address);
        return receiveImfoVO;
    }


}
