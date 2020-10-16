package com.strawhats.kenerator.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.PsiClass
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory

class EqualsAction : AnAction() {

    init {
        templatePresentation.description = "Equals action"
        templatePresentation.setText("Equals", false)
    }

    override fun actionPerformed(event: AnActionEvent) {

        try {
            val ktFile = (event.getData(LangDataKeys.PSI_FILE) as KtFile)
            ktFile.classes.forEach { clazz ->
                val fieldList = getFieldList(clazz).map { it.name }
                val funDecl = getFunctionDeclaration(fieldList, clazz.name)
                val function = KtPsiFactory(event.project).createFunction(funDecl)
                addFunctionToClass(clazz, event, function)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun addFunctionToClass(clazz: PsiClass, e: AnActionEvent, function: KtNamedFunction) {
        val navigationElement = clazz.navigationElement
        WriteCommandAction.runWriteCommandAction(e.project) {
            val ktClassBody = navigationElement.children[0] as KtClassBody
            ktClassBody.addAfter(function, ktClassBody.children[ktClassBody.children.size - 1])
        }
    }

    private fun getFieldList(clazz: PsiClass) =
            clazz.fields.filter { it.modifierList?.hasExplicitModifier("static") == false }

    private fun getFunctionDeclaration(fieldList: List<String>, className: String?): String {
        val fieldsText = fieldList.joinToString(" && \n") { "this.${it} == other.${it}" }
        return """ override fun equals(other: Any?): Boolean = other is $className &&
                                super.equals(other) &&   
                                $fieldsText """
    }
}