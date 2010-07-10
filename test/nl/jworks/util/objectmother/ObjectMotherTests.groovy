package nl.jworks.util.objectmother

import org.junit.Test

/**
 * @author Erik Pragt
 */
class ObjectMotherTests {

    @Test
    void testBuildObjectWithDefaultValues() {
        use(ObjectMother) {
            def person = Person.build()

            assert "name" == person.name
            assert person.myBoolean
            assert 1 == person.age
            assert 1 == person.myInt
            assert 1 == person.myLong
        }
    }

    @Test
    void shouldBuildObjectWithOverridenValues() {
        use(ObjectMother) {
            def person = Person.build(name: 'erik')

            assert "erik" == person.name
            assert "city" == person.city
        }
    }

    @Test
    void shouldBuildImmutableObject() {
        use(ObjectMother) {
            def person = ImmutableClass.build(name: 'erik')

            assert "erik" == person.name
        }
    }

    @Test
    void shouldBuildImmutableObjectWithMixin() {
        Class.mixin ObjectMother

        def person = ImmutableClass.build(name: 'erik')

        assert "erik" == person.name
    }

    @Test
    void shouldPopulateObject() {
        use(ObjectMother) {
            def person = new Person().populate()

            assert "name" == person.name
            assert "city" == person.city
        }
    }

    @Test
    void shouldPopulateObjectWithSetParameters() {
        use(ObjectMother) {
            def person = new Person(name: 'erik pragt').populate()

            assert "erik pragt" == person.name
            assert "city" == person.city
        }
    }

    @Test
    void shouldPopuplateObjectWithoutDefaultConstructor() {
        use(ObjectMother) {
            def invoice = new Invoice(100).populate()

            assert 100 == invoice.number
            assert "description" == invoice.description

            invoice = new Invoice("new computer").populate()

            assert 1 == invoice.number
            assert "new computer" == invoice.description
        }
    }
}


class Person {
    String name
    String city
    Integer age
    boolean myBoolean
    int myInt
    long myLong

    List list
}

class Invoice {
    int number
    String description

    Invoice(int number) {
        this.number = number
    }

    Invoice(String description) {
        this.description = description
    }
}

@Immutable
final class ImmutableClass {
    String name
}

