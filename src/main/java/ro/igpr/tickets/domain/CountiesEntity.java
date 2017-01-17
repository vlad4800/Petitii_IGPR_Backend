package ro.igpr.tickets.domain;

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
@Table(name = "counties")
@DynamicUpdate
@ApiModel(value = "Counties Item",
        description = "The list of counties",
        parent = BaseEntity.class
)
public class CountiesEntity extends BaseEntity {

    private String name;

    @Basic
    @Column(name = "`name`", nullable = false, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountiesEntity)) return false;
        if (!super.equals(o)) return false;

        CountiesEntity that = (CountiesEntity) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CountiesEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
