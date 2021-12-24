package logic

import java.util.*
import kotlin.collections.HashSet

class LangTech (val name: String, val version: Version) {
    //describes a language in the EPILANG app
    private var _name: String = name
    private var _version: Version = version
    private var _users: HashSet<User>
    private var _advancedUser: HashSet<User>
    private var _teachers: HashSet<User>
    private var _numberOfUsers: Int
    private var _numberOfAdvancedUsers: Int
    private var _numberOfTeachers: Int

    init {
        _users = HashSet()
        _advancedUser = HashSet()
        _teachers = HashSet()
        _numberOfUsers = 0
        _numberOfAdvancedUsers = 0
        _numberOfTeachers = 0
    }

    constructor(users: Enumeration<User>) : this("UNNAMMED", Version(0)) {
        _users = HashSet()
        // add all the elements of users to _users
        val usersList: List<User> = users.toList()
        _users.addAll(usersList)
    }


}