package craftsmanLab.core;

import craftsmanLab.models.ApartmentRenovation;
import craftsmanLab.models.Craftsman;

import java.util.*;
import java.util.stream.Collectors;

public class CraftsmanLabImpl implements CraftsmanLab {

    private final Map<String, Craftsman> craftsmanMap = new LinkedHashMap<>();
    private final Map<String, ApartmentRenovation> apartmentRenovationMap = new LinkedHashMap<>();

    private final Map<ApartmentRenovation,Craftsman> assignmentMap = new LinkedHashMap<>();
    @Override
    public void addApartment(ApartmentRenovation job) {
        if (apartmentRenovationMap.containsKey(job.address)) throw new IllegalArgumentException();
        apartmentRenovationMap.put(job.address, job);

    }

    @Override
    public void addCraftsman(Craftsman craftsman) {
        craftsmanMap.put(craftsman.name, craftsman);
    }

    @Override
    public boolean exists(ApartmentRenovation job) {
        return apartmentRenovationMap.containsValue(job);
    }

    @Override
    public boolean exists(Craftsman craftsman) {
        return craftsmanMap.containsValue(craftsman);
    }

    @Override
    public void removeCraftsman(Craftsman craftsman) {
        if (!craftsmanMap.containsKey(craftsman.getName()) ||
                assignmentMap.containsValue(craftsman) ) {
            throw new IllegalArgumentException();
        }

        craftsmanMap.remove(craftsman.getName());
        assignmentMap.remove(craftsman);

    }

    @Override
    public Collection<Craftsman> getAllCraftsmen() {
        return craftsmanMap.values();
    }

    @Override
    public void assignRenovations() {


        List<ApartmentRenovation> apartments = new ArrayList<>(apartmentRenovationMap.values());
        apartments.sort(Comparator.comparing(a->a.deadline));

        for (ApartmentRenovation apartment : apartments) {
            if (!assignmentMap.containsKey(apartment)) {
                Craftsman lowestEarningCraftsman = getLeastProfitable();
                assignmentMap.put(apartment, lowestEarningCraftsman);
                double cost = apartment.calculateCost();
               lowestEarningCraftsman.setTotalEarnings(cost + lowestEarningCraftsman.getTotalEarnings());
            }
        }

    }

    @Override
    public Craftsman getContractor(ApartmentRenovation job) {
        if (!assignmentMap.containsKey(job)) throw new IllegalArgumentException();


        return assignmentMap.get(job);
    }

    @Override
    public Craftsman getLeastProfitable() {
        if (craftsmanMap.isEmpty()) throw new IllegalArgumentException();
        return     craftsmanMap.values().stream()
                .min(Comparator.comparingDouble(a->a.totalEarnings)).get();


    }

    @Override
    public Collection<ApartmentRenovation> getApartmentsByRenovationCost() {
      return   apartmentRenovationMap.values().stream()
                .sorted(Comparator.comparingDouble(ApartmentRenovation::calculateCost))
                .sorted((a1, a2) -> {
                    double aa = assignmentMap.get(a1).getTotalEarnings();
                    double bb = assignmentMap.get(a2).getTotalEarnings();
                    double result = Double.compare(aa, bb);
                    if (result==0) result = Double.compare(a1.calculateCost(), a2.calculateCost());
return (int) result;

                }).collect(Collectors.toList());



//        List<ApartmentRenovation> apartmentList = new ArrayList<>(apartmentRenovationMap.values());
//        Collections.sort(apartmentList, (a1, a2) -> {
//            double cost1 = assignmentMap.containsKey(a1) ? assignmentMap.get(a1).getTotalEarnings() : a1.calculateCost();
//            double cost2 = assignmentMap.containsKey(a2) ? assignmentMap.get(a2).getTotalEarnings() : a2.calculateCost();
//            return Double.compare(cost2, cost1);
//        });
//        return apartmentList;
    }

    @Override
    public Collection<ApartmentRenovation> getMostUrgentRenovations(int limit) {
        List<ApartmentRenovation> apartmentList = new ArrayList<>(apartmentRenovationMap.values());
        apartmentList.sort(Comparator.comparing(ApartmentRenovation::getDeadline));
        return apartmentList.subList(0, Math.max(limit, apartmentList.size()));


//        return apartmentRenovationMap.values().stream()
//                .sorted(Comparator.comparing(a->a.deadline)).collect(Collectors.toList());
    }
}
