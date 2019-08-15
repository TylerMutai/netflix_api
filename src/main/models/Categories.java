package main.models;



import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        //serialize to Json only if the data was persisted.
        if(!Objects.isNull(id)){
            builder.add("id",id);
        }
        if(!Objects.isNull(name)){
            builder.add("name",name);
        }

        return builder.build().toString();
    }


}
