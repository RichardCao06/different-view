package com.cy.view.repository.weibo;

import com.cy.view.domain.weibo.TopSearchBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopSearchRepository  extends JpaRepository<TopSearchBlog, Long> {

    /**
     * 根据博文识别码查找博文
     * @param code
     * @return
     */
    public TopSearchBlog findByBlogCode(String code);
}
