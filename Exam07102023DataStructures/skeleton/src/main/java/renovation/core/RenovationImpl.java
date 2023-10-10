package renovation.core;

import renovation.models.Laminate;
import renovation.models.Tile;
import renovation.models.WoodType;

import java.util.*;

public class RenovationImpl implements Renovation {
    private final List<Tile> tilePile = new LinkedList<>();
    private final List<Laminate> laminatePile = new LinkedList<>();
    private double deliveredTileArea = 0.0;
    @Override
    public void deliverTile(Tile tile) {
        double totalArea = deliveredTileArea + tile.getArea();
        if (totalArea > 30) {
            throw new IllegalArgumentException();
        }

        tilePile.add(0, tile);
        deliveredTileArea = totalArea;
    }

    @Override
    public void deliverFlooring(Laminate laminate) {
laminatePile.add(laminate);
    }

    @Override
    public double getDeliveredTileArea() {

        return deliveredTileArea;
    }

    @Override
    public boolean isDelivered(Laminate laminate) {
        return laminatePile.contains(laminate);
    }

    @Override
    public void returnTile(Tile tile) {
        if (!tilePile.contains(tile)) {
            throw new IllegalArgumentException();
        }

        tilePile.remove(tile);
       deliveredTileArea -= tile.getArea();

    }

    @Override
    public void returnLaminate(Laminate laminate) {
        if (!laminatePile.contains(laminate)) {
            throw new IllegalArgumentException();
        }

        laminatePile.remove(laminate);
    }

    @Override
    public Collection<Laminate> getAllByWoodType(WoodType wood) {
        List<Laminate> matchingLaminates = new ArrayList<>();
        for (Laminate laminate : laminatePile) {
            if (laminate.getWoodType() == wood) {
                matchingLaminates.add(laminate);
            }
        }
        return matchingLaminates;
    }

    @Override
    public Collection<Tile> getAllTilesFitting(double width, double height) {
        List<Tile> fittingTiles = new ArrayList<>();
        for (Tile tile : tilePile) {
            if (tile.getWidth() <= width && tile.getHeight() <= height) {
                fittingTiles.add(tile);
            }
        }
        return fittingTiles;
    }

    @Override
    public Collection<Tile> sortTilesBySize() {
        List<Tile> sortedTiles = new ArrayList<>(tilePile);
        sortedTiles.sort(Comparator.comparing(Tile::getWidth).thenComparing(Tile::getDepth));
        return sortedTiles;
    }

    @Override
    public Iterator<Laminate> layFlooring() {

        Collections.reverse(laminatePile);
        return laminatePile.iterator();
    }
}
