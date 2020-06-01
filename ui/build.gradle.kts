import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask

plugins {
  id("com.github.node-gradle.node") version "2.2.4"
}

node {
  download = false
}

tasks.create<NpmTask>("check") {
  dependsOn(NpmInstallTask.NAME)
  setArgs(listOf("run", "lint"))
}

tasks.create<NpmTask>("test") {
  dependsOn(NpmInstallTask.NAME)
  setArgs(listOf("test"))
}

tasks.create<NpmTask>("build") {
  dependsOn(NpmInstallTask.NAME)
  setArgs(listOf("run", "build"))

  inputs.dir("src")
  outputs.dir("dist/ui")
}

tasks.create<Zip>("jar") {
  dependsOn(tasks["build"])
  archiveBaseName.set(project.name)
  archiveExtension.set("jar")
  destinationDirectory.set(file("dist"))
  from("dist/ui") {
    into("static")
  }
}

tasks.create<Delete>("clean") {
  delete("dist")
}

tasks.create("assemble") {
  dependsOn(tasks["jar"])
}

val ui by configurations.creating
configurations {
  create("default") {
    extendsFrom(ui)
  }
}

artifacts {
  add(ui.name, tasks["jar"])
}
