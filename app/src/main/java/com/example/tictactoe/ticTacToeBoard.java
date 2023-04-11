package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ticTacToeBoard extends View {

    private final int boardColor;
    private final int xColor;
    private final int oColor;
    private final int winningLineColor;

    private boolean winningLine = false;

    private final Paint paint = new Paint();

    private final gameLogic game;

    private int cellSize = getWidth()/3;

    public ticTacToeBoard(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);

        game = new gameLogic();

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ticTacToeBoard, 0, 0);

        try {
            boardColor = array.getInteger(R.styleable.ticTacToeBoard_boardColor, 0);
            xColor = array.getInteger(R.styleable.ticTacToeBoard_xColor, 0);
            oColor = array.getInteger(R.styleable.ticTacToeBoard_oColor, 0);
            winningLineColor = array.getInteger(R.styleable.ticTacToeBoard_winningLineColor, 0);
        }finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/cellSize);
            int column = (int) Math.ceil(x/cellSize);

            if (!winningLine){
                if (game.updateGameBoard(row, column)){
                    invalidate();

                    if (game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    if (game.getPlayer()%2 == 0){
                        game.setPlayer(game.getPlayer()-1);
                    }else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }


            invalidate();

            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas){
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for(int column=1; column<3; column++){
            canvas.drawLine(cellSize*column, 0, cellSize*column, canvas.getWidth(), paint);
        }

        for(int row=1; row<3; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(), cellSize*row, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for (int row=0; row<3; row++){
            for (int column=0; column<3; column++){
                if (game.getGameBoard()[row][column] != 0){
                    if (game.getGameBoard()[row][column] == 1){
                        drawX(canvas, row, column);
                    }else {
                        drawO(canvas, row, column);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int column){
        paint.setColor(xColor);

        canvas.drawLine((float) ((column+1)*cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (column*cellSize + cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                         paint);

        canvas.drawLine((float) (column*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((column+1)*cellSize - cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);

    }

    private void drawO(Canvas canvas, int row, int column){
        paint.setColor(oColor);

        canvas.drawOval((float) (column*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((column*cellSize + cellSize) - cellSize*0.2),
                        (float) ((row*cellSize + cellSize) - cellSize*0.2),
                        paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column, row*cellSize + (float)cellSize/2,
                cellSize*3, row*cellSize + (float)cellSize/2, paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int column){
        canvas.drawLine(column*cellSize + (float)cellSize/2, row,
                column*cellSize + (float)cellSize/2, cellSize*3, paint);
    }

    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize*3,
                cellSize*3, 0, paint);
    }

    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0, 0,
                cellSize*3, cellSize*3, paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int column = game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                drawHorizontalLine(canvas, row, column);
                break;
            case 2:
                drawVerticalLine(canvas, row, column);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }

    public void setUpGame(Button restart, Button exit, TextView playerDisplay, String[] names){
        game.setRestartBtn(restart);
        game.setExitBtn(exit);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
        game.resetGame();
        winningLine = false;
    }
}
