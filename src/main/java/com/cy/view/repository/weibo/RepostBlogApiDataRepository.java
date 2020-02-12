package com.cy.view.repository.weibo;

import com.cy.view.domain.weibo.api.RepostBlogApiData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepostBlogApiDataRepository extends JpaRepository<RepostBlogApiData, Long> {

    RepostBlogApiData findByBlogId(String blogId);
}
