package com.vc.nobar.acao.dejt

import com.poiji.annotation.ExcelCell

class UsuarioDEJT (@ExcelCell(0) var nome :String,
                   @ExcelCell(1) var cpf: String,
                   @ExcelCell(2) var email: String)
{
    constructor() : this("","","");
    override fun toString(): String = "$nome | $cpf | $email"
}

