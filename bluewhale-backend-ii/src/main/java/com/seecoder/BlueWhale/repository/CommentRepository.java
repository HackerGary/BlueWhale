package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.Comment;
import com.seecoder.BlueWhale.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findByProductId(Integer productId);

    Comment findByCommentId(Integer commentId);
}
