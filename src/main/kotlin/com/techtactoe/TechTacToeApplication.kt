package com.techtactoe

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class TechTacToeApplication : Application() {
    override fun start(primaryStage: Stage?) {
        if (primaryStage != null) {
            val scene = Scene(FXMLLoader.load(javaClass.classLoader.getResource("Layout.fxml")))
            with(primaryStage) {
                this.scene = scene
                title = "TechTacToe"
                show()
            }
        } else {
            System.err.println()
        }
    }

}

fun main(args: Array<String>) {
    Application.launch(TechTacToeApplication::class.java,
            *args)
}

enum class Token {
    X,
    O
}

