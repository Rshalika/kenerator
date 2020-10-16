package com.strawhats.kenerator.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Caret
import com.intellij.psi.PsiClass
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory

class EqualsAction : AnAction() {

    init {
        templatePresentation.description = "Equals action"
        templatePresentation.setText("Equals", false)
    }

    override fun actionPerformed(event: AnActionEvent) {


        val ktFile = (event.getData(LangDataKeys.PSI_FILE) as KtFile)
        ktFile.classes.forEach { clazz ->
            val fieldList = getFieldList(clazz).map { it.name }
            val funDecl = getFunctionDeclaration(fieldList, clazz.name)
            val function = KtPsiFactory(event.project).createFunction(funDecl)
            addFunctionToClass(ktFile, clazz, event, function)
        }

    }

    private fun addFunctionToClass(ktFile: KtFile, clazz: PsiClass, event: AnActionEvent, function: KtNamedFunction) {
        val navigationElement = clazz.navigationElement
        WriteCommandAction.runWriteCommandAction(event.project) {
            val caret: Caret? = event.dataContext.getData(LangDataKeys.CARET)
            caret?.let {
                ktFile.findElementAt(it.offset)?.let { elem ->
                    navigationElement.addAfter(function, elem.prevSibling)
                }
            }
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