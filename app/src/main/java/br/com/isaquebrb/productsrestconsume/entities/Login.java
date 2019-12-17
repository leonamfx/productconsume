package br.com.isaquebrb.productsrestconsume.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login implements Serializable {

    @SerializedName("email")
    private String user;
    private String password;
}
