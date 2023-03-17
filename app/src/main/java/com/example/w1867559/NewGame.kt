package com.example.w1867559

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random


class NewGame : AppCompatActivity() {

    private lateinit var userScore: TextView
    private lateinit var computerScore: TextView
    lateinit var throwbtn: Button
    lateinit var ScoreBtn: Button
    lateinit var UserImageList: MutableList<ImageView>
    lateinit var ComputerimageList: MutableList<ImageView>
    lateinit var selectedimageList: MutableList<ImageView>
    var userWin = 0 //no of times that the user has won
    var comWin = 0 //no of time that the computer has won
    var allUserTotal = 0
    lateinit var total: MutableList<Int>

//    lateinit var ClickedDices: MutableList<ImageView>

    var attempt = 0
    var localUserScore = 0

    //scores after score btn pressed
    var UScore: Int = 0
    var CScore: Int = 0
    var Count: Int = 0

    var UserScore: Int = 0
    var ComputerScore: Int = 0
    var TargetScore: Int = 0
    var optionalRethrow = 0
    private var throwCount = 0
    var userWon = 0
    var comWon = 0

    var c1 = 0
    var c2 = 0
    var c3 = 0
    var c4 = 0
    var c5 = 0
    var c6 = 0


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)

        val input = intent.getIntExtra("input", 0)
        val textView = findViewById<TextView>(R.id.text_8)
        TargetScore = input
        textView.text = "$input"
        textView.setText(textView.text)


        val Uwin: TextView = findViewById(R.id.UWin)
        userWin = intent.getIntExtra("userWin", 0)
        Uwin.setText(userWin.toString())
        comWin = intent.getIntExtra("computerWin", 0)
        val CWin: TextView = findViewById(R.id.CWin)
        CWin.setText(comWin.toString())
        println("shit")
        println(userWin)
        println(comWin)


        total = arrayListOf()

        val user1: ImageView = findViewById(R.id.i_1)
        val user2: ImageView = findViewById(R.id.i_2)
        val user3: ImageView = findViewById(R.id.i_3)
        val user4: ImageView = findViewById(R.id.i_4)
        val user5: ImageView = findViewById(R.id.i_5)
        UserImageList = mutableListOf()
        UserImageList.add(user1)
        UserImageList.add(user2)
        UserImageList.add(user3)
        UserImageList.add(user4)
        UserImageList.add(user5)

        val com1: ImageView = findViewById(R.id.i_6)
        val com2: ImageView = findViewById(R.id.i_7)
        val com3: ImageView = findViewById(R.id.i_8)
        val com4: ImageView = findViewById(R.id.i_9)
        val com5: ImageView = findViewById(R.id.i_10)
        ComputerimageList = mutableListOf()
        ComputerimageList.add(com1)
        ComputerimageList.add(com2)
        ComputerimageList.add(com3)
        ComputerimageList.add(com4)
        ComputerimageList.add(com5)
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        throwbtn = findViewById(R.id.rollbtn)


        selectedimageList = mutableListOf()
        if (savedInstanceState != null) {
            val afterUScore = savedInstanceState.getInt("UserScore", 0)
            userScore.setText("" + afterUScore.toString())

            val afterCScore = savedInstanceState.getInt("comScore", 0)

            computerScore.setText("" + afterCScore.toString())

        }



        throwbtn.setOnClickListener {
            localUserScore = 0
            if (optionalRethrow == 0) {
                Throw()
                diceClicked()
                optionalRethrow++
            } else {
                if (optionalRethrow == 1 || optionalRethrow == 2) {
                    reRoll()
                    selectedimageList.clear()
                    for (q in UserImageList) {
                        q.clearColorFilter()
                        val toast = Toast.makeText(
                            this,
                            "you have only one re roll available",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                    optionalRethrow++
                    if (optionalRethrow == 3) {
                        val toast =
                            Toast.makeText(this, "your scores are updated!", Toast.LENGTH_SHORT)
                        toast.show()
                        Score()
                        optionalRethrow = 0
                    }
                }
            }
        }

        ScoreBtn = findViewById(R.id.scorebtn)
        ScoreBtn.setOnClickListener {
            Score()
            attempt = 0
        }
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)


    }

    override fun onBackPressed() {
        // Navigate back to the initial screen of the application
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userWin", userWin)
        intent.putExtra("computerWin", comWin)
        println(userWin)
        println(comWin)
        startActivity(intent)
    }

    fun Score() {
        compReRoll()
        for ((index, num) in total.withIndex()) {
            if (index < 5) {
                UScore = UScore + num
            } else {
                CScore = CScore + num
            }
        }
//        println(total.size)
        total.clear()
        userScore = findViewById(R.id.UScore)
        computerScore = findViewById(R.id.CScore)
        userScore.setText("" + UScore)
        computerScore.setText("" + CScore)
//        Count++

        if (UScore > TargetScore || CScore > TargetScore) {
            if (UScore > CScore) {
                userWin++
                println(userWin)
                var Uwin: TextView = findViewById(R.id.UWin)
                Uwin.setText("" + userWin)
                showResultPopup("User won!",R.color.purple_500)
//                showRestartPopup("The game has finished. If you want to restart the game please press back button ",R.color.red)
            } else if (UScore == CScore) {
                UserScore = setImage(UserImageList)
                UScore += UserScore
                ComputerScore = setImage(ComputerimageList)
                CScore += ComputerScore

                userScore.setText("" + UScore)
                computerScore.setText("" + CScore)
                if (UScore > CScore) {
                    showResultPopup("User won!", R.color.purple_500)
                } else {
                    showResultPopup("Computer won!", R.color.red)
                }
            } else {
                comWin++
                var Cwin: TextView = findViewById(R.id.CWin)
                Cwin.setText("" + comWin)
                showResultPopup("Computer won!", R.color.purple_500)
                ScoreBtn.setOnClickListener {
//                    showRestartPopup("The game has finished. If you want to restart the game please press back button ",R.color.red)
                }

            }
        }
        throwCount = 0
        optionalRethrow = 0
        selectedimageList.clear()
    }


    fun Throw() {
        UserScore = setImage(UserImageList)
        ComputerScore = setImage(ComputerimageList)
//        CScore += ComputerScore
    }

    fun setImage(UserImageList: MutableList<ImageView>): Int {
        localUserScore = 0
        for (a in UserImageList) {
            var an = ((1..6).random())
            if (an == 1) {
                a.setImageResource(R.drawable.die_face_1)
                localUserScore += an
                total.add(an)
            } else if (an == 2) {
                a.setImageResource(R.drawable.die_face_2)
                localUserScore += an
                total.add(an)
            } else if (an == 3) {
                a.setImageResource(R.drawable.die_face_3)
                localUserScore += an
                total.add(an)
            } else if (an == 4) {
                a.setImageResource(R.drawable.die_face_4)
                localUserScore += an
                total.add(an)
            } else if (an == 5) {
                a.setImageResource(R.drawable.die_face_5)
                localUserScore += an
                total.add(an)
            } else if (an == 6) {
                a.setImageResource(R.drawable.die_face_6)
                localUserScore += an
                total.add(an)
            }
        }
        allUserTotal += localUserScore
        return localUserScore

    }

    fun reRoll(): Int {

        localUserScore = 0

        for ((index, a) in UserImageList.withIndex()) {
            if (a !in selectedimageList) {
                var an = ((1..6).random())
                if (an == 1) {
                    a.setImageResource(R.drawable.die_face_1)
                    localUserScore += an
                    total[index] = an
                } else if (an == 2) {
                    a.setImageResource(R.drawable.die_face_2)
                    localUserScore += an
                    total[index] = an
                } else if (an == 3) {
                    a.setImageResource(R.drawable.die_face_3)
                    localUserScore += an
                    total[index] = an
                } else if (an == 4) {
                    a.setImageResource(R.drawable.die_face_4)
                    localUserScore += an
                    total[index] = an
                } else if (an == 5) {
                    a.setImageResource(R.drawable.die_face_5)
                    localUserScore += an
                    total[index] = an
                } else if (an == 6) {
                    a.setImageResource(R.drawable.die_face_6)
                    localUserScore += an
                    total[index] = an
                }

            }
        }
        return localUserScore
    }


    fun showResultPopup(message: String, color: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)
        dialog.findViewById<TextView>(R.id.Winner).apply {
            text = message
            this.setBackgroundColor(color)
        }
        dialog.findViewById<Button>(R.id.okButton).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun showRestartPopup(message: String, color: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)
        dialog.findViewById<TextView>(R.id.Winner).apply {
            text = message
            this.setBackgroundColor(color)
        }
        dialog.findViewById<Button>(R.id.okButton).setOnClickListener {
            val inrr = Intent(this, MainActivity::class.java)
            startActivity(inrr)
        }
        dialog.show()

    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("localUserScore", localUserScore)
        outState.putInt("UserScore", UScore)
        outState.putInt("comScore", CScore)
        outState.putInt("throwCount", throwCount)

    }


    fun diceClicked() {
        for (i in UserImageList) {
            if (i !in selectedimageList) {
                i.setOnClickListener {
                    selectedimageList.add(i)
                    i.setColorFilter(R.color.purple_500)
                }
            }
        }

    }


    fun compReRoll() {
        var reRollCount = 0
        var cList = mutableListOf<Int>()
        var reRollBoolNum = Random.nextInt(0, 2)
        if (reRollBoolNum == 1) {
            cList.clear()
            var average = 0
            while (reRollCount < 2) {
                reRollCount++
                for ((index, a) in ComputerimageList.withIndex()) {
                    var reRellDice = Random.nextInt(0, 2)
                    if (reRellDice == 1) {
                        var an = ((1..6).random())
                        if (an == 1) {
                            a.setImageResource(R.drawable.die_face_1)
                            localUserScore += an
                            c1 = an
                            cList.add(c1)
                            if (c1 < average) {
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                                val toast = Toast.makeText(
                                    this,
                                    "1 value dice got re-rolled",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                            total[index + 5] = an
                        } else if (an == 2) {
                            a.setImageResource(R.drawable.die_face_2)
                            localUserScore += an
                            c2 = an
                            cList.add(c2)
                            if (c2 < average) {
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                            }
                            total[index + 5] = an
                            val toast = Toast.makeText(
                                this,
                                "value of 2 dice got re-rolled",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else if (an == 3) {
                            a.setImageResource(R.drawable.die_face_3)
                            localUserScore += an
                            c3 = an
                            cList.add(c3)
                            if (c3 < average) {
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                            }
                            total[index + 5] = an
                            val toast = Toast.makeText(
                                this,
                                "value of 3 dice got re-rolled",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else if (an == 4) {
                            a.setImageResource(R.drawable.die_face_4)
                            localUserScore += an
                            c4 = an
                            cList.add(c4)
                            total[index + 5] = an
                            if (c4 < average) {
                                cList.remove(c4)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                            }
                            total[index + 5] = an
                            val toast = Toast.makeText(
                                this,
                                "value of 4 dice got re-rolled",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else if (an == 5) {
                            a.setImageResource(R.drawable.die_face_5)
                            localUserScore += an
                            c5 = an
                            cList.add(c5)
                            total[index + 5] = an
                            if (c5 < average) {
                                cList.remove(c5)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                            }
                            total[index + 5] = an
                            val toast = Toast.makeText(
                                this,
                                "value of 5 dice got re-rolled",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        } else if (an == 6) {
                            a.setImageResource(R.drawable.die_face_6)
                            localUserScore += an
                            c6 = an
                            cList.add(c6)
                            total[index + 5] = an
                            if (c6 < average) {
                                cList.remove(c6)
                                for (x in cList) {
                                    if (x == 2) {
                                        a.setImageResource(R.drawable.die_face_2)
                                        total[index + 5] = 2
                                    } else if (x == 3) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        total[index + 5] = 3
                                    } else if (x == 4) {
                                        a.setImageResource(R.drawable.die_face_4)
                                        total[index + 5] = 4
                                    } else if (x == 5) {
                                        a.setImageResource(R.drawable.die_face_5)
                                        total[index + 5] = 5
                                    } else if (x == 6) {
                                        a.setImageResource(R.drawable.die_face_3)
                                        a.setColorFilter(R.color.red)
                                        total[index + 5] = 6
                                    }
                                    a.setColorFilter(R.color.red)
                                }
                            }
                            total[index + 5] = an
                            val toast = Toast.makeText(
                                this,
                                "value of 6 dice got re-rolled",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }

                        a.clearColorFilter()
                    }
                }
                average = localUserScore % 5

                println("average" + average)
            }
        }

    }


}