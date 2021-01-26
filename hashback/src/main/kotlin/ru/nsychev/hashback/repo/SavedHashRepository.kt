package ru.nsychev.hashback.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.nsychev.hashback.model.SavedHash

@Component
interface SavedHashRepository : CrudRepository<SavedHash, Long> {
    fun findByHash(hash: String): SavedHash?
}

