package craftsmanLab.models;

import java.time.LocalDate;

public class ApartmentRenovation {
    public String address;
    public double area;
    public double workHoursNeeded;
    public LocalDate deadline;

    public ApartmentRenovation(String address, double area, double workHoursNeeded, LocalDate deadline) {
        this.address = address;
        this.area = area;
        this.workHoursNeeded = workHoursNeeded;
        this.deadline = deadline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getWorkHoursNeeded() {
        return workHoursNeeded;
    }

    public void setWorkHoursNeeded(double workHoursNeeded) {
        this.workHoursNeeded = workHoursNeeded;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "ApartmentRenovation{" +
                "address='" + address + '\'' +
                ", area=" + area +
                ", workHoursNeeded=" + workHoursNeeded +
                ", deadline=" + deadline +
                '}';
    }
public double calculateCost(){
        return getWorkHoursNeeded() * getArea();
}


}
