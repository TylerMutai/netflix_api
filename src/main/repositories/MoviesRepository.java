package main.repositories;

import main.models.Movies;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoviesRepository extends CrudRepository<Movies,Long> {

    List<Movies> findAllByCategoryIdEqualsAndTypeEquals(Long categoryId,String type);

    List<Movies> findBySuggestedByEqualsAndIdEquals(Long suggestedBy,Long movieId);
}
