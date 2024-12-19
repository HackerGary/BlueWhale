package com.seecoder.BlueWhale.po;

import com.seecoder.BlueWhale.vo.CommentVO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`comment`",indexes = {@Index(columnList = "product_id")})
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "comment_id")
    private Integer commentId;

    @Basic
    @Column (name="to_comment_id")
    private Integer toCommentId;

    @Basic
    @Column (name="text")
    private String text;

    @Basic
    @Column (name="time")
    private Date time;

    @Basic
    @Column (name="user_id")
    private Integer UserId;

    @Basic
    @Column(name="product_id")
    private Integer productId;

    @Basic
    @Column(name="score")
    private Integer score;

    @Basic
    @Column(name="order_id")
    private Integer orderId;

    @Basic
    @Column(name="user_name")
    private String userName;

    @Basic
    @Column(name="to_user_name")
    private String toUserName;

    public CommentVO toVO(){
        CommentVO commentVO = new CommentVO();
        commentVO.setToCommentId(this.toCommentId);
        commentVO.setCommentId(this.commentId);
        commentVO.setText(this.text);
        commentVO.setTime(this.time);
        commentVO.setUserId(this.UserId);
        commentVO.setProductId(this.productId);
        commentVO.setScore(this.score);
        commentVO.setOrderId(this.orderId);
        commentVO.setUserName(this.userName);
        commentVO.setToUserName(this.toUserName);
        return commentVO;
    }

}
