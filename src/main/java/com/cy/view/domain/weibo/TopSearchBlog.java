package com.cy.view.domain.weibo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "weibo_topsearch_tb")
public class TopSearchBlog extends Blog {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 博主名称
     */
    private String bloggerName;

    /**
     * 博主个人空间地址
     */
    private String bloggerUrl;

    /**
     * 微博资源识别码
     */
    private String blogCode;

    /**
     * 博客内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 话题信息
     */
    //private List<Topic> topicList;

    /**
     * 发布时间
     */
    private Date postTime;

    /**
     * 发布源
     */
    private String origin;

    /**
     * 转发数
     */
    private Integer transmitNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 点赞数
     */
    private Integer likeNum;


    /**
     * 热搜关键字
     */
    private String keyword;


}
