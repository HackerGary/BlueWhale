package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.ReceiveImfo;
import com.seecoder.BlueWhale.po.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiveImfoRepository extends JpaRepository<ReceiveImfo, Integer> {

    List<ReceiveImfo> findByUserId(Integer userId);

    ReceiveImfo findByReceiveImfoId(Integer receiveImfoId);
}
