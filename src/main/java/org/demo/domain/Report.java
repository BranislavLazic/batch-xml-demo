package org.demo.domain;

import org.demo.adapter.JaxbBigDecimalAdapter;
import org.demo.adapter.JaxbDateAdapter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Date;


@XmlRootElement(name = "record")
@Entity
public class Report {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long refId;
    private String name;
    private int age;
    private Date dob;
    private BigDecimal income;


    @XmlAttribute(name = "refId")
    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    @XmlElement(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    @XmlElement
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @XmlJavaTypeAdapter(JaxbBigDecimalAdapter.class)
    @XmlElement
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Report{" +
                "refId=" + refId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob=" + dob +
                ", income=" + income +
                '}';
    }
}