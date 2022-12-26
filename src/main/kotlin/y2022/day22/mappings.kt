package y2022.day22

import common.Coordinate

fun dirSwitches(sideSize: Int) = when(sideSize) {
    4 -> dirSwitchesSmall
    100 -> dirSwitches
    else -> throw IllegalArgumentException("$sideSize")
}

fun cubePassthroughs(sideSize: Int) = when(sideSize) {
    4 -> cubePassthroughsSmall
    100 -> cubePassthroughs
    else -> throw IllegalArgumentException("$sideSize")
}

private val cubePassthroughsSmall: Map<Coordinate, Coordinate> =
    (
            (5..8).row(5).zip((1..4).col(9)).andReverse() +
                    (1..4).row(5).zip((12 downTo 9).row(1)).andReverse() +
                    (5..8).row(8).zip((12 downTo 9).col(9)).andReverse() +
                    (13..16).row(9).zip((8 downTo 5).col(12)).andReverse() +
                    (1..4).col(12).zip((12 downTo 9).col(16)).andReverse() +
                    (9..12).row(12).zip((4 downTo 1).row(8)).andReverse() +
                    (13..16).row(12).zip((8 downTo 5).col(1)).andReverse()
            ).toMap()

private val dirSwitchesSmall: Map<Coordinate, String> =
    (
            (5..8).row(5).map { it to "right" } +
                    (1..4).col(9).map { it to "down" } +
                    (1..4).row(5).map { it to "down" } +
                    (12 downTo 9).row(1).map { it to "down" } +
                    (5..8).row(8).map { it to "right" } +
                    (12 downTo 9).col(9).map { it to "up" } +
                    (13..16).row(9).map { it to "left" } +
                    (8 downTo 5).col(12).map { it to "down" } +
                    (1..4).col(12).map { it to "left" } +
                    (12 downTo 9).col(16).map { it to "left" } +
                    (9..12).row(12).map { it to "up" } +
                    (4 downTo 1).row(8).map { it to "up" } +
                    (13..16).row(12).map { it to "right" } +
                    (8 downTo 5).col(1).map { it to "up" }
            ).toMap()

/* small cube
 row 5 5-8 <-> col 9 1-4 up->right down<-left
 row 5 1-4 <-> row 1 12-9!! ODWR up->down down<-up
 row 8 5-8 <-> col 9 12-9 !!ODWR down->right up<-left
 row 9 13-16 <-> col 12 8-5 !!ODWR up->left down<-right
 col 12 1-4 <-> col 16 12-9 !!ODWR right->left left<-right
 row 12 9-12 <-> row 8 4-1 !!ODWR down->up up<-down
 row 12 13-16 <-> col 1 8-5 !!ODWR down->right up<-left
 */

private val cubePassthroughs: Map<Coordinate, Coordinate> =
    (
            (101..150).row(50).zip((51..100).col(100)).andReverse() +
                    (1..50).row(101).zip((51..100).col(51)).andReverse() +
                    (51..100).row(150).zip((151..200).col(50)).andReverse() +
                    (51..100).row(1).zip((151..200).col(1)).andReverse() +
                    (101..150).row(1).zip((1..50).row(200)).andReverse() +
                    (1..50).col(150).zip((150 downTo 101).col(100)).andReverse() +
                    (1..50).col(51).zip((150 downTo 101).col(1)).andReverse()
            ).toMap()

private val dirSwitches: Map<Coordinate, String> =
    (
            (101..150).row(50).map { it to "left" } +
                    (51..100).col(100).map { it to "up" } +
                    (1..50).row(101).map { it to "right" } +
                    (51..100).col(51).map { it to "down" } +
                    (151..200).col(50).map { it to "up" } +
                    (51..100).row(150).map { it to "left" } +
                    (51..100).row(1).map { it to "right" } +
                    (151..200).col(1).map { it to "down" } +
                    (101..150).row(1).map { it to "up" } +
                    (1..50).row(200).map { it to "down" } +
                    (1..50).col(150).map { it to "left" } +
                    (150 downTo 101).col(100).map { it to "left" } +
                    (1..50).col(51).map { it to "right" } +
                    (150 downTo 101).col(1).map { it to "right" }
            ).toMap()
/*
 row 50 101-150 <-> col 100 51-100 down->left up<-right
 row 101 1-50 <-> col 51 51-100 up->right down<-left
 col 50 151-200 <-> row 150 51-100 right->up left<-down
 row 1 51-100 <-> col 1 151-200 up->right down<-left
 row 1 101-150 <-> row 200 1-50 up->up down<-down
 col 150 1-50 <-> col 100 150-101!!!! ODWROTNI right->left left<-right
 col 51 1-50 <-> col 1 150-101 !!!! ODWROTNIE left->right right<-left
 */