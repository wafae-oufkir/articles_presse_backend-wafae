package univ.paris13.lee.app.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersistenceTest {

    @Autowired
    // this repository is connected to an embedded mongodb instance thanks to the dependency de.flapdoodle.embed.mongo
    // this way we can do unit testing without depending of an actual DB instance
    private ArticleRepository repository;

    @Before
    public void init() {
        repository.deleteAll();
    }

    @Test
    public void test() {
        repository.findAll();
    }

    @Test(expected = DuplicateKeyException.class)
    public void unique() {
        repository.save(new MongoArticle().author("foo ").title("bar"));
        repository.save(new MongoArticle().author("foo ").title("bar"));
    }
}
