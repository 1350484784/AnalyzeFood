package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.Article;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;
import com.github.pagehelper.PageInfo;

public interface ArticleService {
    int addNewArticle(Article newArticle);

    PageInfo<Article> getPageArticle(PageArticleCondition pageArticleCondition);
}
