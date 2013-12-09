/*
 * Copyright (C) 2013 Alexey Matveev (mvaleksej@gmail.com)
 */
package by.matveev.christmas.core;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * @author Alexey Matveev
 */
public class OldPlayScreen extends AbstractScreen {

    private HeadUpDisplay hud;
    private Countdown countdown;

    private final Cell[][] cells = new Cell[Cfg.GRID_WIDTH][Cfg.GRID_HEIGHT];

    private Cell firstCell;


    public OldPlayScreen() {
    }

    @Override
    public void show() {
        super.show();

        while (true) {
            createCells();

            if (hasMatches()) continue;
            if (!hasPossibles()) continue;

            break;
        }

        showCells();


        hud = new HeadUpDisplay();
        stage.addActor(hud);

        countdown = new Countdown(Cfg.INITIAL_TIME, new Callback<Integer>() {
            @Override
            public void result(Integer result) {
                hud.setTime(result);
            }
        });

        countdown.start();
    }

    private void showCells() {
        for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
            for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
                cells[col][row].addAction(sequence(delay((row + col) * 0.1f), alpha(1f, 0.5f)));
            }
        }
    }

    private boolean hasPossibles() {
        for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
            for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
                if (matchPattern(col, row, new int[][]{{1, 0}}, new int[][]{{-2, 0}, {-1, -1}, {-1, 1}, {2, -1}, {2, 1}, {3, 0}})) {
                    return true;
                }

                if (matchPattern(col, row, new int[][]{{2, 0}}, new int[][]{{1, -1}, {1, 1}})) {
                    return true;
                }

                if (matchPattern(col, row, new int[][]{{0, 1}}, new int[][]{{0, -2}, {-1, -1}, {1, -1}, {-1, 2}, {1, 2}, {0, 3}})) {
                    return true;
                }

                if (matchPattern(col, row, new int[][]{{0, 2}}, new int[][]{{-1, 1}, {1, 1}})) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean matchType(int col, int row, int type) {
        if ((col < 0) || (col >= Cfg.GRID_WIDTH) || (row < 0) || (row >= Cfg.GRID_HEIGHT)) return false;
        return (cells[col][row].getType() == type);
    }

    public boolean matchPattern(int col, int row, int[][] mustHave, int[][] needOne) {
        final int thisType = cells[col][row].getType();

        for (int i = 0; i < mustHave.length; i++) {
            if (!matchType(col + mustHave[i][0], row + mustHave[i][1], thisType)) {
                return false;
            }
        }

        for (int i = 0; i < needOne.length; i++) {
            if (matchType(col + needOne[i][0], row + needOne[i][1], thisType)) {
                return true;
            }
        }
        return false;
    }

    private void createCells() {
        cleanUp();


        final TextureAtlas iconsAtlas = Assets.instance().get("gfx/icons.atlas");
        final TextureAtlas masksAtlas = Assets.instance().get("gfx/masks.atlas");

        float initialX = (Cfg.width() - (Cfg.CELL_WIDTH * Cfg.GRID_WIDTH + (Cfg.GRID_WIDTH - 1) * Cfg.SPACING)) * 0.5f;
        float currentX = initialX;
        float currentY = Cfg.height() * 0.8f;


        for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
            for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
                final int type = MathUtils.random(1, 7);
                final Cell cell = new Cell(type, row, col,
                        masksAtlas.findRegion("rect"),
                        iconsAtlas.findRegion("icon" + type));

                cells[col][row] = cell;

                cell.setPosition(currentX, currentY);
                cell.getColor().a = 0f;
                cell.addListener(new CellClickHandler(cell));
                stage.addActor(cell);

                currentX += Cfg.CELL_WIDTH + Cfg.SPACING;
            }
            currentY -= Cfg.CELL_HEIGHT + Cfg.SPACING;
            currentX = initialX;
        }


    }

    private void cleanUp() {
        for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
            for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
                cells[col][row] = null;
            }
        }

        for (Actor actor : stage.getActors()) {
            if (actor instanceof Cell) {
                actor.remove();
            }
        }
    }

    private final class CellClickHandler extends ClickListener {

        private final Cell cell;

        private CellClickHandler(Cell cell) {
            this.cell = cell;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if (firstCell == null) {
                cell.setSelected(true);
                firstCell = cell;
            } else if (firstCell == cell) {
                cell.setSelected(false);
                firstCell = null;
            } else {
                firstCell.setSelected(false);

                if ((firstCell.getRow() == cell.getRow()) && (Math.abs(firstCell.getCol() - cell.getCol()) == 1)) {
                    swap(firstCell, cell);
                    firstCell = null;
                } else if ((firstCell.getCol() == cell.getCol()) && (Math.abs(firstCell.getRow() - cell.getRow()) == 1)) {
                    swap(firstCell, cell);
                    firstCell = null;
                } else {
                    firstCell = cell;
                    firstCell.setSelected(true);
                }
            }
        }
    }

    private void swap(final Cell first, final Cell second) {
        doSwap(first, second, new Runnable() {
            @Override
            public void run() {
                final List<Cell> matches = findMatches();
                if (matches.size() > 0) {
                    handleMathches(matches);
                } else {
                    doSwap(second, first, null);
                    if (!hasPossibles()) {
                        // TODO Game Over
                    }


                }
            }
        });
    }

    private void handleMathches(List<Cell> matches) {
        for (Cell cell : matches) {
            affect(cell);

            cells[cell.getCol()][cell.getRow()] = null;
            cell.remove();
        }
        addNewCells();
    }

    private void addNewCells() {
        final TextureAtlas iconsAtlas = Assets.instance().get("gfx/icons.atlas");
        final TextureAtlas masksAtlas = Assets.instance().get("gfx/masks.atlas");


        float initialX = (Cfg.width() - (Cfg.CELL_WIDTH * Cfg.GRID_WIDTH + (Cfg.GRID_WIDTH - 1) * Cfg.SPACING)) * 0.5f;
        float initialY = Cfg.height() * 0.8f;

        for(int col=0;col<Cfg.GRID_WIDTH;col++) {
            int  missingPieces = 0;
            for(int row =Cfg.GRID_HEIGHT-1;row>=0;row--) {
                if (cells[col][row] == null) {
                    final int type = MathUtils.random(1, 7);
                    final Cell cell = new Cell(type, row, col,
                            masksAtlas.findRegion("rect"),
                            iconsAtlas.findRegion("icon" + type));

                    cell.setPosition(initialX + (Cfg.CELL_WIDTH * col + (Cfg.SPACING) * col),
                            initialY - (Cfg.CELL_HEIGHT * row + (Cfg.SPACING) * row));
                    cells[col][row] = cell;

                    cell.getColor().a = 0f;
                    cell.addAction(sequence(delay((row + col) * 0.1f), alpha(1f, 0.5f)));

                    stage.addActor(cell);


                }
            }
        }
    }


    public void affect(Cell cell) {
        for (int row = cell.getRow() - 1; row >= 0; row--) {
            if (cells[cell.getCol()][row] != null) {
                cells[cell.getCol()][row].setRow(cells[cell.getCol()][row].getRow() + 1);
                cells[cell.getCol()][row + 1] = cells[cell.getCol()][row];
                cells[cell.getCol()][row] = null;
            }
        }
    }

    private List<Cell> findHorizontalMatches(final int col, final int row) {
        final List<Cell> found = new ArrayList<Cell>();
        for (int ix = 0; col + ix < Cfg.GRID_WIDTH; ix++) {
            if (cells[col][row].getType() == cells[col + ix][row].getType()) {
                found.add(cells[col + ix][row]);
            }
        }
        return found;
    }

    private List<Cell> findVerticalMatches(int col, int row) {
        final List<Cell> found = new ArrayList<Cell>();
        for (int ix = 0; row + ix < Cfg.GRID_HEIGHT; ix++) {
            if (cells[col][row].getType() == cells[col][row + ix].getType()) {
                found.add(cells[col][row + ix]);
            }
        }
        return found;
    }

    private List<Cell> findMatches() {
        final List<Cell> found = new ArrayList<Cell>();

        for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
            for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
                final List<Cell> match = findHorizontalMatches(col, row);
                if (match.size() > 2) {
                    found.addAll(match);
                    col += match.size() - 1;
                }
            }
        }

        for (int col = 0; col < Cfg.GRID_WIDTH; col++) {
            for (int row = 0; row < Cfg.GRID_HEIGHT; row++) {
                final List<Cell> match = findVerticalMatches(col, row);
                if (match.size() > 2) {
                    found.addAll(match);
                    row += match.size() - 1;
                }

            }
        }

        System.out.println("found match: " + found.size());

        return found;
    }

    private boolean hasMatches() {
        final boolean b = findMatches().size() > 0;
        System.out.println(!b ? "Not matches" : "Matches found");
        return b;
    }

    private void doSwap(Cell first, Cell second, final Runnable onComplete) {
        final float firstX = first.getX();
        final float firstY = first.getY();

        final int firstRow = first.getRow();
        final int firstCol = first.getCol();

        first.setRow(second.getRow());
        first.setCol(second.getCol());

        second.setRow(firstRow);
        second.setCol(firstCol);

        cells[first.getCol()][first.getRow()] = first;
        cells[second.getCol()][second.getRow()] = second;

        first.addAction(moveTo(second.getX(), second.getY(), 0.5f));
        second.addAction(sequence(moveTo(firstX, firstY, 0.5f), new Action() {
            @Override
            public boolean act(float delta) {
                if (onComplete != null) onComplete.run();
                return true;
            }
        }));

    }
}

