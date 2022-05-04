package bullethell.entity.ship;

import bullethell.Coords;

public class ShipMovementCreator {
    public static int createOscillatingMovement(int frames) {
        return (int) (5 * Math.sin(((frames / 8f) * Math.PI) / 10 + (Math.PI / 2)));
    }

    public static int shiftTowardTargetCoordsX(Coords currentCoords, Coords targetCoords, float speed) {
        if (currentCoords.getX() == targetCoords.getX()) {
            return 0;
        }
        return (int) Math.ceil(-(currentCoords.getX() - targetCoords.getX()) * speed);
    }

    public static int shiftTowardTargetCoordsY(Coords currentCoords, Coords targetCoords, float speed) {
        if (currentCoords.getY() == targetCoords.getY()) {
            return 0;
        }
        return (int) Math.ceil(-(currentCoords.getY() - targetCoords.getY()) * speed);
    }
}
