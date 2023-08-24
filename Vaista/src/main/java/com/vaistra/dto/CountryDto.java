package com.vaistra.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Data
@Table(name="country")
@SQLDelete(sql="UPDATE COUNTRY SET deleted=true WHERE No=?")
@Where(clause = "deleted=false")


public class CountryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Integer No;

    @NotEmpty(message = "Country Should not be Empty!")
    @NotBlank(message = "Country Should not be Blank!")
    @Size(min = 3, message = "Country name should be at least 3 characters!")



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

    public void setNo(Integer No)
    {
        this. No = No;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }



    public void setSTATUS(String STATUS) {
        this.STATUS=STATUS;

    }

    public CountryDto(Integer No, String COUNTRY, String STATUS) {
        this.No = No;
        this.COUNTRY = COUNTRY;
        this.STATUS = STATUS;
        this.deleted = deleted;
    }

    public CountryDto() {
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
