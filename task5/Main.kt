package org.example

interface Sounding {
    fun makeSound(): String
}

// Абстрактный класс животного
abstract class Animal(val id: Int, val height: Double)

// Разные типы животных
class Cat(id: Int, height: Double) : Animal(id, height), Sounding {
    override fun makeSound() = "Meow"
}

class Dog(id: Int, height: Double) : Animal(id, height), Sounding {
    override fun makeSound() = "Woof"
}

class Hippo(id: Int, height: Double) : Animal(id, height), Sounding {
    override fun makeSound() = "Grunt"
}

class Horse(id: Int, height: Double) : Animal(id, height), Sounding {
    override fun makeSound() = "Neigh"
}

class Fish(id: Int, height: Double) : Animal(id, height) // Рыба не издает звуки

// Класс надзирателя
data class Zookeeper(val id: Int, val name: String)

// Класс зоопарка
class Zoo(private val animals: MutableMap<Int, Animal> = HashMap()) {
    private val zookeeperAssignments = HashMap<Int, Int>() // id животного -> id надзирателя
    private val zookeeperToAnimals = HashMap<Int, MutableSet<Int>>() // id надзирателя -> id животных

    // Добавление животного
    fun addAnimal(animal: Animal) {
        animals[animal.id] = animal
    }

    // Поиск животного по id
    fun findAnimalById(id: Int): Animal? = animals[id]

    // Удаление животного по id
    fun removeAnimalById(id: Int) {
        animals.remove(id)
        zookeeperAssignments[id]?.let { zookeeperToAnimals[it]?.remove(id) }
        zookeeperAssignments.remove(id)
    }

    // Назначение надзирателя животному
    fun assignZookeeper(animalId: Int, zookeeper: Zookeeper) {
        zookeeperAssignments[animalId] = zookeeper.id
        zookeeperToAnimals.computeIfAbsent(zookeeper.id) { HashSet() }.add(animalId)
    }

    // Получение всех животных под надзором определенного надзирателя
    fun getAnimalsByZookeeperId(zookeeperId: Int): List<Animal> =
        zookeeperToAnimals[zookeeperId]?.mapNotNull { animals[it] } ?: emptyList()

    // Получение всех животных под надзором надзирателя с заданным именем
    fun getAnimalsByZookeeperName(name: String, zookeepers: List<Zookeeper>): List<Animal> {
        val ids = zookeepers.filter { it.name == name }.map { it.id }.toSet()
        return zookeeperToAnimals.filterKeys { it in ids }.values.flatten().mapNotNull { animals[it] }
    }

    // Получение всех животных с ростом выше заданного
    fun getAnimalsTallerThan(minHeight: Double): List<Animal> =
        animals.values.filter { it.height > minHeight }

    // Получение всех животных, умеющих издавать звуки
    fun getSoundingAnimals(): List<Animal> =
        animals.values.filterIsInstance<Sounding>().map { it as Animal }

    // Получение всех животных определенного типа
    fun <T : Animal> getAnimalsOfType(clazz: Class<T>): List<T> =
        animals.values.filter { clazz.isInstance(it) }.map { clazz.cast(it) }

}

fun main() {
    val zoo = Zoo()

    val cat = Cat(1, 30.0)
    val dog = Dog(2, 50.0)
    val hippo = Hippo(3, 150.0)
    val fish = Fish(4, 10.0)

    zoo.addAnimal(cat)
    zoo.addAnimal(dog)
    zoo.addAnimal(hippo)
    zoo.addAnimal(fish)

    val zookeeper1 = Zookeeper(101, "Anya")
    val zookeeper2 = Zookeeper(102, "Katya")

    zoo.assignZookeeper(1, zookeeper1) // Кошка -> Аня
    zoo.assignZookeeper(2, zookeeper1) // Собака -> Аня
    zoo.assignZookeeper(3, zookeeper2) // Бегемот -> Катя

    println("Животные под надзором Ани: ${zoo.getAnimalsByZookeeperName("Anya", listOf(zookeeper1, zookeeper2))}")
    println("Животные выше 40 см: ${zoo.getAnimalsTallerThan(40.0)}")
    println("Животные, издающие звуки: ${zoo.getSoundingAnimals().map { it::class.simpleName }}")
}