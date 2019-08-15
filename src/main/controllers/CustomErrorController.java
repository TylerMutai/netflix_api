package main.controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {


    @RequestMapping(value = "error",produces = "application/json;charset=UTF-8")
    public String renderErrorPage(HttpServletRequest request) {
        JsonObjectBuilder jsonResponse = Json.createObjectBuilder();
        String errorMsg = "";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int httpErrorCode = 404;
        if(status != null){
            httpErrorCode = Integer.valueOf(status.toString());
        }
        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
        }
        jsonResponse.add("error",errorMsg);
        return jsonResponse.build().toString();
    }

    @Override
    public String getErrorPath() {
        System.out.println("############################");
        return "/error";
    }
}