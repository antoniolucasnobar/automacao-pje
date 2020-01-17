package com.vc.nobar.pje

import com.poiji.annotation.ExcelCell
import com.vc.nobar.interfaces.ItemProcessamento

class Processo (@ExcelCell(0) var numero :String,
                @ExcelCell(1) var orgaoJulgador: String) : ItemProcessamento
{
    constructor() : this("","")

    override fun getItem(): Any {
        return this;
    };

    override fun toString(): String = "$numero | $orgaoJulgador"
}

