package CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList {
    public List<AdminUnit> units = new ArrayList<>();


    public void read(String filename) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(filename), ",", true);
        Map<Long, AdminUnit> ids = new HashMap<>();
        Map<AdminUnit, Long> ids2 = new HashMap<>();
        Map<AdminUnit, Long> parents = new HashMap<>();
        Map<Long,List<AdminUnit>> parentid2child = new HashMap<>();

        while (reader.next()){
            String name = reader.get("name");
            Integer admin_level = reader.getInt("admin_level");
            Double population = reader.getDouble("population");
            Double area = reader.getDouble("area");
            Double density = reader.getDouble("density");

            AdminUnit currentUnit = new AdminUnit(name, admin_level, population, area, density);
            units.add(currentUnit);

            Long parentId = reader.getLong("parent");
            ids.put(reader.getLong("id"), currentUnit);
            ids2.put(currentUnit, reader.getLong("id"));
            parents.put(currentUnit, parentId);

            if (parentId != null) {
                parentid2child.putIfAbsent(parentId, new ArrayList<>());
                parentid2child.get(parentId).add(currentUnit);
            }

            currentUnit.bbox = new BoundingBox();
            currentUnit.bbox.addPoint(reader.getDouble("x1"), reader.getDouble("y1"));
            currentUnit.bbox.addPoint(reader.getDouble("x2"), reader.getDouble("y2"));
            currentUnit.bbox.addPoint(reader.getDouble("x3"), reader.getDouble("y3"));
            currentUnit.bbox.addPoint(reader.getDouble("x4"), reader.getDouble("y4"));
            currentUnit.bbox.addPoint(reader.getDouble("x5"), reader.getDouble("y5"));
        }

        for (AdminUnit unit : units) {
            Long parentId = parents.get(unit);
            if (parentId == null) {
                unit.parent = null;
            } else {
                unit.parent = ids.get(parentId);
            }
            unit.fixMissingValues();

            List<AdminUnit> children = parentid2child.get(ids2.get(unit));
            if (children != null) {
                if (unit.children == null) {
                    unit.children = new ArrayList<>();
                }
                unit.children.addAll(children);
            }
        }
    }

    void list(PrintStream out){
        for (AdminUnit unit : units){
            AdminUnit.show(unit, out);
        }
    }

    void list(PrintStream out, int offset, int limit) {
        if (offset < 0 || limit > units.size() || offset > limit) {
            throw new IllegalArgumentException("Invalid offset or limit");
        }
        for (int i = offset; i < limit; i++) {
            AdminUnit.show(units.get(i), out);
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            String name = unit.name;
            if (regex) {
                if (name.matches(pattern)) {
                    ret.units.add(unit);
                }
            }
            else {
                if (name.contains(pattern)) {
                    ret.units.add(unit);
                }
            }
        }
        return ret;
    }

    AdminUnitList selectByParent(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (unit.parent != null) {
                String parentName = unit.parent.name;
                if (regex) {
                    if (parentName.matches(pattern)) {
                        ret.units.add(unit);
                    }
                } else {
                    if (parentName.contains(pattern)) {
                        ret.units.add(unit);
                    }
                }
            }
        }
        return ret;
    }

    public AdminUnitList getNeighbours(AdminUnit unit) {
        return getNeighbours(unit, 0);
    }

    public AdminUnitList getNeighbours(AdminUnit unit, double maxdistance) {
        AdminUnitList neighbors = new AdminUnitList();

        for (AdminUnit candidate : units) {
            if (candidate.adminLevel != unit.adminLevel) continue;
            if (candidate.name.equals(unit.name)) continue;

            if (unit.adminLevel < 8) {
                if (unit.bbox != null && candidate.bbox != null && unit.bbox.intersects(candidate.bbox)) {
                    neighbors.units.add(candidate);
                }
            }
            else {
                if (unit.bbox != null && candidate.bbox != null && unit.bbox.distanceTo(candidate.bbox) <= maxdistance * 1000) {
                    neighbors.units.add(candidate);
                }
            }
        }
        return neighbors;
    }


    List<AdminUnit> returnIntersectedUnits (List<AdminUnit> children, AdminUnit unit){
        List<AdminUnit> intersectedChildren = new ArrayList<>();
        if (children != null){
            for (AdminUnit child : children) {
                if (child.bbox.intersects(unit.bbox)) {
                    if (child.adminLevel == unit.adminLevel && child != unit)
                    {
                        intersectedChildren.add(child);
                    }
                    else {
                        intersectedChildren.addAll(returnIntersectedUnits(child.children, unit));
                    }
                }
            }
        }
        return intersectedChildren;
    }

    public AdminUnitList getNeighboursByChildren(AdminUnit unit) {
        return getNeighboursByChildren(unit, 0);
    }

    public AdminUnitList getNeighboursByChildren(AdminUnit unit, double maxdistance){
        List<AdminUnit> wojew = new ArrayList<>();
        AdminUnitList neighbourhood = new AdminUnitList();

        for (AdminUnit unit1 : units){
            if (unit1.parent == null){
                wojew.add(unit1);
            }
        }

        if (unit.adminLevel >= 8){
            AdminUnit resizedUnit = unit;
            resizedUnit.bbox = BoundingBox.resize(unit.bbox, maxdistance);
            neighbourhood.units = returnIntersectedUnits(wojew, resizedUnit);
            List<AdminUnit> toRemove = new ArrayList<>();
            for (AdminUnit candidate : neighbourhood.units) {
                double distance = resizedUnit.bbox.distanceTo(candidate.bbox);
                if (distance > maxdistance * 1000) { // maxdistance w kilometrach
                    toRemove.add(candidate);
                }
            }
            neighbourhood.units.removeAll(toRemove);
        }
        else {
            neighbourhood.units = returnIntersectedUnits(wojew, unit);
        }
        return neighbourhood;
    }

    public AdminUnit findByName(String name) throws IOException {
        for (AdminUnit unit : units) {
            if (unit.name.equals(name)) return unit;
        }
        throw new IOException ("No such admin unit");
    }

    public String getWKT(){
        StringBuilder sb = new StringBuilder();
        for (AdminUnit unit : units) {
            if (unit.bbox != null) {
                sb.append(unit.bbox.getWKT());
            }
        }
        String replaced = sb.toString().replace(")\nPOLYGON(", ",");
        return replaced;
    }

    public AdminUnit findByNameAndLevel(String name, int admin_level){
        for (AdminUnit unit : units) {
            if (unit.name.equalsIgnoreCase(name) && unit.adminLevel == admin_level) {
                return unit;
            }
        }
        return null;
    }

    public AdminUnitList sortInplaceByName(){

        class Compare implements Comparator<AdminUnit> {
            public int compare(AdminUnit t, AdminUnit t1){
                return t.name.compareTo(t1.name);
            }
        }

        units.sort(new Compare());
        return this;
    }

    AdminUnitList sortInplaceByArea(){
        units.sort(new Comparator <AdminUnit> ()
        { public int compare(AdminUnit t, AdminUnit t1) { return Double.compare
                (t1.area == null ? Double.NaN: t1.area,
                        t.area == null ? Double.NaN: t.area); }});
        return this;
    }

    AdminUnitList sortInplaceByPopulation(){
        units.sort((t, t1) -> Double.compare
                (t.population == null? Double.NaN : t.population,
                        t1.population == null ? Double.NaN: t1.population));
        return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList copy = new AdminUnitList();
        copy.units = new ArrayList<>(this.units);
        copy.sortInplace(cmp);
        return copy;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred){
        if (pred == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        AdminUnitList copy = new AdminUnitList();
        for (AdminUnit unit : units) {
            if (unit != null && pred != null && pred.test(unit)) {
                copy.units.add(unit);
            }
        }
        return copy;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        if (pred == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        AdminUnitList copy = new AdminUnitList();
        int count = 0;
        for (AdminUnit unit : units) {
            if (unit != null && pred != null && pred.test(unit)) {
                copy.units.add(unit);
                count++;
            }
            if (count >= limit) {
                break;
            }
        }
        return copy;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        if (pred == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        AdminUnitList copy = new AdminUnitList();
        int count = 0;
        for (AdminUnit unit : units) {
            if (unit != null && pred != null && pred.test(unit)) {
                count++;
                if (count >= offset && count < limit) {
                    copy.units.add(unit);
                }
            }
            if (count >= limit) {
                break;
            }
        }
        return copy;
    }
}