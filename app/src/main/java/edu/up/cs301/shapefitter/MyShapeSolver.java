package edu.up.cs301.shapefitter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author Alicia Sheehan
 * @version **** put completion date here ****
 */
public class MyShapeSolver extends ShapeSolver {

    private int worldHeight = world.length;
    private int worldWidth = world[0].length;
    private int shapeHeight = shape.length;
    private int shapeWidth = shape[0].length;
    private int maxIndex = shapeHeight-1;


    /**
     * Creates a solver for a particular problem.
     *
     * @param parmShape the shape to fit
     * @param parmWorld the world to fit it into
     * @param acc to send notification messages to
     */
    public MyShapeSolver(boolean[][] parmShape, boolean[][] parmWorld, ShapeSolutionAcceptor acc) {
        // invoke superclass constructor
        super(parmShape, parmWorld, acc);
    }

    /**
     * Solves the problem by finding a fit, if possible. The last call to display tells where
     * the fit is. If there is no fit, no call to display should be made--alternatively, a call to
     * undisplay can be made.
     */
    public void solve() {

        //create a 2-D boolean array of the same size as shape.
        boolean[][] worldSegment = new boolean[shapeHeight][shapeWidth];


        int i; //integer value for incrementing rows of shape
        int j; //integer value for incrementing columns of shape
        int ii; //integer value for incrementing rows of orientedShape
        int jj; //integer value for incrementing columns of orientedShape

        boolean[][] orientedShape; // 2 Dimensional boolean array equal to transformed shape
        Orientation currOrientation;

        //an array of NULL and valid, distinct orientations
        Orientation[] diffOrientations = discreteOrientations();

        int o; // integer value used to increment orientation
        boolean shapeFound = false; // boolean variable for whether shape matches world segment

        // use a for loop to check all different orientations given by discreteOrientations
        for(o = 0; o < diffOrientations.length; o++) {

            currOrientation = diffOrientations[o];
            if ( currOrientation != null) {

                //transform shape using orientedShape array to desired orientation before comparison begins
                orientedShape = changeOrientation(currOrientation);

                //make sure shape is smaller than world before continuing
                if (shapeHeight <= worldHeight && shapeWidth <= worldWidth) {

                    //create a segment of world that is equal in size to shape to compare shape to
                    for (i = 0; i < worldHeight - shapeHeight + 1; i++) {
                        for (j = 0; j < worldWidth - shapeWidth + 1; j++) {

                            //set the values of worldsegment equal to the corresponding values of world
                            for (ii = 0; ii < shapeHeight; ii++) {
                                for (jj = 0; jj < shapeWidth; jj++) {
                                    worldSegment[ii][jj] = world[i + ii][j + jj];
                                }
                            }

                            //compare the oriented shape and worldSegment with deep equals
                            //call display and exit solve() if equals
                            if (Arrays.deepEquals(worldSegment, orientedShape)) {
                                display(i, j, currOrientation);
                                shapeFound = true;
                                return;
                            }
                            if (!shapeFound) {
                                undisplay();
                            }
                            //Return boolean or int saying whether equal
                            //use i and j for display if equal

                        }
                    }


                }
            }
        }
    }



    /**
     * Creates a new 2-Dimensional boolean array of the array 'shape' to the specified orientation.
     *
     * @param orient the desired orientation of the new shape
     * @return a new 2-Dimensional boolean array containing the transformed shape
     */
    public boolean[][] changeOrientation(Orientation orient) {

        int i; //integer value for incrementing rows of shape
        int j; //integer value for incrementing columns of shape


        /* Creates a two dimensional array of booleans the same size as shape*/
        boolean[][] orientedShape = new boolean[shape.length][shape[0].length];

        /* Creates a two dimensional array of booleans to hold the reversed copy
         of orientedShape or shape for reversed transformations*/
        boolean[][] reversedShape;


        //Switch to return array in desired orientation
        switch (orient) {
            case ROTATE_NONE:
                return shape;

            case ROTATE_CLOCKWISE:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[j][maxIndex-i] = shape[i][j];
                    }
                }
                return orientedShape;

            case ROTATE_180:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[maxIndex-i][maxIndex-j] = shape[i][j];
                    }
                }
                return orientedShape;

            case ROTATE_COUNTERCLOCKWISE:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[maxIndex-j][i] = shape[i][j];
                    }
                }
                return orientedShape;

            case ROTATE_NONE_REV:
                return reverse(shape);

            case ROTATE_CLOCKWISE_REV:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[j][maxIndex-i] = shape[i][j];
                    }
                }
                reversedShape = reverse(orientedShape);
                return reversedShape;

            case ROTATE_180_REV:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[maxIndex-i][maxIndex-j] = shape[i][j];
                    }
                }
                reversedShape = reverse(orientedShape);
                return reversedShape;

            case ROTATE_COUNTERCLOCKWISE_REV:
                for (i = 0; i < shapeHeight; i++ ) {
                    for (j = 0; j < shapeWidth; j++) {
                        orientedShape[maxIndex-j][i] = shape[i][j];
                    }
                }
                reversedShape = reverse(orientedShape);
                return reversedShape;

        }

        //use switch statement followed by for loop to transform shape into new array
        return null;
    }


    /**
     * Finds the possible unique orientations of a particular shape as compared to base shape
     *
     * @return returns an Orientation array with unique orientations and NULL values where the array would be redundant
     */
    private Orientation[] discreteOrientations() {
        Orientation[] discreteOri = new Orientation[8];

        discreteOri[0]=(Orientation.ROTATE_NONE);


        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_CLOCKWISE))) {
            discreteOri[1] = (Orientation.ROTATE_CLOCKWISE);
        } else { discreteOri[1] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_180))) {
            discreteOri[2] =(Orientation.ROTATE_180);
        } else { discreteOri[2] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_COUNTERCLOCKWISE))) {
            discreteOri[3] =(Orientation.ROTATE_CLOCKWISE);
        } else { discreteOri[3] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_NONE_REV))) {
            discreteOri[4] =(Orientation.ROTATE_NONE_REV);
        } else {discreteOri[4] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_CLOCKWISE_REV))) {
            discreteOri[5] =(Orientation.ROTATE_COUNTERCLOCKWISE_REV);
        } else {discreteOri[5] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_180_REV))) {
            discreteOri[6] =(Orientation.ROTATE_180_REV);
        } else {discreteOri[6] = null; }

        if(!Arrays.deepEquals(shape, changeOrientation(Orientation.ROTATE_COUNTERCLOCKWISE_REV))) {
            discreteOri[7] =(Orientation.ROTATE_COUNTERCLOCKWISE_REV);
        } else {discreteOri[7] = null; }

        return discreteOri;

    }

    /**
     * Transforms the supplied array to a left-right reflection of itself.
     *
     * @param shapeInstance the shape to transform
     * @return a 2-Dimensional boolean array that is a left-right reflection of shapeInstance
     */
    private boolean[][] reverse(boolean[][] shapeInstance) {
        int i;
        int j;

        boolean[][] revShape = new boolean[shapeHeight][shapeWidth];

        for (i = 0; i < shapeHeight; i++) {
            for (j = 0; j < shapeWidth; j++) {
                revShape[i][maxIndex - j] = shapeInstance[i][j];
            }
        }

        return revShape;
    }


    /**
     * Checks if the shape is well-formed: has at least one square, and has all squares connected.
     *
     * @return whether the shape is well-formed
     */
    public boolean check() {
        return Math.random() < 0.5;
    }

}
