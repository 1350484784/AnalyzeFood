package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.Article;
import com.cs.analyzefood.entity.vo.pageArticle.PageArticleCondition;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article newArticle);

    int selectArticleNum(PageArticleCondition pageArticleCondition);

    List<Article> selectPageArticle(PageArticleCondition pageArticleCondition);
}
