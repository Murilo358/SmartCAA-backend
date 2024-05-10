package com.smartCaa.smartCaa.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "LSCostsInKBRL")
public class LSCostsInKBRL { //This won't exist on final version (will be dynamic)

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String EligForCapi;
        private String year2022;
        private String year2023;
        private String year2024;
        private String year2025;
        private String year2026;
        private String year2027;
        private String year2028;
        private String year2029;
        private String year2030;
        private String year2031;
        private String year2032;
        private String year2033;
        private String year2034;
        private String year2035;
        private String year2036;
        private String Comments;
}
