package be.nils.familyfinder.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "family")
public class Family {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Column(name = "additional_info")
    private String additionalInfo;

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "family_person",
            joinColumns = @JoinColumn(name = "family_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> familyMembers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<Person> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<Person> familyMembers) {
        this.familyMembers = familyMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Family family = (Family) o;
        return id == family.id &&
                Objects.equals(additionalInfo, family.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, additionalInfo);
    }

    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", additionalInfo='" + additionalInfo + '\'' +
                ", familyMembers=" + familyMembers +
                '}';
    }
}
