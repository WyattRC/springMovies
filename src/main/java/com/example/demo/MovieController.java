package com.example.demo;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MovieController {

    public static List<Movie> findMovie(String route){
            RestTemplate template = new RestTemplate();
            ResultsPage nowPlaying = template.getForObject("https://api.themoviedb.org/3/movie/now_playing?api_key=e02372b05f7b71693db90ff6957a605c", ResultsPage.class);
             return nowPlaying.results;
    }

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/now-playing")
    public String nowPlaying(Model model){
        model.addAttribute("movies", findMovie("now-playing"));
        System.out.println(findMovie("now-playing"));
        return "now-playing";
    }



    @GetMapping("/medium-popular-long-name")
    public String list(Model model){


        model.addAttribute("movies", findMovie("medium-popular-long-name"));


        List<Movie> weirdSearch = findMovie("medium-popular-long-name");
        List<Movie> mediumPopularLongName = new ArrayList<>();
        for (Movie movie : weirdSearch){
            if (movie.getPopularity() >= 30 && movie.getPopularity() <= 80 && movie.getTitle().length() >= 10){
                mediumPopularLongName.add(movie);
            }
        }
        model.addAttribute("mediumPopularLongName", mediumPopularLongName);
        return "medium-popular-long-name";
    }


}
