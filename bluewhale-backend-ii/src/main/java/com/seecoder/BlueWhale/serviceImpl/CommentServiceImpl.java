package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.*;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.service.CommentService;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Boolean CreateComment(CommentVO commentVO) {
        if(commentVO.getToCommentId().intValue()==-1) {
            Order order = orderRepository.findByOrderId(commentVO.getOrderId());
            Product product = productRepository.getProductById(order.getProductId());
            Store store = storeRepository.findByStoreId(product.getStoreId());
            if (order.getOrderStatusEnum() != OrderStatusEnum.UNCOMMENT) {
                throw BlueWhaleException.ErrorStatus();
            }
            Comment newcomment = commentVO.toPO();
            User user = userRepository.findById(newcomment.getUserId()).get();
            newcomment.setUserName(user.getName());
            newcomment.setTime(new Date());
            commentRepository.save(newcomment);
            int score = commentVO.getScore();
            order.setOrderStatusEnum(OrderStatusEnum.DONE);
            orderRepository.save(order);
            int times = product.getTimes();
            double product_score = (product.getScore() * times + score) / (times + 1);
            String str1 = String.format("%.1f", product_score);
            product_score = Double.parseDouble(str1);
            product.setScore(product_score);
            product.setTimes(times + 1);
            productRepository.save(product);
            List<Product> productList = productRepository.findByStoreId(store.getStoreId());
            double sum = 0.0;
            for (int i = 0; i < productList.size(); i++) {
                sum += productList.get(i).getScore();
            }
            double newscore = sum / productList.size();
            String str2 = String.format("%.1f", newscore);
            newscore = Double.parseDouble(str2);
            store.setScore(newscore);
            store.setTimes(store.getTimes() + 1);
            storeRepository.save(store);
        }else{//是追评
            Comment newcomment = commentVO.toPO();
            User user = userRepository.findById(newcomment.getUserId()).get();
            newcomment.setUserName(user.getName());
            newcomment.setToUserName(commentRepository.findByCommentId(commentVO.getToCommentId()).getUserName());
            newcomment.setTime(new Date());
            commentRepository.save(newcomment);
        }
           return true;

    }

    @Override
    public List<CommentVO> getCommentByProductId(Integer productId) {
        List<Comment> commentList=commentRepository.findByProductId(productId);
        List<CommentVO> CommentVOList = new ArrayList<>();//评论列表
        List<CommentVO> toCommentVOList = new ArrayList<>();//追评列表
        for(int i=0;i<commentList.size();i++){
            if(commentList.get(i).getToCommentId()!=-1) {
                toCommentVOList.add(commentList.get(i).toVO());
            }else{
                CommentVOList.add(commentList.get(i).toVO());
            }
        }
        LinkedList<CommentVO> comments = new LinkedList<>(CommentVOList);
        while(!toCommentVOList.isEmpty()){
            CommentVO tempToComment = toCommentVOList.get(0);
            toCommentVOList.remove(0);
            int toCommentId = tempToComment.getToCommentId();
            for(int pos=0;pos<comments.size();pos++) {
                CommentVO tempComment = comments.get(pos);
                if (tempComment.getCommentId() == toCommentId) {
                    comments.add(pos + 1, tempToComment);
                    break;
                }
            }
        }
        List<CommentVO> commentVOList = new ArrayList<>(comments);
        return commentVOList;
    }
}
