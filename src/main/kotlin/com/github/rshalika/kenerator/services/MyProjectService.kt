package com.github.rshalika.kenerator.services

import com.intellij.openapi.project.Project
import com.github.rshalika.kenerator.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
