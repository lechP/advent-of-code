package y2022.day22

import common.Coordinate

/* small cube
 row 5 5-8 <-> col 9 1-4 up->right down<-left
 row 5 1-4 <-> row 1 12-9 !!INVERSE up->down down<-up
 row 8 5-8 <-> col 9 12-9 !!INVERSE down->right up<-left
 row 9 13-16 <-> col 12 8-5 !!INVERSE up->left down<-right
 col 12 1-4 <-> col 16 12-9 !!INVERSE right->left left<-right
 row 12 9-12 <-> row 8 4-1 !!INVERSE down->up up<-down
 row 12 13-16 <-> col 1 8-5 !!INVERSE down->right up<-left
 */

val orientationMappings: Map<Orientation, Orientation> =
    (
            (101..150).row(50, "down").zip((51..100).col(100, "left")) +
                    (51..100).col(100, "right").zip((101..150).row(50, "up")) +
                    (1..50).row(101, "up").zip((51..100).col(51, "right")) +
                    (51..100).col(51, "left").zip((1..50).row(101, "down")) +
                    (151..200).col(50, "right").zip((51..100).row(150, "up")) +
                    (51..100).row(150, "down").zip((151..200).col(50, "left")) +
                    (51..100).row(1, "up").zip((151..200).col(1, "right")) +
                    (151..200).col(1, "left").zip((51..100).row(1, "down")) +
                    (101..150).row(1, "up").zip((1..50).row(200, "up")) +
                    (1..50).row(200, "down").zip((101..150).row(1, "down")) +
                    (1..50).col(150, "right").zip((150 downTo 101).col(100, "left")) +
                    (150 downTo 101).col(100, "right").zip((1..50).col(150, "left")) +
                    (1..50).col(51, "left").zip((150 downTo 101).col(1, "right")) +
                    (150 downTo 101).col(1, "left").zip((1..50).col(51, "right"))

            ).toMap()

/*
 row 50 101-150 <-> col 100 51-100 down->left up<-right
 row 101 1-50 <-> col 51 51-100 up->right down<-left
 col 50 151-200 <-> row 150 51-100 right->up left<-down
 row 1 51-100 <-> col 1 151-200 up->right down<-left
 row 1 101-150 <-> row 200 1-50 up->up down<-down
 col 150 1-50 <-> col 100 150-101!!!! INVERSE right->left left<-right
 col 51 1-50 <-> col 1 150-101 !!!! INVERSE left->right right<-left
 */

private fun IntProgression.col(col: Int, dir: String) = map { Orientation(Coordinate(it, col), dir) }
private fun IntProgression.row(row: Int, dir: String) = map { Orientation(Coordinate(row, it), dir) }