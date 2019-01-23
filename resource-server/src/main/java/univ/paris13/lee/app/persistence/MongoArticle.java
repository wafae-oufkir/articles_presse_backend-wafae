package univ.paris13.lee.app.persistence;

import io.swagger.model.Article;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "article")
public class MongoArticle {
    @Id
    private String id = null;
    private String author = null;
    private String title = null;
    private String content = null;

    public MongoArticle id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public MongoArticle author(String author) {
        this.author = author;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public MongoArticle title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MongoArticle content(String content) {
        this.content = content;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Article toArticle() {
        return new Article().id(id).author(author).title(title).content(content);
    }

}
