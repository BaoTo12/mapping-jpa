import com.manning.javapersistence.ch03.validation.Item;
import com.manning.javapersistence.ch03.validation.Item_;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class MetamodelTest {
    private static EntityManagerFactory emf;

    @BeforeEach
    public void createEntityManager(){
        emf = Persistence.createEntityManagerFactory("ch03");
    }
    @Test
    public void accessDynamicMetamodel() {
        Metamodel metamodel = emf.getMetamodel();
        Set<ManagedType<?>> managedTypes = metamodel.getManagedTypes();
        ManagedType<?> itemType = managedTypes.iterator().next();
        Assertions.assertAll(() -> Assertions.assertEquals(1, managedTypes.size()),
                () -> Assertions.assertEquals(
                        Type.PersistenceType.ENTITY,
                        itemType.getPersistenceType()));

        SingularAttribute<?, ?> idAttribute =
                itemType.getSingularAttribute("id");
        Assertions.assertFalse(idAttribute.isOptional());
        SingularAttribute<?, ?> nameAttribute =
                itemType.getSingularAttribute("name");
        Assertions.assertAll(() -> Assertions.assertEquals(String.class, nameAttribute.getJavaType()),
                () -> Assertions.assertEquals(
                        Attribute.PersistentAttributeType.BASIC,
                        nameAttribute.getPersistentAttributeType()
                ));
        SingularAttribute<?, ?> auctionEndAttribute =
                itemType.getSingularAttribute("auctionEnd");
        Assertions.assertAll(() -> Assertions.assertEquals(Date.class,
                        auctionEndAttribute.getJavaType()),
                () -> Assertions.assertFalse(auctionEndAttribute.isCollection()),
                () -> Assertions.assertFalse(auctionEndAttribute.isAssociation())
        );
    }
    @Test
    public void accessStaticMetamodel() {
        EntityManager entityManager = emf.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = query.from(Item.class);
        query.select(itemRoot);
        List<Item> items = entityManager.createQuery(query).getResultList();
        System.out.println(items);
    }

    @Test
    public void testItemsPattern() {
        // select i from Item i where i.name like :pattern
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        // runtime check
        Path<String> namePath = fromItem.get("name");
        query.where(criteriaBuilder.like(namePath, criteriaBuilder.parameter(String.class, "pattern")));
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
        Assertions.assertAll(() -> Assertions.assertEquals(1, items.size()),
                () -> Assertions.assertEquals("Item 1", items.iterator().next().getName()));

    }
    @Test
    public void testItemsPatternWithGeneratedClass() {
        EntityManager em = emf.createEntityManager();
        deleteItems(em);
        persistItems(em);
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
        Root<Item> fromItem = query.from(Item.class);
        // compilation check
        Path<String> namePath = fromItem.get(Item_.name);
        query.where(criteriaBuilder.like(namePath, criteriaBuilder.parameter(String.class, "pattern")));
        List<Item> items = em.createQuery(query).setParameter("pattern", "%Item 1%").getResultList();
        Assertions.assertAll(() -> Assertions.assertEquals(1, items.size()),
                () -> Assertions.assertEquals("Item 1", items.iterator().next().getName()));
    }
    private void deleteItems(EntityManager entityManager){
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Item> query = criteriaBuilder.createCriteriaDelete(Item.class);
        query.from(Item.class);
        entityManager.createQuery(query).executeUpdate();
        entityManager.getTransaction().commit();
    }
    private void persistItems(EntityManager entityManager){
        entityManager.getTransaction().begin();
        Item item1 = new Item();
        item1.setName("Item 1");
        item1.setAuctionEnd(tomorrow());

        Item item2 = new Item();
        item2.setName("Item 2");
        item2.setAuctionEnd(tomorrow());
        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.getTransaction().commit();
    }
    private Date tomorrow() {
        return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
    }

    @AfterAll
    static void afterAll() {
        emf.close();
    }
}
