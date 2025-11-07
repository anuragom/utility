package com.omnivers.utility_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OPS_CN_M")
@Data
public class CNReport {
    @Id
    private String cnNo;
}

