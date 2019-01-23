package univ.paris13.lee.app.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersistenceTest {

    @Autowired
    // this repository is connected to an embedded mongodb instance thanks to the dependency de.flapdoodle.embed.mongo
    // this way we can do unit testing without depending of an actual DB instance
    private ArticleRepository repository;

    @Test
    public void test() {
        repository.findAll();
    }
}
