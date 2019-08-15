package main.controllers;

import main.models.Categories;
import main.models.Movies;
import main.repositories.CategoriesRepository;
import main.repositories.MoviesRepository;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api",produces = "application/json;charset=UTF-8") //All our api request URLs will start with /api and will return Json
public class MoviesController {

    private MoviesRepository moviesRepository;
    private CategoriesRepository categoriesRepository;
    private UserRepository userRepository;

    @Autowired
    public MoviesController(MoviesRepository moviesRepository, CategoriesRepository categoriesRepository, UserRepository userRepository){
        this.moviesRepository = moviesRepository;
        this.categoriesRepository = categoriesRepository;
        this.userRepository = userRepository;
    }

    //Suggest A movie
    @GetMapping(value = "/suggestMovie")
    public String suggestMovie(@RequestParam(name = "category_id") Long categoryId,@RequestParam(name = "name")String name
    ,@RequestParam(name = "suggested_by")Long suggestedBy){
        //Movies added through this API route are automatically marked as suggested.
        String movieType = Movies.MovieType.SUGGESTED.getMovieType();
        Movies movies = new Movies();

        //Provided category id should be in our categories table.
        if(categoriesRepository.findById(categoryId).isPresent()){

            if(userRepository.findById(suggestedBy).isPresent()){
                movies.setCategoryId(categoryId);
                movies.setName(name);
                movies.setType(movieType);
                movies.setSuggestedBy(suggestedBy);
                return moviesRepository.save(movies).toString();
            } else {
                return "{'error':'The specified user id does not exist.'}";
            }

        } else {
            return "{'error':'The specified category id does not exist.'}";
        }



    }

    //delete a suggested movie
    @GetMapping(value = "/deleteMovie")
    public String deleteMovie(@RequestParam(name = "movie_id") Long movieId,@RequestParam(name = "user_id")Long userId) {
        if(userRepository.findById(userId).isPresent()){
            Optional<Movies> movies = moviesRepository.findById(movieId);
            if(movies.isPresent()){
                List<Movies> movie = moviesRepository.findBySuggestedByEqualsAndIdEquals(userId,movieId);
                if(movie.size()>0){
                    moviesRepository.delete(movie.get(0));
                    return movie.toString();
                } else {
                    return generateErrorResponse("The user specified cannot delete this movie");
                }


            } else {
                return  generateErrorResponse("Specified movie id does not exist");
            }

        } else {
            return generateErrorResponse("Specified user id does not exist");
        }
    }

    //update a suggested movie. Supports only updating of the movie name or category.
    @GetMapping(value = "/updateMovie/{movie_id}")
    public String updateMovie(@PathVariable(name = "movie_id") Long movieId,@RequestParam(name = "user_id")Long userId,
                              @RequestParam(name = "movie_name",required = false)String movieName, @RequestParam(name = "movie_category",required = false) Long movieCategory) {
        List<Movies> movie = moviesRepository.findBySuggestedByEqualsAndIdEquals(userId,movieId);
        if(!(movie.size()>0)){
            return generateErrorResponse("The user specified cannot update this movie");
        }

        if(moviesRepository.findById(movieId).isPresent()){
            Movies movies = moviesRepository.findById(movieId).get();
            if(movieName != null && !movieName.isEmpty()){
                movies.setName(movieName);
            }
            if(movieCategory != null && categoriesRepository.findById(movieCategory).isPresent()){
                movies.setCategoryId(movieCategory);
            }

            return moviesRepository.save(movies).toString();
        } else {
            return generateErrorResponse("The specified movie id does not exist");
        }
    }

    //query available movies
    @GetMapping(value = "/queryMovies/{categoryId}")
    public String queryMovies(@PathVariable Long categoryId,@RequestParam(name = "type") String type){
        JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        JsonObjectBuilder temp = Json.createObjectBuilder();
        int count = 0;
        for(Movies movie:moviesRepository.findAllByCategoryIdEqualsAndTypeEquals(categoryId,type)){
            temp.add("id",movie.getId());
            temp.add("name",movie.getName());
            temp.add("type",movie.getType());
            temp.add("category_id",movie.getCategoryId());
            temp.add("created_at",movie.getCreatedAt().toString());
            jsonResponse.add(count + "",temp);
            temp = Json.createObjectBuilder();
            count++;
        }

        return jsonResponse.build().toString();
    }

    private String generateErrorResponse(String message){
        return "{\"error\":\"" + message + "\"";
    }

    //add categories
    @GetMapping(value = "/addCategories")
    public String addCategories(@RequestParam(name = "name") String name){
        Categories categories = new Categories();
        categories.setName(name);

        return categoriesRepository.save(categories).toString();
    }

}
