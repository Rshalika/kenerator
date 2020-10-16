package com.strawhats.kenerator.actions

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Caret
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.KtFile


class GenerateAction : ActionGroup() {

    override fun hideIfNoVisibleChildren(): Boolean {
        return false
    }

    override fun disableIfNoVisibleChildren(): Boolean {
        return false
    }

    override fun getChildren(event: AnActionEvent?): Array<AnAction> {
        if (event == null) {
            return AnAction.EMPTY_ARRAY
        }
        PlatformDataKeys.PROJECT.getData(event.dataContext) ?: return EMPTY_ARRAY

        val file: KtFile = (event.dataContext.getData(LangDataKeys.PSI_FILE) as? KtFile) ?: return EMPTY_ARRAY

        val caret: Caret? = event.dataContext.getData(LangDataKeys.CARET)
        val isProjectView = caret == null

        if (!isProjectView) {
            // EditorPopup menu
            val element = file.findElementAt(caret!!.offset)!!
            val parentOfType = PsiTreeUtil.getParentOfType(element, KtClassBody::class.java, false)
            parentOfType
                    ?: // not inside a class
                    return EMPTY_ARRAY
        }
        return arrayOf(getEqualsAction())

    }

    private fun getEqualsAction(): AnAction {
        val actionId = "kenerator.Menu.Action.Equals"
        var action = ActionManager.getInstance().getAction(actionId)
        if (action == null) {
            action = EqualsAction()
            ActionManager.getInstance().registerAction(actionId, action)
        }
        return action
    }
}