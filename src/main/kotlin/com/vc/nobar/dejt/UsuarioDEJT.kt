package com.vc.nobar.dejt

import com.poiji.annotation.ExcelCell
import com.vc.nobar.interfaces.ItemProcessamento

class UsuarioDEJT (@ExcelCell(0) var nome :String,
                   @ExcelCell(1) var cpf: String,
                   @ExcelCell(2) var email: String) : ItemProcessamento
{
    constructor() : this("","","")

    override fun getItem(): Any {
        return this;
    };
    override fun toString(): String = "$nome | $cpf | $email"
}

