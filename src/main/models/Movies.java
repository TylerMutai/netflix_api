package main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "movies")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"},allowGetters = true)
public class Movies implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private Long categoryId;

    @NotBlank
    private String  type;

    @NotBlank
    private String name;

    private Long suggestedBy;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt; //Stores the date at which a user was created.

    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
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

        if(!Objects.isNull(categoryId)){
            builder.add("category_id",categoryId);
        }

        if(!Objects.isNull(createdAt)) {
            builder.add("created_at",createdAt.toString());
        }
        return builder.build().toString();
    }

    public Long getSuggestedBy() {
        return suggestedBy;
    }

    public void setSuggestedBy(Long suggestedBy) {
        this.suggestedBy = suggestedBy;
    }

    public enum MovieType{
        SUGGESTED("suggested"),ORIGINAL("original");

        private String movieType;

         MovieType(String movieType){
            this.movieType = movieType;
        }

        public String getMovieType() {
            return movieType;
        }

    }

}
