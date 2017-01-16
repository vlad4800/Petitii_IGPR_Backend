package ro.igpr.petitions.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
@Entity
@Table(name = "petition_messages")
@DynamicUpdate
@ApiModel(value = "Petition Messages Item",
        description = "The list of petition messages",
        parent = BaseEntity.class
)
public class PetitionMessagesEntity extends BaseEntity {

    @JsonIgnore
    private Integer petitionId;

    private String name;
    private String subject;
    private String message;

    @Basic
    @Column(name = "`petition_id`", nullable = false, insertable = true, updatable = true)
    public Integer getPetitionId() {
        return petitionId;
    }

    public void setPetitionId(Integer petitionId) {
        this.petitionId = petitionId;
    }

    @Basic
    @Column(name = "`name`", nullable = true, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "`subject`", nullable = false, insertable = true, updatable = true, length = 255)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    @Column(name = "`message`", nullable = false, insertable = true, updatable = true)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
