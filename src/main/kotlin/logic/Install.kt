package logic

import java.io.File

/*class Install {
    private var location: String
    private var versionVal: Version
    private var installArchitecture: GeneralTree<Triple<Boolean, String>>
    //address for getting information in case it isn't installed
    private var address: String

    init {
        location = "-1"
        versionVal = Version(0)
        val root: Pair<Boolean, String> = Pair(false, "/")
        installArchitecture = GeneralTree(root)
        address = "https://raw.githubusercontent.com/Kotlin/kotlin-doc/master/kotlin-doc-api.md"
    }

    constructor(location: String, address: String) {
        //check if the location exists
        if (File(location).exists()) {
            this.location = location
            versionVal = Version(0)
            val root: Pair<Boolean, String> = Pair(false, "/")
            installArchitecture = GeneralTree(root)
            this.address = address
        } else {
            throw IllegalArgumentException("The location does not exist")
        }
    }

    constructor(location: String, version: Version, address: String) {
        if (File(location).exists()) {
            this.location = location
            versionVal = version
            val root: Pair<Boolean, String> = Pair(false, "/")
            installArchitecture = GeneralTree(root)
            this.address = address
        } else {
            throw IllegalArgumentException("The location does not exist")
        }
    }

    var version: Version
        get() {
            return this.versionVal
        }

        private set(value: Version) {
            val versionCopy: Version = value.copy()
            versionVal = versionCopy
        }

    //build a function which builds a general tree of the files at the location with each key being the name of the file and whether it is a directory or not using
    //the breadth first search function
    fun buildTree(): GeneralTree<Pair<Boolean, String>> {
        fun addToTree(file: File, parent: GeneralTree<Pair<Boolean, String>>) {
            if (file.isDirectory) {
                val newNode: GeneralTree<Pair<Boolean, String>> = GeneralTree(Pair(true, file.name))
                parent.addChild(newNode)
                for (child in file.listFiles()) {
                    addToTree(child, newNode)
                }
            } else {
                parent.addChild(Pair(false, file.name))
            }
        }

        val root: Pair<Boolean, String> = Pair(false, "/")
        val tree: GeneralTree<Pair<Boolean, String>> = GeneralTree(root)
        addToTree(File(location), tree)
        return tree
    }

    //install missing files by comparing the general tree to the installed tree
    fun installMissingFiles() {
        val currentFiles: GeneralTree<Pair<Boolean, String>> = buildTree()
        val toInstallFiles: GeneralTree<Pair<Boolean, String>> = installArchitecture

        //a function which returns a list of the files that need to be installed
        fun getFilesToInstall(currentFiles: GeneralTree<Pair<Boolean, String>>, toInstallFiles: GeneralTree<Pair<Boolean, String>>, currentPath: String): List<Pair<Boolean, String>> {
            val filesToInstall: MutableList<Pair<Boolean, String>> = mutableListOf()
            for (currentFile in currentFiles.getChildren()) {
                if (currentFile.getValue().first) {
                    //if the current file is a directory
                    val newPath: String = currentPath + currentFile.getValue().second + "/"
                    val newInternalNode: GeneralTree<Pair<Boolean, String>> = toInstallFiles.getChild(newPath)
                } else {
                    if (!toInstallFiles.contains(currentFile)) {
                        filesToInstall.add(Pair(false, currentFile.getValue().second))
                    }
                }
            }
            return filesToInstall
        }

        val filesToInstall: List<Pair<Boolean, String>> = getFilesToInstall(currentFiles, toInstallFiles, location)
        for (file in filesToInstall) {
            if (file.first) {
                //if the file is a directory
                val newFile: File = File(file.second)
                newFile.mkdir()
            } else {
                //if the file is a file
                val newFile: File = File(file.second)
                newFile.createNewFile()
            }
        }
    }

    //install a file from the address
    fun installFile(fileName: String) {
        val file: File = File(location + fileName)
        val fileToCopy: File = File(address + fileName)
        file.writeText(fileToCopy.readText())
    }
}*/