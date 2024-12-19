package com.seecoder.BlueWhale.vo;

import com.seecoder.BlueWhale.po.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentVO {


    private Integer commentId;
    private Integer toCommentId;
    private String text;
    private Date time;
    private Integer UserId;
    private Integer productId;
    private Integer score;
    private Integer orderId;
    private String userName;
    private String toUserName;
    public Comment toPO()
    {
        Comment comment=new Comment();
        comment.setToCommentId(this.toCommentId);
        comment.setCommentId(this.commentId);
        comment.setText(this.text);
        comment.setTime(this.time);
        comment.setUserId(this.UserId);
        comment.setProductId(this.productId);
        comment.setScore(this.score);
        comment.setOrderId(this.orderId);
        comment.setUserName(this.userName);
        comment.setToUserName(this.toUserName);
        return comment;
    }
}
