package ru.mishanin.game.math;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

/**
 * Класс для преобразования матриц
 * */
public class MatrixUtils {

    public MatrixUtils(){}

    /**
     * @param mat исходная матрица преобразований
     * @param dst итоговый квадрат
     * @param scr исходный квадрат
     * */
    public static void calcTransformMatrix(Matrix4 mat, RectBody scr, RectBody dst){

        final float scaleX = dst.getWidth() / scr.getWidth();
        final float scaleY = dst.getHeight() / scr.getHeight();
        mat.idt().translate(dst.getX(), dst.getY(),0f).scale(scaleX,scaleY,1f).translate(-scr.getX(),-scr.getY(),0f);
    }

    /**
     * @param mat исходная матрица преобразований
     * @param dst итоговый квадрат
     * @param scr исходный квадрат
     * */
    public static void calcTransformMatrix(Matrix3 mat, RectBody scr, RectBody dst){

        final float scaleX = dst.getWidth() / scr.getWidth();
        final float scaleY = dst.getHeight() / scr.getHeight();
        mat.idt().translate(dst.getX(), dst.getY()).scale(scaleX,scaleY).translate(-scr.getX(),-scr.getY());
    }
}
