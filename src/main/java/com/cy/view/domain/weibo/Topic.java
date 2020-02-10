package com.cy.view.domain.weibo;

import lombok.Data;

/**
 * @description: 微博话题信息实体
 * @author CaoYong
 * @date: 2020/2/10
 * @version 1.0
 */
@Data
public class Topic {

    /**
     * 主键
     */
    private Long id;

    /**
     * 话题链接
     */
    private String topicUrl;

    /**
     * 排名
     */
    private String rank;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String tag;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 阅读数
     */
    private Integer readNum;

    /**
     * 博主
     */
    private Blogger blogger;


}
