package com.strawhats.kenerator.actions

interface ActionTemplate {

    val name: String

    val description: String

    val actionId: String

    fun getFunctionDeclaration(fieldList: List<String>, className: String?): String
}