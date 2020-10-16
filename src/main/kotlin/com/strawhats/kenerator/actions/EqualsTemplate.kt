package com.strawhats.kenerator.actions

class EqualsTemplate : ActionTemplate {

    override val actionId: String
        get() = "com.strawhats.kenerator.actions.EqualsAction"

    override val name: String
        get() = "Equals"
    override val description: String
        get() = "Generate equals method"


    override fun getFunctionDeclaration(fieldList: List<String>, className: String?): String {
        val fieldsText = fieldList.joinToString(" \n && ") { "$it == other.${it}" }
        return """ override fun equals(other: Any?): Boolean = other is $className
                                && super.equals(other)
                                && $fieldsText """
    }
}