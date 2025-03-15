package CSVReader;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AdminUnit {
    public String name;
    public Integer adminLevel;
    public Double population;
    public Double area;
    public Double density;
    public AdminUnit parent;
    public BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children = new ArrayList<>();

    AdminUnit(){}

    AdminUnit(String name, Integer adminLevel, Double population, Double area, Double density) {
        this.name = name;
        this.adminLevel = adminLevel;
        this.population = population;
        this.area = area;
        this.density = density;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("Name: " + name + '\n');
        if (parent != null) {
            str.append("Parent: " + parent.name + '\n');
        }
        else {str.append("Parent: Unknown" + '\n');}
        if (adminLevel != null) {
            if (adminLevel == 4) {
                str.append("Unit type: " + "województwo" + '\n');
            } else if (adminLevel == 6) {
                str.append("Unit type: " + "powiat" + '\n');
            } else if (adminLevel == 7) {
                str.append("Unit type: " + "gmina" + '\n');
            } else if (adminLevel == 8) {
                str.append("Unit type: " + "Miasto/wieś" + '\n');
            } else if (adminLevel == 9) {
                str.append("Unit type: " + "dzielnica" + '\n');
            } else if (adminLevel == 10) {
                str.append("Unit type: " + "przysiółek" + '\n');
            } else if (adminLevel == 11) {
                str.append("Unit type: " + "osiedle" + '\n');
            } else {
                str.append("Unit type: Unknown" + '\n');
            }
        } else {
            str.append("Unit type: null" + '\n');
        }
        str.append("Population: " + population + '\n');
        str.append("Area: " + area + '\n');
        str.append("Density: " + density + '\n');
        str.append(bbox.getWKT());
        return str.toString();
    }

    static void show(AdminUnit unit, PrintStream out) {
        if (unit == null) {
            out.println("null");
        } else {
            out.println(unit.toString());
        }
    }

    static void showChildren(AdminUnit unit, PrintStream out) {
        if (unit == null || unit.children.isEmpty()) {
            out.println("No children");
        }
        else {
            for (AdminUnit child : unit.children) {
                if (child != null) {
                    out.println(child.name);
                }
            }
        }
    }

    void fixMissingValues() {
        if (this.area == null)
        {
            this.area = (double) -1;
        }
        if (this.population == null && this.area == (double) -1){
            this.population = (double) -1;
        }
        if (this.density == null) {
            AdminUnit parent1 = this.parent;
            while (parent1 != null) {
                if (parent1.density != null) {
                    this.density = parent1.density;
                    break;
                }
                parent1 = parent1.parent;
            }
        }
        if (this.population == null && this.area != (double) -1) {
            AdminUnit parent1 = this.parent;
            while (parent1 != null) {
                if (this.density != null) {
                    this.population = this.area * this.density;
                    break;
                }
                parent1 = parent1.parent;
            }
        }
    }
}