package com.eop.baseservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends BaseEntity {

    @Column(name = "trx_number")
    private String trxNumber;

    @Column(name = "trx_date")
    private String trxDate;

    @Column(name = "trx_code")
    private Boolean trxCode;

}
