package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);
    User findByPhoneAndPassword(String phone, String password);

    Optional<User> findById(Integer id);

}
