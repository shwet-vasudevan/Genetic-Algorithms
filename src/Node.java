/**
 * Shwet Vasudevan
 * 6549422
 * November 13, 2020
 */

/**
 * The node class is used in the main Assign2 class
 */

public class Node {
    int[] chrom;
    public Node(int[] chrom) {
        this.chrom = chrom;
    }

    public int[] getChrom(){
        return chrom;
    }

    public void printChrom(){
        for (int i : chrom) {
            System.out.print(i+" ");
        }
        System.out.println();
    }
}
