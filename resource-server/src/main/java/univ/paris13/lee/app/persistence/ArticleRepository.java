package univ.paris13.lee.app.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<MongoArticle, String> {
}
