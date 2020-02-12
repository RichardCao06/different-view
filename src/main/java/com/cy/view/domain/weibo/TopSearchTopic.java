package com.cy.view.domain.weibo;

import lombok.Data;

@Data
public class TopSearchTopic {

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
    private String readNum;

    /**
     * 主持人名称
     */
    private String questionMasterName;

    /**
     * 主持人url
     */
    private String questionMasterUrl;
}
