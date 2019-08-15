package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClientApplication {

    //Simple client to consume our Netflix Server

    private static URL ROOT_DOMAIN;

    static {
        try {
            ROOT_DOMAIN = new URL("http://localhost:8080/api");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //Create a user
    private static void addUser() {
        String userId = "999";
        String userName = "netflix_users";
        HttpURLConnection con = null;
        try {
            URL url = new URL(ROOT_DOMAIN.toString() + "/addUser?id=" + userId + "&name=" + userName);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        //   addUser();
         // addMovie();
        //addCategory();
        // updateMovie();
        //deleteMovie();
        //queryMovies();
    }

    //Suggest a movie
    private static void addMovie() {
        String categoryId = "13";
        String name = "Jack and the Bean Stalk";
        String suggestedBy = "909";
        HttpURLConnection con = null;
        try {
            name = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
            URL url = new URL(ROOT_DOMAIN.toString() + "/suggestMovie?category_id=" + categoryId + "&name=" + name + "&suggested_by=" + suggestedBy);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //delete a suggested movie
    private static void deleteMovie() {
        String movieId = "10";
        String userId = "909";
        HttpURLConnection con = null;
        try {
            URL url = new URL(ROOT_DOMAIN.toString() + "/deleteMovie?movie_id=" + movieId + "&user_id=" + userId);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //update a movie
    private static void updateMovie() {
        String movieId = "8";
        String userId = "99";
        String movieName = "beauty and the beast";
        HttpURLConnection con = null;
        try {
            movieName = URLEncoder.encode(movieName, StandardCharsets.UTF_8.toString());
            URL url = new URL(ROOT_DOMAIN.toString() + "/updateMovie/" + movieId + "?user_id=" + userId +
                    "&movie_name=" + movieName);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Query a movie
    private static void queryMovies() {
        String categoryId = "5";
        String movieType = "suggested";
        HttpURLConnection con = null;
        try {
            URL url = new URL(ROOT_DOMAIN.toString() + "/queryMovies/" + categoryId + "?type=" + movieType);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Add a movie category
    private static void addCategory() {
        String categoryName = "romance";
        HttpURLConnection con = null;
        try {
            categoryName = URLEncoder.encode(categoryName, StandardCharsets.UTF_8.toString());
            URL url = new URL(ROOT_DOMAIN.toString() + "/addCategories?name=" + categoryName);
            System.out.println(url.toString());
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            printResponse(con);
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printResponse(HttpURLConnection connection) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
