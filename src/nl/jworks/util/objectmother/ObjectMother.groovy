package nl.jworks.util.objectmother

/**
 * Simple category for building testdata using the Object Mother pattern.
 *
 * Sample usage:
 *
 * use(ObjectMother) {
 *     def person = new Person().populate()
 *     def person = new Person().populate()
 * }
 *
 * use(ObjectMother) {
 *     def person = Person.build()
 * }
 *
 * @author Erik Pragt
 */
class ObjectMother {

    static def build(clazz, values = [:]) {
        // Filter out some Groovy properties
        def filtered = clazz.declaredFields.findAll { !(it.name.startsWith('$') || it.name.startsWith('__') || it.name == 'metaClass')}

        def constructorArgValues = [:]

        filtered.each { p ->
            def value

            if(values.containsKey(p.name)) {
                value = values.get(p.name)
            }
            else {
                value = createValue(p)
            }

            constructorArgValues."${p.name}" = value
        }

        return clazz.newInstance(constructorArgValues)
    }

    static def populate(object) {
        // Filter out some Groovy properties
        def filtered = object.metaClass.properties.findAll { !(it.name == 'class' || it.name == 'metaClass')}

        filtered.each { p ->
			if (!object."${p.name}") {
	            def value = createValue(p)

	            if (value) {
	                object."${p.name}" = value
	            }
			}
        }

        return object
    }

    private static def createValue(field) {
        switch (field.type) {
            case String:
                return field.name
            case Integer:
                return 1
            case Float:
                return 1
            case Double:
                return 1
            case Long:
                return 1
            case Byte:
                return 1
            case Boolean:
                return Boolean.TRUE
            case int:
                return 1
            case long:
                return 1
            case short:
                return 1
            case byte:
                return 1
            case double:
                return 1.0
            case float:
                return 1
            case boolean:
                return true
            case byte:
                return 1


            default: return null
        }
    }
}
