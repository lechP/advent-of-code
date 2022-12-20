package y2022.day19

import common.printSolutions


fun task1(input: List<String>): Int {
    val blueprints = input.map { it.parseBlueprint() }
    val t0 = System.currentTimeMillis()
    val blueprintOutput = blueprints.associate { blueprint ->
        blueprint.id to maximumOutput(BotAmounts(1, 0, 0, 0), Materials(0, 0, 0, 0), 24, blueprint).also {
            println("For blueprint ${blueprint.id} calculated output is $it")
        }
    }
    println("Computation took ${System.currentTimeMillis() - t0} millis")
    return blueprintOutput.map { it.key * it.value }.sum()
}


fun maximumOutput(
    bots: BotAmounts,
    materials: Materials,
    minutesLeft: Int,
    blueprint: Blueprint
): Int {
    val nextMaterials = materials.plusBots(bots)
    if (minutesLeft == 1) {
        return nextMaterials.geode
    }
    // prefer geodes bots first
    if (materials.ore >= blueprint.costs.geode.ore && materials.obsidian >= blueprint.costs.geode.obsidian) {
        return maximumOutput(
            bots.plusGeode(),
            nextMaterials.copy(
                ore = nextMaterials.ore - blueprint.costs.geode.ore,
                obsidian = nextMaterials.obsidian - blueprint.costs.geode.obsidian,
            ),
            minutesLeft - 1,
            blueprint
        )
    }


    // other options
    val otherOptions = mutableSetOf<Int>()

    // optimisation - if having at least 4 ore always buy a bot -> skip path with no bot build
    if (materials.ore < 4) otherOptions.add(
        maximumOutput(
            bots,
            nextMaterials,
            minutesLeft - 1,
            blueprint
        )
    )

    // add obsidian if we can afford it
    if (materials.ore >= blueprint.costs.obsidian.ore && materials.clay >= blueprint.costs.obsidian.clay)
        otherOptions.add(
            maximumOutput(
                bots.plusObsidian(),
                nextMaterials.copy(
                    ore = nextMaterials.ore - blueprint.costs.obsidian.ore,
                    clay = nextMaterials.clay - blueprint.costs.obsidian.clay,
                ),
                minutesLeft - 1,
                blueprint
            )
        )

    // optimisation - having more clay bots than cost of obsidian bot makes no sense
    if (materials.ore >= blueprint.costs.clay.ore && bots.clay < blueprint.costs.obsidian.clay) otherOptions.add(
        maximumOutput(
            bots.plusClay(),
            nextMaterials.copy(
                ore = nextMaterials.ore - blueprint.costs.clay.ore,
            ),
            minutesLeft - 1,
            blueprint
        )
    )
    // optimisation - having more ore bots than max ore cost makes no sense
    if (materials.ore >= blueprint.costs.ore.ore && bots.ore < blueprint.costs.maxOreCost()) otherOptions.add(
        maximumOutput(
            bots.plusOre(),
            nextMaterials.copy(
                ore = nextMaterials.ore - blueprint.costs.ore.ore,
            ),
            minutesLeft - 1,
            blueprint
        )
    )

    return otherOptions.max()
}

fun task2(input: List<String>): Int {
    val blueprints = input.take(3).map { it.parseBlueprint() }
    val t0 = System.currentTimeMillis()
    val blueprintOutput = blueprints.map { blueprint ->
        maximumOutput(BotAmounts(1, 0, 0, 0), Materials(0, 0, 0, 0), 32, blueprint).also {
            println("For blueprint ${blueprint.id} calculated output is $it")
        }
    }
    println("Computation took ${System.currentTimeMillis() - t0} millis")
    return blueprintOutput.reduce { acc, int -> acc * int }
}

fun String.parseBlueprint() = Blueprint(
    id = drop(10).split(':')[0].toInt(),
    costs = split(": ")[1].dropLast(1).split(". ").let { costsStrings ->
        BotCosts(
            ore = OreBotCost(costsStrings[0].split(' ')[4].toInt()),
            clay = ClayBotCost(costsStrings[1].split(' ')[4].toInt()),
            obsidian = costsStrings[2].split(' ').let { ObsidianBotCost(it[4].toInt(), it[7].toInt()) },
            geode = costsStrings[3].split(' ').let { GeodeBotCost(it[4].toInt(), it[7].toInt()) },
        )
    }
)

data class Blueprint(
    val id: Int,
    val costs: BotCosts,
)

data class BotCosts(
    val ore: OreBotCost,
    val clay: ClayBotCost,
    val obsidian: ObsidianBotCost,
    val geode: GeodeBotCost,
) {
    fun maxOreCost() = maxOf(ore.ore, clay.ore, obsidian.ore, geode.ore)
}

data class OreBotCost(
    val ore: Int,
)

data class ClayBotCost(
    val ore: Int,
)

data class ObsidianBotCost(
    val ore: Int,
    val clay: Int,
)

data class GeodeBotCost(
    val ore: Int,
    val obsidian: Int,
)

data class BotAmounts(
    val ore: Int,
    val clay: Int,
    val obsidian: Int,
    val geode: Int,
) {
    fun plusOre() = copy(
        ore = ore + 1
    )

    fun plusClay() = copy(
        clay = clay + 1
    )

    fun plusObsidian() = copy(
        obsidian = obsidian + 1
    )

    fun plusGeode() = copy(
        geode = geode + 1
    )
}

data class Materials(
    val ore: Int,
    val clay: Int,
    val obsidian: Int,
    val geode: Int,
) {
    fun plusBots(bots: BotAmounts) = copy(
        ore = ore + bots.ore,
        clay = clay + bots.clay,
        obsidian = obsidian + bots.obsidian,
        geode = geode + bots.geode,
    )
}

fun main() = printSolutions(19, 2022, { input -> task1(input) }, { input -> task2(input) })