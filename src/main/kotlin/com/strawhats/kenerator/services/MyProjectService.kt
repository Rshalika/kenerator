package com.strawhats.kenerator.services

import com.intellij.openapi.project.Project
import com.strawhats.kenerator.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
