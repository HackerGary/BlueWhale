package com.seecoder.BlueWhale.service;

import com.seecoder.BlueWhale.po.Comment;
import com.seecoder.BlueWhale.vo.CommentVO;

import java.util.List;

public interface CommentService {

        Boolean CreateComment(CommentVO commentVO);

        List<CommentVO> getCommentByProductId(Integer productId);


}
