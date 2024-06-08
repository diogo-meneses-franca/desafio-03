package com.pbcompass.apipropostas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensagemErroPadrao implements Serializable {

    private Date timestamp;
    private Integer status;
    private String message;
    private String path;
}
