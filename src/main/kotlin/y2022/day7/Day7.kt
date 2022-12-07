package y2022.day7

import common.printSolutions
import java.lang.Integer.min


fun task1(input: List<String>): Int {
    val filesystem = buildFilesystem(input)
    val maxDirSize = 100000
    return countSizeIfMatters(filesystem, maxDirSize)
}

fun countSizeIfMatters(directory: Directory, limit: Int): Int =
    with(directory.size()) {
        if (this <= limit) {
            this
        } else {
            0
        }
    } + directory.subdirs.values.sumOf { countSizeIfMatters(it, limit) }

fun buildFilesystem(input: List<String>): Directory {
    val inp = input.drop(1)

    val filesystem = Directory("root")
    var currentDir = filesystem

    inp.forEach {
        if (it.startsWith("$")) {
            if (!it.endsWith("ls")) {
                val chunkedCommand = it.split(" ")
                if (chunkedCommand[1] == "cd") {
                    currentDir = if (chunkedCommand[2] == "..") {
                        if (currentDir.parent == null) {
                            throw RuntimeException("trying to reach parent of root")
                        } else {
                            currentDir.parent!!
                        }
                    } else {
                        val subdirName = chunkedCommand[2]
                        currentDir.subdirs[subdirName]!!
                    }
                } else {
                    throw RuntimeException("Corrupted command: $chunkedCommand")
                }
            }
        } else {
            val fileDetails = it.split(" ")
            val name = fileDetails[1]
            if (fileDetails[0] == "dir") {
                currentDir.subdirs[name] = Directory(name, currentDir)
            } else {
                currentDir.files[name] = PlainFile(name, fileDetails[0].toInt())
            }
        }
    }
    return filesystem
}


fun task2(input: List<String>): Int {
    val filesystem = buildFilesystem(input)
    val totalSpace = 70000000
    val requiredSpace = 30000000
    val currentFreeSpace = totalSpace - filesystem.size()
    val minimalRequiredCleanup = requiredSpace - currentFreeSpace
    return findSmallestEligibleDirectory(filesystem, minimalRequiredCleanup, filesystem.size())
}

fun findSmallestEligibleDirectory(directory: Directory, minimalRequiredCleanup: Int, currentCandidate: Int): Int =
    if (directory.size() < minimalRequiredCleanup) {
        currentCandidate
    } else {
        val nextCurrentCandidate = min(directory.size(), currentCandidate)
        if (directory.subdirs.isEmpty()) {
            nextCurrentCandidate
        } else {
            directory.subdirs.values.minOf {
                findSmallestEligibleDirectory(
                    it, minimalRequiredCleanup, nextCurrentCandidate
                )
            }
        }
    }

fun main() = printSolutions(7, 2022, { input -> task1(input) }, { input -> task2(input) })

data class PlainFile(
    val name: String,
    val size: Int
)

data class Directory(
    val name: String,
    val parent: Directory? = null,
    val subdirs: MutableMap<String, Directory> = mutableMapOf(),
    val files: MutableMap<String, PlainFile> = mutableMapOf(),
) {
    fun size(): Int = subdirs.values.sumOf { it.size() } + files.values.sumOf { it.size }
}