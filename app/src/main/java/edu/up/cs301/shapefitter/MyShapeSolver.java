package edu.up.cs301.shapefitter;

import java.util.Arrays;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author Alicia Sheehan
 * @version **** put completion date here ****
 */
public class MyShapeSolver extends ShapeSolver {

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
        int worldHeight = world.length;
        int worldWidth = world[0].length;
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        boolean[][] worldSegment = new boolean[shapeHeight][shapeWidth];

        int i;
        int j;
        int ii;
        int jj;



        boolean shapeFound = false;
        if (shapeHeight < worldHeight && shapeWidth < worldWidth) {



            for (i=0; i < worldHeight - shapeHeight + 1; i++) {
                for ( j = 0; j < worldWidth - shapeWidth + 1; j++) {
                    //Maybe use stream to copy a smaller array into worldSegment??


                    for (ii = 0; ii < shapeHeight; ii++) {
                        for (jj = 0; jj < shapeWidth; jj++) {
                            worldSegment[ii][jj] = world[i+ii][j+jj];
                        }
                    }
                    //compare shape and worldSegment
                    if(Arrays.deepEquals(worldSegment,shape)) {
                        display(i, j, Orientation.ROTATE_NONE);
                        shapeFound = true;
                    }
                    if(!shapeFound) {
                        undisplay();
                    }
                    //Return boolean or int saying whether equal
                    //use i and j for display if equal

                }
            }



        }

    }

    /*
    GOAL: OPTIMIZE solve()
 For example, if the shape is a square, no orientations
other than the unrotated one should be tested, since a square is identical to itself no matter
how it is rotated (in the 90-degree sense) or reflected; a vertical line would require that
only two orientations be tested; a “T” shape would require only four orientations to be
tested
     */



        // ****dummied up****
        // always "finds" a solution at row 3, column 4, with a 90-degree clockwise orientation
        //display(3, 4, Orientation.ROTATE_CLOCKWISE);

    /**
     * Creates a new array of the array shape to the specified orientation.
     * @param orient the desired orientation of the new shape
     * @return a new 2-Dimensional boolean array containing the transformed shape
     */
    public boolean[][] changeOrientation(Orientation orient) {
        //use switch statement followed by for loop to transform shape into new array
        return null;
    }


    /**
     *
     * @return
     */
    public Orientation[] discreteOrientations() {
        //
        return null;

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
