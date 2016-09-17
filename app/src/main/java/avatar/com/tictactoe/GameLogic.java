package avatar.com.tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by partha on 9/15/16.
 */
public class GameLogic {

    static int[] cornerCell = new int[]{0, 2, 6, 7};
    static int center = 4;
    int[] middleCell = new int[]{1, 3, 5, 7};
    private int[][] winCombination = new int[8][3];
    private int[][] straightCombination = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
    private int[][] diagonalCombination = new int[][]{{0, 4, 8}, {2, 4, 6}};

    public int algo(String type, int[] moveArray, int[] viewArray) {

        List emptyCell = new ArrayList();
        for (int i = 0; i < moveArray.length; i++) {
            if (moveArray[i] == 0) {
                emptyCell.add(viewArray[i]);
            }
        }
        if ("random".equalsIgnoreCase(type)) {
            return (random(emptyCell));
        } else if ("easy".equalsIgnoreCase(type)) {
            return (mediumDifficulty(emptyCell, moveArray, viewArray));
        }
//        else if ("hard".equalsIgnoreCase(type)) {
//            return (hardDifficulty(emptyCell, moveArray, viewArray));
//        }
        return -1;
    }

    private int random(List emptyCell) {
        //for random we just populate random cells
        int emptyCellCount = emptyCell.size();
        if (emptyCellCount > 1) {
            int item = new Random().nextInt(emptyCellCount);
            return (int) emptyCell.get(item);
        }
        return -1;
    }

    private int mediumDifficulty(List emptyCell, int[] moveArray, int[] viewArray) {
        //for medium difficulty we can populate both all the cells giving the user a fair chance

        populateWinCombi();
        int userMove = -1;

        int nextMove = -1;
        int[][] userCombination = new int[3][];
        int moves = 0;
        for (int i = 0; i < moveArray.length; i++) {
            if (moveArray[i] == 2) {
                moves++;
            }
        }

        if (moves == 0) {
            return random(emptyCell);
        } else if (moves == 1) {
            int a = 0;

            for (int i = 0; i < moveArray.length; i++) {
                if (moveArray[i] == 1) {
                    userMove = moveArray[i];
                }
                for (int j = 0; j < winCombination.length; j++) {
                    for (int k = 0; k < winCombination[j].length; k++) {
                        if (moveArray[i] == 2 && winCombination[j][k] == moveArray[i]) {
                            userCombination[a++] = winCombination[j];
                        }
                    }
                }
            }
        }
        int[] currentCombination = new int[]{};
        int len = userCombination.length;
        if (len > 0) {
            int item = new Random().nextInt(len);
            currentCombination = userCombination[item];
        }

        if (currentCombination == null) {
            return random(emptyCell);
        } else {
            for (int i = 0; i < currentCombination.length; i++) {
                for (int j = 0; j < 3; j++) {
                    if (userMove == currentCombination[j]) {
                        continue;
                    } else {
                        int ran = (j + 3) % 3;
                        return viewArray[currentCombination[ran]];
                    }
                }
            }
        }

        return random(emptyCell);

    }

//    private int hardDifficulty(List emptyCell, int[] moveArray, int[] viewArray) {
//        //for hard difficulty we can populate centre or the corner to increase our win
//
//        populateWinCombi();
//        int userMove = -1;
//        int myMove = -1;
//
//        int nextMove = -1;
//        int[][] userCombination = new int[3][];
//        int moves = 0;
//        for (int i = 0; i < moveArray.length; i++) {
//            if (moveArray[i] == 1) {
//                moves++;
//            }
//        }
//
//        if (moves == 0) {
//            if (moveArray[4] == 0) {
//                return viewArray[4];
//            } else {
//                for (int i = 0; i < cornerCell.length; i++) {
//                    for (int j = 0; j < moveArray.length; j++) {
//                        if (moveArray[j] == 0 && cornerCell[i] == moveArray[j]) {
//                            return viewArray[moveArray[j]];
//                        }
//                    }
//                }
//            }
//        } else if (moves == 1) {
//            for (int i = 0; i < cornerCell.length; i++) {
//                for (int j = 0; j < moveArray.length; j++) {
//                    if (moveArray[j] == 0 && cornerCell[i] == moveArray[j]) {
//                        return viewArray[moveArray[j]];
//                    }
//                }
//            }
//        } else if (moves >= 2) {
//            int userGoodMoveCount = 0;
//            int[] userMoves = new int[moves];
//            Integer[][] possibleMoves = new Integer[moves][moves];
//            int a = 0;
//            int item = 0;
//
//            Integer[][] newWinCombination = new Integer[winCombination.length][3];
//            //get the missing cell here which the user nay fill next
//            for (int j = 0; j < winCombination.length; j++) {
//                for (int k = 0; k < winCombination[j].length; k++) {
//                    newWinCombination[j][k] = Integer.valueOf(winCombination[j][k]);
//                }
//            }
//
//            for (int i = 0; i < moveArray.length; i++) {
//                for (int j = 0; j < moves; ) {
//                    if (moveArray[i] == 1) {
//                        userMoves[j++] = moveArray[i];
//                    }
//                }
//            }
//
////            for (int i = 0; i < userMoves.length; i++) {
////                for (int j = 0; j < userMoves.length; j++) {
////                    possibleMoves[i][j] = userMoves[][]
////                }
////            }
////
////
////            for (int i = 0, c = 0; i < winCombination.length; i++) {
////                for (int j = 0; j < winCombination[i].length; j++) {
////                    a = 0;
////                    for (int k = 0; k < moveArray.length; k++) {
////                        if (moveArray[k] == 1 && moveArray[k] == winCombination[i][j]) {
////                            a++;
////                            item = moveArray[k];
////                        }
////                        if (a == 2) {
////                            possibleMoves[c++] = winCombination[i];
////                        }
////                    }
////                }
////            }
//
//            if (Arrays.asList(newWinCombination[j]).containsAll(Arrays.asList(userMoves)))
//
//
//                //this is the deciding step for the game , so we check the moves of the user
//                for (int i = 0; i < moveArray.length; i++) {
//                    if (moveArray[i] == 1) {
//                        userMoves[userGoodMoveCount++] = i;
//                    }
//                }
//
//
////            Integer[][] newWinCombination = new Integer[winCombination.length][3];
//            //get the missing cell here which the user nay fill next
//            for (int j = 0; j < winCombination.length; j++) {
//                for (int k = 0; k < winCombination[j].length; k++) {
//                    newWinCombination[j][k] = Integer.valueOf(winCombination[j][k]);
//                }
//                if (Arrays.asList(newWinCombination[j]).containsAll(Arrays.asList(userMoves))) {
//                }
//            }
//        }
//
//        return
//
//                random(emptyCell);
//
//    }


    public Map winwin(int[] moveArray) {
        //if we can map the user move in the array , we can later expand to NxN
        //we return true for user win and false for user lose/draw
        populateWinCombi();

        int a = 0;
        int b = 0;
        Map obj = new HashMap<String, Object>();
        for (int i = 0; i < winCombination.length; i++) {
            a = 0;
            b = 0;
            for (int j = 0; j < 3; j++) {
                if (moveArray[winCombination[i][j]] == 1) {
                    a++;
                } else if (moveArray[winCombination[i][j]] == 2) {
                    b++;
                }
                if (a == 3) {
                    obj.put("win", true);
                    obj.put("combination", winCombination[i]);
                    break;
                }
                if (b == 3) {
                    obj.put("win", false);
                    obj.put("combination", winCombination[i]);
                }
            }
            if (a == 3) {
                break;
            }
        }

        return obj;
    }

    private void populateWinCombi() {
        for (int i = 0; i < 3; i++) {
            winCombination[i] = straightCombination[i];
        }

        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                winCombination[i][j] = straightCombination[j][i - 3];
            }
        }

        for (int i = 6; i < 8; i++) {
            winCombination[i] = diagonalCombination[i - 6];
        }
    }
}
