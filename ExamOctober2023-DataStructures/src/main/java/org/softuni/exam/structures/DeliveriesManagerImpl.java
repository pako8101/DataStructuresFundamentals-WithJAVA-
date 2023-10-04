package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {
    private Map<String, Deliverer> deliverersById;
    private Map<String, Package> packagesById;
    private Map<String, Package> unasignedPackages;


    private Map<String, Integer> delivererPackage;

    public DeliveriesManagerImpl() {
        this.deliverersById = new LinkedHashMap<>();
        this.packagesById = new LinkedHashMap<>();
        this.delivererPackage = new LinkedHashMap<>();
        this.unasignedPackages = new HashMap<>();
    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        deliverersById.put(deliverer.getId(), deliverer);
        delivererPackage.put(deliverer.getId(), 0);
    }

    @Override
    public void addPackage(Package _package) {
        packagesById.put(_package.getId(), _package);
unasignedPackages.put(_package.getId(), _package);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return deliverersById.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return packagesById.containsKey(_package.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return deliverersById.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return packagesById.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!contains(_package) || !contains(deliverer)) {
            throw new IllegalArgumentException();
        }
        Integer count = delivererPackage.get(deliverer.getId());
        delivererPackage.put(deliverer.getId(), count + 1);
        unasignedPackages.remove(_package.getId());
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return unasignedPackages.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return packagesById.values().stream().sorted(Comparator.comparing(Package::getWeight).reversed()
                        .thenComparing(Package::getReceiver))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return deliverersById.values()
                .stream()
                .sorted(Comparator.comparing((Deliverer d) -> delivererPackage.get(d.getId())).reversed()
                        .thenComparing(Deliverer::getName)).collect(Collectors.toList());
    }
}
