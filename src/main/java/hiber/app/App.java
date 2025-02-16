package hiber.app;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("JPAApp");
            em = emf.createEntityManager();
            try {
                System.out.print("\n");
                addApartment(4, 100,  "1 Chreschatyk str, Kyiv", 100000, "Center");
                addApartment(3, 70,  "1 Bandery ave, Kyiv", 80000, "Pochayna");
                addApartment(1, 30.8d,  "1 Bazhana ave, Kyiv", 50000, "Osokorky");
                addApartment(1, 28.4d,  "1 Hlushkova ave, Kyiv", 45000, "Teremky");

                printApartments(getApartments(1), " with 1 rooms");

                changeApartment(1, 110000);

                getApartment(6);

                markApartmentSold("1 Bandery ave, Kyiv");
                printApartments(getApartments(), " unsold only");

                removeApartment(25);

                removeApartment(2);
                printApartments(getApartments(), " unsold only");

            } finally {
                em.close();
                emf.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addApartment(int rooms, double square, String address, int price, String district) {
        em.getTransaction().begin();
        try {
            Apartment apart = new Apartment(rooms, square, address, price);
            apart.setDistrict(district);
            em.persist(apart);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeApartment(int id, int price) {
        Apartment apart = getApartment(id);
        if (apart != null) {
            em.getTransaction().begin();
            try {
                apart.setPrice(price);
                em.persist(apart);
                em.getTransaction().commit();

                System.out.println("Apartment price changed: " + apart);
            } catch (Exception ex) {
                em.getTransaction().rollback();
                System.out.println("Apartment price not changed: " + ex.getMessage());
            }
        }
    }

    private static void markApartmentSold(String address) {
        Apartment apart = getApartment(address);
        if (apart != null) {
            em.getTransaction().begin();
            try {
                apart.markSold();
                em.getTransaction().commit();
                System.out.println("Apartment  is sold: " + apart);
            } catch (Exception ex) {
                em.getTransaction().rollback();
                System.out.println("Apartment can't be marked sold: " + ex.getMessage());
            }
        }
    }

    private static boolean removeApartment(int id) {
        Apartment apart = em.getReference(Apartment.class, id);
        if (apart == null) {
            System.out.println("Apartment with id " + id + " not found");

            return false;
        } else {
            em.getTransaction().begin();
            try {
                em.remove(apart);
                em.getTransaction().commit();

                return true;
            } catch (Exception ex) {
                em.getTransaction().rollback();
                System.out.println("Apartment with id " + id + " removin error");
            }
        }

        return false;

    }

    private static Apartment getApartment(int id) {
        Apartment apart = em.find(Apartment.class, id);
        if (apart == null) {
            System.out.println("Apartment with id " + id + " not found");

            return null;
        }
        return apart;
    }

    private static Apartment getApartment(String address) {
        Apartment apart = em.createQuery("SELECT a FROM Apartment a WHERE a.address = :address", Apartment.class)
            .setParameter("address", address)
            .getSingleResult();
        if (apart == null) {
            System.out.println("Apartment with address " + address + " not found");

            return null;
        }
        return apart;
    }

    private static List<Apartment> getApartments(int rooms) {
        return new ApartmentList(em).getApartments(rooms, false);
    }

    private static List<Apartment> getApartments() {
        return new ApartmentList(em).getApartments(false);
    }

    private static void printApartments(List<Apartment> apartList, String details) {
        System.out.print("\nFound Apartments " + details + ":\n");
        for (Apartment apart : apartList) {
            System.out.println(apart);
        }
        System.out.print("\n");
    }
}
