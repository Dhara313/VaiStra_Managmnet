package com.vaistra.entity;


import com.vaistra.dto.CountryDto;
import jakarta.persistence.*;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer  No;

    private String COUNTRY;



    private Boolean isActive=Boolean.TRUE;
    private String STATUS= String.valueOf(isActive);


    private boolean deleted = Boolean.FALSE;



    public Integer getNo() {

        return No;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }


    public String getSTATUS() {
        return STATUS;
    }

    public void setNo(Integer No) {
        this.No = No;
    }

    public void setCOUNTRY(String COUNTRY)
    {

        this.COUNTRY = COUNTRY;
    }



    public void setSTATUS(String STATUS)
    {
        this.STATUS=STATUS;

    }

    public Country(Integer No, String COUNTRY, String STATUS, boolean deleted) {
        this.No = No;
        this.COUNTRY = COUNTRY;
        this.STATUS = STATUS;
        this.deleted = deleted;
    }

    public Country() {
    }

    @Override
    public String toString() {
        return "Country{" +
                "No=" + No +
                ", COUNTRY='" + COUNTRY + '\'' +
                ", isActive=" + isActive +
                ", STATUS='" + STATUS + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}