package main.java.ru.game.snake;

import main.java.ru.game.mouse.Mouse;
import main.java.ru.game.room.Room;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        SnakeSection head = new SnakeSection(x, y);
        sections.add(head);
        isAlive = true;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public void move() {
        if (!isAlive()) {
            return;
        } if (direction == SnakeDirection.UP) {
            move(0, -1);
        } else if (direction == SnakeDirection.RIGHT) {
            move(1, 0);
        } else if (direction == SnakeDirection.DOWN) {
            move(0, 1);
        } else if (direction == SnakeDirection.LEFT) {
            move(-1, 0);
        }
    }

    public void move(int dx, int dy) {
        SnakeSection head = new SnakeSection(sections.get(0).getX() + dx, sections.get(0).getY() + dy);
        checkBorders(head);

        if (!isAlive) return;
        checkBody(head);
        if (!isAlive) return;

        Room game = Room.game;
        Mouse mouse = game.getMouse();

        if (mouse.getX() == head.getX() && mouse.getY() == head.getY()) {
            sections.add(0, head);
            game.eatMouse();
        } else {
            sections.add(0, head);
            sections.remove(sections.size()-1);
        }
    }

    public void checkBorders(SnakeSection head) {
        Room room = Room.game;
        int height = room.getHeight();
        int width = room.getWidth();

        int y = head.getY();
        int x = head.getX();

        if (y < 0 || y >= height || x < 0 || x >= width) {
            isAlive = false;
        }
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}