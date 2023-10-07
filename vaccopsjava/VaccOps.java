package vaccopsjava11;

import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    private Map<String, Doctor> doctors = new LinkedHashMap<>();
    private Map<String, Patient> patients = new LinkedHashMap<>();
    private Map<String, List<Patient>> doctorsPatients = new LinkedHashMap<>();


    public VaccOps() {
    }

    public void addDoctor(Doctor d) {
        if (doctors.containsValue(d)) throw new IllegalArgumentException();
        doctors.put(d.name, d);
        doctorsPatients.put(d.name,new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        if (!exist(d)) throw new IllegalArgumentException();
        doctors.put(d.name, d);
        doctorsPatients.get(d.name).add(p);

    }

    public Collection<Doctor> getDoctors() {
        return doctors.values();
    }

    public Collection<Patient> getPatients() {
        return patients.values();
    }

    public boolean exist(Doctor d) {
        return doctors.containsValue(d);
    }

    public boolean exist(Patient p) {
        return patients.containsValue(p);
    }


    public Doctor removeDoctor(String name) {
        if (!doctors.containsKey(name)) throw new IllegalArgumentException();
        doctorsPatients.clear();
        patients.clear();

        return doctors.remove(name);
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!exist(from)&&!exist(to)&&!exist(p)) throw new IllegalArgumentException();
List<Doctor>getDocs = new ArrayList<>(doctors.values());



    }

    public Collection<Doctor> getDoctorsByPopularity(int popularity) {
        return doctors.values().stream()
                .filter(d->d.popularity==popularity)
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return patients.values().stream()
                .filter(p->p.town.equals(town)).collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return patients.values()
                .stream().filter(p->p.age>lo && p.age<hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        doctors.values().stream()
                .sorted((d1,d2) -> {
                   int result = getPatients().size();
                    if (result==0) return Integer.parseInt(d1.name); var name =d2.name;
                    return result;
                }).collect(Collectors.toList());




        return null;
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return doctorsPatients.values().stream()
                .flatMap(List::stream)
                .sorted((p1,p2) -> {
                    doctors.values().stream().sorted(((d1, d2) -> {
                        int result = Integer.compare(d2.popularity, d1.popularity);
                        if (result == 0) result = Integer.compare(p2.height, p1.height);
                        if (result == 0) result = Integer.compare(p1.age, p2.age);
                        return result;

                    }));
                    return 0;
                }).collect(Collectors.toList());
                }

}
