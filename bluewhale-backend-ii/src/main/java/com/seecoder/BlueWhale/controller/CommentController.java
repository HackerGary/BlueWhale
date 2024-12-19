package com.seecoder.BlueWhale.controller;

import com.seecoder.BlueWhale.service.CommentService;
import com.seecoder.BlueWhale.serviceImpl.CommentServiceImpl;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
        @Autowired
        CommentService commentService;
        @PostMapping("/createComment")
        public ResultVO<Boolean> CreateComment(@RequestBody CommentVO commentVO)
        {
                return ResultVO.buildSuccess(commentService.CreateComment(commentVO));
        }

        @GetMapping("/getCommentByProductId")
        public ResultVO<List<CommentVO>> getCommentByProductId(@RequestParam("productId")Integer productId)
        {
                return ResultVO.buildSuccess(commentService.getCommentByProductId(productId));
        }
        

}
