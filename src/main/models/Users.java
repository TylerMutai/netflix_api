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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt"},
        allowGetters = true)
public class Users implements Serializable {
    private static final long serialVersionUID = 2L;

    @Column(updatable = false)
    @Id
    private Long id;

    @NotBlank(message = "The field 'name' is mandatory.")
    private String name;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt; //Stores the date at which a user was created.

    @PrePersist
    public void prePersist(){
        createdAt = new Date();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

        if(!Objects.isNull(createdAt)) {
            builder.add("created_at",createdAt.toString());
        }
        return builder.build().toString();
    }


    public Date getCreatedAt() {
        return createdAt;
    }

}
