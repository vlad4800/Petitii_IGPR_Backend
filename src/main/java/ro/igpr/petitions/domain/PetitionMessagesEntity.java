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
@ApiModel(value = "Petition Message Item",
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetitionMessagesEntity)) return false;
        if (!super.equals(o)) return false;

        PetitionMessagesEntity that = (PetitionMessagesEntity) o;

        if (getPetitionId() != null ? !getPetitionId().equals(that.getPetitionId()) : that.getPetitionId() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        return getMessage() != null ? getMessage().equals(that.getMessage()) : that.getMessage() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPetitionId() != null ? getPetitionId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PetitionMessagesEntity{" +
                "petitionId=" + petitionId +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
