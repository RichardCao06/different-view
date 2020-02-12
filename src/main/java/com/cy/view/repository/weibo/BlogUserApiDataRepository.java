package com.cy.view.repository.weibo;

import com.cy.view.domain.weibo.api.BlogUserApiData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserApiDataRepository extends JpaRepository<BlogUserApiData, Long> {

    BlogUserApiData findByUserId(Long userId);
}
