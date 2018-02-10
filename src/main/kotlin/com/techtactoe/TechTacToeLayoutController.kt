package com.techtactoe

import com.jfoenix.controls.JFXButton
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

class TechTacToeLayoutController : Initializable {
    override fun initialize(location: URL?,
                            resources: ResourceBundle?) {
        assert(value = !::grid.isInitialized,
                lazyMessage = { "The grid has not been initialized" })
        grid.children.forEach {
            buttons[GridPane.getRowIndex(it) to GridPane.getColumnIndex(it)] = it as JFXButton
        }
    }

    @FXML
    private lateinit var grid: GridPane
    private val buttons: MutableMap<Pair<Int, Int>, JFXButton> = mutableMapOf()
    private var token: Token = Token.X
    private val combinations: List<Combination> = listOf(
            Combination(listOf(0 to 0,
                    1 to 1,
                    2 to 2)),
            Combination(listOf(0 to 0,
                    1 to 0,
                    2 to 0)),
            Combination(listOf(0 to 1,
                    1 to 1,
                    2 to 1)),
            Combination(listOf(0 to 2,
                    1 to 2,
                    2 to 2)),
            Combination(listOf(0 to 2,
                    1 to 1,
                    2 to 0)),
            Combination(listOf(0 to 0,
                    0 to 1,
                    0 to 2)),
            Combination(listOf(1 to 0,
                    1 to 1,
                    1 to 2)),
            Combination(listOf(2 to 0,
                    2 to 1,
                    2 to 2))
    )

    @FXML
    fun buttonOnAction(actionEvent: ActionEvent) {
        val source = actionEvent.source as JFXButton
        source.text = token.toString()
        tokenSwitch()
        victoryCheck()
    }

    private fun tokenSwitch() {
        token = if (token == Token.O) {
            Token.X
        } else {
            Token.O
        }
    }

    private fun victoryCheck() {
        var flag = false
        combinations.forEach {
            if (it.applies()) {
                flag = true
                tokenSwitch()
                Alert(Alert.AlertType.INFORMATION,
                        "Congratulations, $token won!").apply {
                    application()
                }
                return@forEach
            }
        }
    }

    fun Alert.application() {
        setOnCloseRequest {
            (grid.scene.window as Stage).close()
        }
        showAndWait()
    }

    inner class Combination(private var points: List<Pair<Int, Int>> = listOf()) {
        init {
            assert(value = points.size == 3)
        }

        fun applies(): Boolean {
            val text1 = points[0].button.text
            val text2 = points[1].button.text
            val text3 = points[2].button.text
            return ((text1 != "" && text2 != "" && text3 != "") && (text1 == text2 && text2 == text3))
        }
    }

    private val Pair<Int, Int>.button: Button
        get() = buttons[this]!!
}

