package top.meethigher.demo03.domain;

import javax.persistence.*;

/**
 * Person
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/15
 */
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name="p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pId;
    @Column(name="person_name")
    private String personName;
    @Column(name="person_address")
    private String personAddress;

    //配置个人和单位的多对一关系
    @ManyToOne(targetEntity = Unit.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id",referencedColumnName = "unit_Id")
    private Unit unit;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Person{" +
                "pId=" + pId +
                ", personName='" + personName + '\'' +
                ", personAddress='" + personAddress + '\'' +
                '}';
    }
}
