package com.strawhats.kenerator.actions

class HashCodeTemplate : ActionTemplate {

    override val actionId: String
        get() = "com.strawhats.kenerator.actions.HashCodeAction"

    override val name: String
        get() = "Hashcode"
    override val description: String
        get() = "Generate hashcode method"


    override fun getFunctionDeclaration(fieldList: List<String>, className: String?): String {
        val fieldsText = fieldList.joinToString(" , \n")

        return """ override fun hashCode(): Int = Objects.hash(
            super.hashCode(),
            $fieldsText 
        ) """
    }
}