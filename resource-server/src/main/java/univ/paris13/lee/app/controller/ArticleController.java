package univ.paris13.lee.app.controller;

import io.swagger.api.ArticlesApi;
import io.swagger.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import univ.paris13.lee.app.error.NotFoundException;
import univ.paris13.lee.app.persistence.ArticleRepository;
import univ.paris13.lee.app.persistence.MongoArticle;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArticleController implements ArticlesApi {

    @Autowired
    private ArticleRepository repository;

    @Override
    public ResponseEntity<Article> createArticle(@Valid Article article) {
        final MongoArticle saved = repository.save(new MongoArticle().author(article.getAuthor())
                .title(article.getTitle())
                .content(article.getContent()));
        return ResponseEntity.created(URI.create(saved.getId())).body(article);
    }

    @Override
    public ResponseEntity<Article> getArticle(String articleId) {
        final MongoArticle article = repository.findById(articleId).
                orElseThrow(() -> new NotFoundException(String.format("Cannot find article with id '%s'", articleId)));
        return ResponseEntity.ok(article.toArticle());
    }

    @Override
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> articles = repository.findAll().stream().map(MongoArticle::toArticle).collect(Collectors.toList());
        return ResponseEntity.ok(articles);
    }

    @Override
    public ResponseEntity<Article> deleteArticle(String articleId) {
        final MongoArticle article = repository.findById(articleId).
                orElseThrow(() -> new NotFoundException(String.format("Cannot find article with id '%s'", articleId)));
        repository.deleteById(articleId);
        return ResponseEntity.ok(article.toArticle());
    }
}
